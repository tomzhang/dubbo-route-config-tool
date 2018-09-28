package com.router.tool.engine;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;
import com.alibaba.dubbo.remoting.zookeeper.zkclient.ZkclientZookeeperTransporter;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.router.tool.helper.VitaminHelper;
import com.router.tool.util.URLUtil;
import com.vdian.vitamin.common.exception.VitaminException;
import com.vdian.vitamin.common.util.CheckerUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.router.tool.domain.VtraceInfo;
import com.router.tool.util.EnvEnum;
import com.router.tool.util.HttpClientUtil;
import com.router.tool.xpath.XPathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.router.tool.common.Constants.TRACE_INFO_FEILED_LENGHT;

/**
 * String traceIdURL = "http://vtrace.vdian.net/trace?id=1cc300000164823040880a2000840896";
 */
public class ToolEngine implements Engine {

    private static String zookeeperUrl = null;

    public String boss(String traceIdURL) throws Exception {
        String traceId = StringUtils.substringAfter(traceIdURL, "id=");
        List<VtraceInfo> vtraceList = getVtraceVOList(HttpClientUtil.httpGetRequest(traceIdURL));

        URL registerURL = URL.valueOf(zookeeperUrl + "/com.alibaba.dubbo.registry.RegistryService?dubbo=3.2.0-SNAPSHOT&interface=com.alibaba.dubbo.registry.RegistryService&pid=19494&timestamp=1532323929545");
        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry(registerURL, new ZkclientZookeeperTransporter());

        ZkClient zkClient = new ZkClient(getZkServer());

        List<String> routes = Lists.newArrayList();
        Map<String, String> IP_MAPPING = getIpMap(vtraceList);
        StringBuilder mutiVersion = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (VtraceInfo info : vtraceList) {
            String service = info.getService();
            String[] routers = info.getRoute().split("=>");
            String consumerHost = routers[0];
            String providerHost = routers[1];

            String servicePath = "/" + Constants.DEFAULT_DIRECTORY + "/" + service;
            boolean isConsumerEmpty = mapHost(consumerHost, "consumers", IP_MAPPING, servicePath,
                    zkClient, mutiVersion);
            boolean isProviderEmpty = mapHost(providerHost, "providers", IP_MAPPING, servicePath,
                    zkClient, mutiVersion);
            if (isConsumerEmpty || isProviderEmpty) {
                continue;
            }

            consumerHost = IP_MAPPING.get(consumerHost);
            providerHost = IP_MAPPING.get(providerHost);

            String[] routerNames = new String[] { "filter_not_match", "filter_match" };
            // delete route
            deleteRouteConfig(zookeeperRegistry, zkClient, routerNames, servicePath);

            int priority = 1;
            for (String routerName : routerNames) {
                URL routerURL = URLUtil.toUrl(service, routerName, priority, URLUtil.getRule(consumerHost, providerHost, "filter_match".equals(routerName)));
                zookeeperRegistry.register(routerURL);
                priority++;
            }

            // register vitamin
            VitaminHelper.register(info.getApp(), traceId, false);
            result.append("rpcId:" + info.getRpcId() + " appname:" + info.getApp() + " service: " + servicePath + " method:" + info.getMethod() + " conumser:" + consumerHost + "-->provider:"
                          + providerHost);
            result.append("\n");
            routes.add(servicePath);
        }

        VitaminHelper.registerRoutes(traceId, JSON.toJSONString(routes));

        if (CheckerUtil.isNotEmpty(mutiVersion.toString())) {
            result.append("\n");
            result.append(mutiVersion);
        }
        return result.toString();
    }

    private void deleteRouteConfig(ZookeeperRegistry zookeeperRegistry, ZkClient zkClient, String[] routerNames, String servicePath) {
        String routersPath = servicePath + "/" + Constants.ROUTERS_CATEGORY;
        List<String> routeList = zkClient.getChildren(routersPath);
        if (CheckerUtil.isNotEmpty(routeList)) {
            for (String route : routeList) {
                if (isExist(route, routerNames)) {
                    URL url = URL.valueOf(URL.decode(route));
                    zookeeperRegistry.unregister(url);
                }
            }
        }
    }

    private boolean isExist(String route, String[] routerNames) {
        for (String filterName : routerNames) {
            if (route.contains(filterName)) {
                return true;
            }
        }
        return false;
    }

    private boolean mapHost(String host, String slide, Map<String, String> IP_MAPPING, String servicePath, ZkClient zkClient, StringBuilder mutiVersion) {
        if (IP_MAPPING.get(host) == null) {
            List<String> ipList = zkClient.getChildren(servicePath + "/" + slide);

            if (CheckerUtil.isEmpty(ipList)) {
                System.out.println("servicePath : " + servicePath + " no " + slide + ".");
                return true;
            } else {
                Multimap<String, String> versionToIpsMap = ArrayListMultimap.create();
                for (String ipUrl : ipList) {
                    URL url = URL.valueOf(URL.decode(ipUrl));
                    String version = url.getMethodParameter(null, "version");
                    versionToIpsMap.put(version, url.getHost());
                }

                List<String> ips = Lists.newArrayList();
                if (versionToIpsMap.keySet().size() > 1) {
                    String version = "";
                    // 多版本的情况下，默认取1.0.0版本
                    String DEFAULT_VERSION = "1.0.0";
                    if (versionToIpsMap.keySet().contains(DEFAULT_VERSION)) {
                        version = DEFAULT_VERSION;
                        ips = (List<String>) versionToIpsMap.get(version);
                    } else {
                        int index = 0;
                        for (String v : versionToIpsMap.keySet()) {
                            if (index == 0) {
                                version = v;
                            } else {
                                if (v.length() < version.length()) {
                                    version = v;
                                }
                            }
                            index++;
                        }
                        ips = (List<String>) versionToIpsMap.get(version);
                    }
                    mutiVersion.append("slide:" + slide + " ,muti version service:" + servicePath
                                       + ", versions:" + versionToIpsMap.keySet()
                                       + " ,selected version:" + version + "\n");
                } else {
                    ips.addAll(versionToIpsMap.values());
                }

                if (CheckerUtil.isNotEmpty(ips)) {
                    IP_MAPPING.put(host, ips.get(0));
                }
            }
        }
        return false;
    }

    private String getZkServer() {
        String backupAddress = zookeeperUrl.replace("zookeeper://", "");
        return backupAddress;
    }

    private Map<String, String> getIpMap(List<VtraceInfo> vtraceList) {
        Map<String, String> IP_MAPPING = Maps.newHashMap();

        for (VtraceInfo vo : vtraceList) {
            String[] routers = vo.getRoute().split("=>");
            String consumerHost = routers[0];
            String providerHost = routers[1];
            IP_MAPPING.put(consumerHost, null);
            IP_MAPPING.put(providerHost, null);
        }

        return IP_MAPPING;
    }

    private List<VtraceInfo> getVtraceVOList(String htmlContent) {
        String trTag = "//tr[@data-tt-id]/td";
        List<String> results = XPathUtil.getXpath(trTag, htmlContent);

        List<List<String>> resultList = new ArrayList<>();
        int filedsCount = TRACE_INFO_FEILED_LENGHT;
        int i = filedsCount;
        int index = 1;
        List<String> list = null;
        for (String result : results) {
            if (index < filedsCount) {
                if (index == 1) {
                    list = new ArrayList<>();
                    resultList.add(list);
                }
                list.add(result);
            } else {
                index = 1;
                list.add(result);
                continue;
            }
            index++;
        }

        List<VtraceInfo> vtraceVOList = new ArrayList<>();
        for (List<String> res : resultList) {
            try {
                String rowContent = JSON.toJSONString(res);
                if (rowContent.contains("DUBBO")) {
                    VtraceInfo vo = new VtraceInfo(res);
                    if ("DUBBO".equals(vo.getType())) {
                        vtraceVOList.add(vo);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return vtraceVOList;
    }

    public void setEnv(String evn) throws VitaminException {
        EnvEnum envEnum = EnvEnum.getEnv(evn);
        ToolEngine.zookeeperUrl = envEnum.getEnvInfo().getZkURL();
        VitaminHelper.vitaminClient = VitaminHelper.initVitaminClient(envEnum.getEnvInfo().getVitaminIp());
    }

    public void removeRoutes(String routeConfig) {
        if (CheckerUtil.isNotEmpty(routeConfig)) {
            List<String> routes = JSON.parseArray(routeConfig, String.class);

            if (CollectionUtils.isNotEmpty(routes)) {
                URL registerURL = URL.valueOf(zookeeperUrl
                                              + "/com.alibaba.dubbo.registry.RegistryService?dubbo=3.2.0-SNAPSHOT&interface=com.alibaba.dubbo.registry.RegistryService&pid=19494&timestamp=1532323929545");
                ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry(registerURL,
                        new ZkclientZookeeperTransporter());
                ZkClient zkClient = new ZkClient(getZkServer());
                String[] routerNames = new String[] { "filter_not_match", "filter_match" };

                for (String servicePath : routes) {
                    deleteRouteConfig(zookeeperRegistry, zkClient, routerNames, servicePath);
                }
            }
        }
    }

    public static final ToolEngine getInstance() {
        return Hodler.INSTANCE;
    }

    private static class Hodler {
        private static final ToolEngine INSTANCE = new ToolEngine();

        private Hodler() {}
    }
}
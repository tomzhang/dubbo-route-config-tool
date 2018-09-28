package com.router.tool.main;

import org.I0Itec.zkclient.ZkClient;

/**
 * <p>文件名称：NodeStorageHolder.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 口袋购物 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2018年7月24日</p>
 *
 * @author dengkui@weidian.com
 * @version 1.0
 */

/**
 * rmr /dubbo/com.alibaba.dubbo.demo.DemoService/routers
 * ls /dubbo/com.alibaba.dubbo.demo.DemoService/routers
 */
public class MainTest {

    private final static String zookeeperUrl = "zookeeper://zk1.dubbo.daily.idcvdian.com:2181";

    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient(getZkServer());

        zkClient.createPersistent("/smart-routing", true);


//        URL registerURL = URL.valueOf(zookeeperUrl + "/com.alibaba.dubbo.registry.RegistryService?dubbo=3.2.0-SNAPSHOT&interface=com.alibaba.dubbo.registry.RegistryService&pid=19494&timestamp=1532323929545");
//        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry(registerURL, new ZkclientZookeeperTransporter());
//
//        String service = "com.alibaba.dubbo.demo.DemoService";
//        String consumerHost = "10.37.2.112";
//        String providerHost = "10.37.2.113";
//
//        String[] routerNames = new String[] { "filter_not_match", "filter_match" };
//        int priority = 1;
//
//        for (String routerName : routerNames) {
//            URL routerURL = URLUtil.toUrl(service, routerName, priority, URLUtil.getRule(consumerHost, providerHost, "filter_match".equals(routerName)));
//            zookeeperRegistry.unregister(routerURL);
//            zookeeperRegistry.register(routerURL);
//            priority++;
//        }
//
//        ZkClient zkClient = new ZkClient(getZkServer());
//        List<String> rotuers = zkClient.getChildren("/" + Constants.DEFAULT_DIRECTORY + "/" + service + "/" + Constants.ROUTERS_CATEGORY);
//        for (String r : rotuers) {
//            System.out.println(URL.decode(URL.decode(r)));
//        }
//        System.out.println("finished!");
//        System.exit(-1);

    }

    private static String getZkServer() {
        String backupAddress = zookeeperUrl.replace("zookeeper://", "");
        return backupAddress;
    }

    //    private static String toUrlPath(URL url) {
    //        return toCategoryPath(url) + Constants.PATH_SEPARATOR + URL.encode(url.toFullString());
    //    }
    //
    //    private static String toCategoryPath(URL url) {
    //        return toServicePath(url) + Constants.PATH_SEPARATOR + url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
    //    }
    //
    //    private static String toServicePath(URL url) {
    //        String name = url.getServiceInterface();
    //        if (Constants.ANY_VALUE.equals(name)) {
    //            return toRootPath();
    //        }
    //        return toRootPath() + URL.encode(name);
    //    }
    //
    //    private static String toRootPath() {
    //        return "/dubbo/";
    //    }
    //
    //    private static String getZKServer() {
    //        String backupAddress = zookeeperUrl.replace("zookeeper://", "");
    //        String[] zkUrls = new String[2];
    //        zkUrls[0] = backupAddress.substring(0, backupAddress.indexOf("?backup"));
    //        zkUrls[1] = backupAddress.substring(backupAddress.indexOf("=") + 1, backupAddress.length());
    //        return zkUrls[0] + "," + zkUrls[1];
    //    }
}

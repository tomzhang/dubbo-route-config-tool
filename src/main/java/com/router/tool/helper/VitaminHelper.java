package com.router.tool.helper;

import com.vdian.vitamin.client.DynamicVitaminClient;
import com.vdian.vitamin.common.exception.VitaminException;

import java.util.Map;

import static com.router.tool.config.VitaminConfig.*;

public class VitaminHelper {

    public static DynamicVitaminClient vitaminClient = null;

    public static void registerRoutes(String traceId, String routeConfig) throws VitaminException {
        // register command service
        vitaminClient.updateService(PLATFORM_GOURPID, ROUTER_SERVIEID, SERVICE_VERSION, traceId, TOKEN);

        // register route
        vitaminClient.register(PLATFORM_GOURPID, ROUTER_SERVIEID, traceId, routeConfig, TOKEN);
    }

    public static void register(String appName, String traceId, boolean enable)
            throws VitaminException {
        doRegister(appName, traceId, enable);
    }

    public static void unregister(String appName, String traceId) throws VitaminException {
        doRegister(appName, traceId, false);
    }

    public static String queryRoutes(String traceId) throws VitaminException {
        return vitaminClient.lookup(PLATFORM_GOURPID, ROUTER_SERVIEID, traceId);
    }

    public static void remove(String appName, String traceId) throws VitaminException {
        doRemove(appName, traceId);
    }

    public static void doRemove(String appName, String traceId) throws VitaminException {
        // delete platform node config
        vitaminClient.unregister(PLATFORM_GOURPID, PLATFORM_SERVIEID, appName, TOKEN);

        // delete command  node config
        vitaminClient.unregister(COMMANDS_GOURPID, appName, COMMANDS_NODE_KEY, TOKEN);

        // delete command  service config
        vitaminClient.deleteService(COMMANDS_GOURPID, appName, TOKEN);
    }

    public static void doRegister(String appName, String traceId, boolean enabled) throws VitaminException {
        String nodeKey = appName;
        String nodeValue = PLATFORM_CONFIG_TEMPATE.replace("${app}", appName).replace("${desc}", traceId).replace("${enabled}", String.valueOf(enabled));

        // register platform
        vitaminClient.register(PLATFORM_GOURPID, PLATFORM_SERVIEID, nodeKey, nodeValue, TOKEN);

        // register command service
        vitaminClient.updateService(COMMANDS_GOURPID, appName, SERVICE_VERSION, traceId, TOKEN);

        // register command node
        nodeValue = COMMANDS_VALUE_CONFIG_TEMPLATE.replace("${enabled}", String.valueOf(enabled));
        vitaminClient.register(COMMANDS_GOURPID, appName, COMMANDS_NODE_KEY, nodeValue, TOKEN);
    }

    public static void enable(String traceId, boolean enable) throws VitaminException {
        Map<String, String> nodeList = vitaminClient.lookup(PLATFORM_GOURPID, PLATFORM_SERVIEID, false);
        for (Map.Entry<String, String> m : nodeList.entrySet()) {
            String nodeValue = m.getValue();
            if (nodeValue.contains(traceId)) {
                if (enable) {
                    register(m.getKey(), traceId, enable);
                } else {
                    unregister(m.getKey(), traceId);
                }
            }
        }
    }

    public static void remove(String traceId) throws VitaminException {
        Map<String, String> nodeList = vitaminClient.lookup(PLATFORM_GOURPID, PLATFORM_SERVIEID, false);
        for (Map.Entry<String, String> m : nodeList.entrySet()) {
            String nodeValue = m.getValue();
            if (nodeValue.contains(traceId)) {
                remove(m.getKey(), traceId);
            }
        }
    }

    public static DynamicVitaminClient initVitaminClient(String ip) throws VitaminException {
        return new DynamicVitaminClient(null, ip);
    }
}

package com.router.tool.config;

public class VitaminConfig {

    public final static String TOKEN = "mt_test";

    public final static String SERVICE_VERSION = "1.0.0";

    public final static String PLATFORM_CONFIG_TEMPATE = "{\"appName\":\"${app}\",\"description\":\"${desc}\",\"enabled\":${enabled}}";

    public final static String PLATFORM_GOURPID = "mt.platform";

    public final static String PLATFORM_SERVIEID = "applications";

    public final static String ROUTER_SERVIEID = "routes";

    public final static String COMMANDS_GOURPID = "mt.commands";

    public final static String COMMANDS_NODE_KEY = "com.vdian.vtrace.dubbo.ProviderTraceFilter|beforeInvoke";

    public final static String COMMANDS_VALUE_CONFIG_TEMPLATE = " [\n" + "   " + " {\n" + "    " + "    \"serviceName\": \"com.vdian.vtrace.dubbo.ProviderTraceFilter\",\n" + "        " + "    \"methodName\": \"beforeInvoke\",\n" + "       " + "     \"config\": {\n" + "       " + "          \"strategy\": \"DELAY\",\n" + "      " + "          \"delayTime\": 3\n" + "    " + "       },\n" + "     " + "   \"enabled\": ${enabled}\n" + " " + "  }\n" + "]";
}

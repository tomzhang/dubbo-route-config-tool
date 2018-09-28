package vdian.router.tool.config;

/**
 * <p>文件名称：NodeStorageHolder.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 口袋购物 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2018年8月8日</p>
 *
 * @author dengkui@weidian.com
 * @version 1.0
 */
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

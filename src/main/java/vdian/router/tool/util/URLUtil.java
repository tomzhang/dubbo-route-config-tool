package vdian.router.tool.util;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;

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
public class URLUtil {

    public static URL toUrl(String service, String routerName, int priority, String rule) {
        String group = null;
        String version = null;
        String path = service;
        int i = path.indexOf("/");
        if (i > 0) {
            group = path.substring(0, i);
            path = path.substring(i + 1);
        }
        i = path.lastIndexOf(":");
        if (i > 0) {
            version = path.substring(i + 1);
            path = path.substring(0, i);
        }
        return URL.valueOf(Constants.ROUTE_PROTOCOL + "://" + Constants.ANYHOST_VALUE + "/" + path + "?"
                           + Constants.CATEGORY_KEY + "=" + Constants.ROUTERS_CATEGORY
                           + "&router=condition"
                           + "&runtime=false"
                           + "&enabled=true"
                           + "&priority=" + priority
                           + "&force=false"
                           + "&dynamic=false"
                           + "&name=" + routerName
                           + "&" + Constants.RULE_KEY + "=" + URL.encode(rule)
                           + "&version=1.0.0");
    }

    public static String getRule(String consumerHost, String providerHost, boolean isMatch) {
        String rule = null;
        if (isMatch) {
            rule = "method = * & consumer.host = " + consumerHost + " => provider.host = " + providerHost;
        } else {
            rule = "method = * & consumer.host != " + consumerHost + " => provider.host != " + providerHost;
        }
        return rule;
    }
}

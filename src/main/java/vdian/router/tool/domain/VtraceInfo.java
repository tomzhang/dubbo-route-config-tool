package vdian.router.tool.domain;

import org.apache.commons.lang3.StringUtils;
import vdian.router.tool.common.Constants;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class VtraceInfo {
    private String rpcId;
    private String app;
    private String route;
    private String type;
    private String status;
    private String service;
    private String method;
    private String size;
    private String timeLine;
    private String message;
    

    public VtraceInfo() {

    }

    public VtraceInfo(List<String> result) {
        this.rpcId = getContent(result.get(0));
        this.app = getContent(result.get(1));
        this.route = getContent(result.get(2));
        this.type = getContent(result.get(3));
        this.status = getContent(result.get(4));
        String service = getContent(result.get(5));

        if ("DUBBO".equals(getType())) {
            try {
                // <td>com.vdian.thor.common.detailService.getItemInfo:1.0</td>
                // <td>com.vdian.thor.common.detailService.getItemInfo.aa.bb:1.0</td>
                service = service.split(":")[0];
                if (service.startsWith(Constants.THOR_SERVER_PREFIX)) {
                    String thorServiceAndMethodStr = service.split(Constants.THOR_SERVER_PREFIX)[1];
                    String thorMethod = StringUtils.substring(thorServiceAndMethodStr, thorServiceAndMethodStr.indexOf(".") + 1);
                    this.method = thorMethod;
                } else {
                    this.method = StringUtils.substringAfterLast(service, ".");
                }

                this.service = StringUtils.substring(service, 0, service.lastIndexOf("."));
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.service = service;
        }
        this.size = getContent(result.get(6));
        this.timeLine = getContent(result.get(7));
        this.message = getContent(result.get(8));
    }

    public static String getContent(String text) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }
        String regex = "\\<td.*?\\>(.*?)\\<\\/td\\>";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group(1);
        }
        return StringUtils.EMPTY;
    }

    public VtraceInfo(String rpcId, String app, String route, String type, String status, String service, String method, String size, String timeLine, String message) {
        this.rpcId = rpcId;
        this.app = app;
        this.route = route;
        this.type = type;
        this.status = status;
        this.service = service;
        this.method = method;
        this.size = size;
        this.timeLine = timeLine;
        this.message = message;
    }

    public String getRpcId() {
        return rpcId;
    }

    public void setRpcId(String rpcId) {
        this.rpcId = rpcId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override public String toString() {
        return "VtraceVO{" + "rpcId='" + rpcId + '\'' + ", app='" + app + '\'' + ", route='" + route + '\'' + ", type='" + type + '\'' + ", status='" + status + '\'' + ", service='" + service + '\'' + ", size='" + size + '\'' + ", timeLine='" + timeLine + '\'' + ", message='" + message + '\'' + '}';
    }
}

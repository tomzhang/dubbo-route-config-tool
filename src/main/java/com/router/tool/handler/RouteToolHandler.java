package com.router.tool.handler;

import com.router.tool.engine.ToolEngine;
import com.router.tool.helper.VitaminHelper;
import com.vdian.vcommand.handler.CommandHandler;
import com.vdian.vitamin.common.exception.VitaminException;
import com.vdian.vitamin.common.util.CheckerUtil;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Command eg:
 *
 showDetail.append("use route                             ps:使用管道命令telnet 127.0.0.1 4099 第一次链接时候,请先先输入该命令\n");
 showDetail.append("setEnv daily                          ps:默认daily，目前只支持，daily，pre,prod\n");
 showDetail.append("parse ${traceUrl}                     ps:parse http://vtrace.vdian.net/trace?id=1cc300000164823040880a2000840896;\n");
 showDetail.append("enableVitaminConfig true ${traceId}   ps:下线包含这个traceid的【mt.platform以及mt.command】vitamin配置\n");
 showDetail.append("removeVtaminConfig ${traceId}         ps:删除包含这个traceid的【mt.platform以及mt.command】vitamin配置\n");
 showDetail.append("removeRoutesConfig ${traceId}         ps:删除包含这个traceid的,【route】dubbo配置\n");
 */
public class RouteToolHandler implements CommandHandler {

    public String setEnv(String env) throws VitaminException {
        if (StringUtils.isNotEmpty(env)) {
            ToolEngine.getInstance().setEnv(env);
        }
        return "set env = " + env + " success.";
    }

    public String parse(String traceURL) {
        String result = null;
        try {
            result = ToolEngine.getInstance().boss(traceURL);
        } catch (Exception e) {
            result = "parse error." + e.getMessage();
        }
        return result;
    }

    public String enableVitaminConfig(String enable, String traceId) throws VitaminException {
        VitaminHelper.enable(traceId, Boolean.valueOf(enable));
        return "set enableMtConfig = " + enable + " traceId = " + traceId + " success.";
    }

    public String removeRoutesConfig(String traceId) throws VitaminException {
        String routeConfig = VitaminHelper.queryRoutes(traceId);
        if (CheckerUtil.isNotEmpty(routeConfig)) {
            ToolEngine.getInstance().removeRoutes(routeConfig);
        }
        return "Remove Routes Config traceId = " + traceId + " success.";
    }

    public String removeVtaminConfig(String traceId) throws VitaminException {
        VitaminHelper.remove(traceId);
        return "Remove Vitamin Config traceId = " + traceId + " success.";
    }
}

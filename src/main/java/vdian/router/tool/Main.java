package vdian.router.tool;

import com.vdian.vcommand.server.CommandServer;
import vdian.router.tool.engine.ToolEngine;
import vdian.router.tool.handler.RouteToolHandler;
import vdian.router.tool.util.EnvEnum;

import static vdian.router.tool.common.Constants.ONE_HOUR;
import static vdian.router.tool.common.Constants.PORT;

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
 *
 * 提供两种方式：
 * 一种是telnet:
 * eg:telnet 127.0.0.1 4099
 *    use route
 *    parse xxx
 *
 * 一种是http:
 * eg: http://127.0.0.1:4099/execute?command={Param_Key}%20{Param_Value}
 *
 * %20 是等于的转义字符
 * http://127.0.0.1:4099/execute?command=use%20route
   http://127.0.0.1:4099/execute?command=parse%20http%3a%2f%2fvtrace.vdian.net%2ftrace%3f%3d0c3d0000016619d986670a0281ad3c22

 */
//@EnableAutoConfiguration
//@SpringBootApplication
//@ServletComponentScan
public class Main {

    public static void main(String[] args) throws Exception {
        CommandServer.setPort(PORT);
        CommandServer.setReadIdleSeconds(ONE_HOUR);
        CommandServer.setWriteIdleSeconds(ONE_HOUR);
        CommandServer.setAllIdleSeconds(ONE_HOUR);
        CommandServer.getInstance().registerHandler("route", new RouteToolHandler());

        System.setProperty("region.name", "ALL");
        ToolEngine.getInstance().setEnv(EnvEnum.DAILY.env);

        StringBuilder startMessage = new StringBuilder();
        startMessage.append("============================================\n");
        startMessage.append("start finished.\nListener port 4099 already...\n");
        startMessage.append("Please use:\n");
        startMessage.append("telnet 127.0.0.1 4099 connect to server\n");
        StringBuilder showDetail = new StringBuilder("Command eg:\n");
        showDetail.append("use route                             ps:使用管道命令telnet 127.0.01 4099 第一次链接时候,请先先输入该命令\n");
        showDetail.append("setEnv daily                          ps:默认daily，目前只支持，daily，pre,prod\n");
        showDetail.append("parse ${traceUrl}                     ps:parse http://vtrace.vdian.net/trace?id=1cc300000164823040880a2000840896;\n");
        showDetail.append("enableVitaminConfig true ${traceId}   ps:下线包含这个traceid的【mt.platform以及mt.command】vitamin配置\n");
        showDetail.append("removeVtaminConfig ${traceId}         ps:删除包含这个traceid的【mt.platform以及mt.command】vitamin配置\n");
        showDetail.append("removeRoutesConfig ${traceId}         ps:删除包含这个traceid的,【route】dubbo配置\n");

        startMessage.append(showDetail);

        System.out.println(startMessage.toString());

        Thread.currentThread().join();
        //SpringApplication.run(App.class, args);
    }
}

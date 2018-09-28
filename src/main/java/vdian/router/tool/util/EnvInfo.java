package vdian.router.tool.util;


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
public class EnvInfo {

    private String vitaminIP;
    private String zkURL;

    EnvInfo() {
    }

    EnvInfo(String vitaminIP, String zkURL) {
        this.vitaminIP = vitaminIP;
        this.zkURL = zkURL;
    }

    public String getVitaminIp() {
        return vitaminIP;
    }

    public String getZkURL() {
        return zkURL;
    }
}
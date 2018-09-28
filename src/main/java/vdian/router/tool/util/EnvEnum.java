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
public enum EnvEnum {
    PERF("perf", new EnvInfo()), DEV("dev", new EnvInfo()), DAILY("daily", new EnvInfo("10.1.122.104:8080", "zookeeper://zk1.dubbo.daily.idcvdian.com:2181")), PRE("pre", new EnvInfo("10.2.131.144:8080", "zookeeper://zk1.dubbo.pre.idcvdian.com:2181")), PROD("prod", new EnvInfo("10.2.109.92:8080", "zookeeper://zk1.dubbo.idcvdian.com:2181"));

    public String env;

    public EnvInfo envInfo;

    EnvEnum(String env, EnvInfo envInfo) {
        this.env = env;
        this.envInfo = envInfo;
    }

    public static EnvEnum getEnv(String env) {
        for (EnvEnum e : values()) {
            if (e.env.equals(env)) {
                return e;
            }
        }
        return DAILY;
    }

    public String getEnv() {
        return env;
    }

    public EnvInfo getEnvInfo() {
        return envInfo;
    }
}

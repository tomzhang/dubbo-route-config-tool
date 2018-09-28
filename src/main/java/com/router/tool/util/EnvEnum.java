package com.router.tool.util;

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

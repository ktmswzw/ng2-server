package com.xecoder.config;

public class ConfigLoader {

    public enum Env {
        prod,
        dev,
        test
    }

    public static final String APP_ENV_VAR = "App_env";

    public static ConfigLoader loader = new ConfigLoader();

    private Env envVar;

    public static ConfigLoader getInstance() {
        return loader;
    }

    public Env getEnv() {
        if (envVar != null) {
            return envVar;
        }
        synchronized (this) {
            if (envVar == null) {
                String env = System.getProperty(APP_ENV_VAR);
                if (env == null) {
                    envVar = Env.prod;
                } else {
                    envVar = Env.valueOf(env);
                }
            }
        }
        return envVar;
    }

    public boolean isTestEnv() {
        return this.getEnv() == Env.test || this.getEnv() == Env.dev;
    }

    public boolean isProdEnv() {
        return this.getEnv() == Env.prod;
    }

    public boolean isDevEnv() {
        return this.getEnv() == Env.dev;
    }
}

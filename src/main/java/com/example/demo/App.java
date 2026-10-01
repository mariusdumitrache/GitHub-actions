package com.example.demo;

/**
 * Entry point. The environment is chosen at runtime, so the same jar
 * can be promoted from TEST -> UAT -> PROD without rebuilding.
 *
 * Resolution order: system property "app.env", then env var APP_ENV, then "test".
 */
public class App {

    public static void main(String[] args) {
        AppConfig config = AppConfig.load(resolveEnv());

        System.out.println("Environment : " + config.getEnvironment());
        System.out.println("App name    : " + config.getAppName());
        System.out.println("API URL     : " + config.getApiUrl());
        System.out.println("Debug mode  : " + config.isDebug());

        Calculator calc = new Calculator();
        System.out.println("2 + 3 = " + calc.add(2, 3));
    }

    static String resolveEnv() {
        String env = System.getProperty("app.env");
        if (env == null || env.isBlank()) {
            env = System.getenv("APP_ENV");
        }
        return (env == null || env.isBlank()) ? "test" : env;
    }
}

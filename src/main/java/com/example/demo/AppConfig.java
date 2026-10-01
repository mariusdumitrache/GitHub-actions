package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * Loads application-{env}.properties from the classpath.
 */
public class AppConfig {

    public static final Set<String> SUPPORTED_ENVS = Set.of("test", "uat", "prod");

    private final String environment;
    private final Properties props;

    private AppConfig(String environment, Properties props) {
        this.environment = environment;
        this.props = props;
    }

    public static AppConfig load(String env) {
        if (env == null) {
            throw new IllegalArgumentException("Environment must not be null");
        }
        String normalized = env.trim().toLowerCase(Locale.ROOT);
        if (!SUPPORTED_ENVS.contains(normalized)) {
            throw new IllegalArgumentException(
                    "Unknown environment '" + env + "'. Supported: " + SUPPORTED_ENVS);
        }

        String file = "application-" + normalized + ".properties";
        Properties props = new Properties();
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream(file)) {
            if (in == null) {
                throw new IllegalStateException("Config file not found on classpath: " + file);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + file, e);
        }
        return new AppConfig(normalized, props);
    }

    public String getEnvironment() {
        return environment;
    }

    public String getAppName() {
        return props.getProperty("app.name");
    }

    public String getApiUrl() {
        return props.getProperty("api.url");
    }

    public boolean isDebug() {
        return Boolean.parseBoolean(props.getProperty("debug", "false"));
    }
}

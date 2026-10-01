package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AppConfigTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "uat", "prod"})
    void everyEnvironmentHasCompleteConfig(String env) {
        AppConfig config = AppConfig.load(env);
        assertEquals(env, config.getEnvironment());
        assertNotNull(config.getAppName());
        assertTrue(config.getApiUrl().startsWith("https://"));
    }

    @Test
    void prodHasDebugDisabled() {
        assertFalse(AppConfig.load("prod").isDebug());
    }

    @Test
    void environmentNameIsCaseInsensitive() {
        assertEquals("uat", AppConfig.load("  UAT ").getEnvironment());
    }

    @Test
    void unknownEnvironmentThrows() {
        assertThrows(IllegalArgumentException.class, () -> AppConfig.load("dev"));
    }

    /**
     * Runs against whatever environment Maven was started with:
     *   mvn test -Dapp.env=uat   (or -P uat)
     * Handy for a workflow matrix over test / uat / prod.
     */
    @Test
    void selectedEnvironmentLoads() {
        String env = System.getProperty("app.env", "test");
        AppConfig config = AppConfig.load(env);
        System.out.println("Tested against environment: " + config.getEnvironment()
                + " -> " + config.getApiUrl());
        assertEquals(env.toLowerCase(), config.getEnvironment());
    }
}

package org.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
        private static final Properties properties = new Properties();

        static {
            try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new MyRuntimeException("Unable to find config.properties");
                }
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Error loading config.properties");
            }
        }

        public static String getProperty(String key) {
            return properties.getProperty(key);
        }
}

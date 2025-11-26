package util;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    public static void load() {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) {
            System.err.println("Could not load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key, String def) {
        return props.getProperty(key, def);
    }

    public static String get(String key) { return props.getProperty(key); }
}

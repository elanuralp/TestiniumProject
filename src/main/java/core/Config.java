package core;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new RuntimeException("config.properties bulunamadı!");
            PROPS.load(is);
        } catch (Exception e) {
            throw new RuntimeException("config.properties okunamadı", e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key));
    }

    public static int getInt(String key, int def) {
        try { return Integer.parseInt(get(key)); } catch (Exception e) { return def; }
    }

    public static boolean getBool(String key, boolean def) {
        String v = get(key);
        return v == null ? def : v.equalsIgnoreCase("true");
    }
}
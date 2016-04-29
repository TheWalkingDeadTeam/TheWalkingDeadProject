package ua.nc.dao;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Pavel on 22.04.2016.
 */
public class AppSetting {
    static Logger logger = Logger.getLogger(AppSetting.class);
    private static AppSetting instance;

    static {
        instance = new AppSetting();
    }

    private Properties properties;

    private AppSetting() {
        InputStream fileInputStream = null;
        properties = new Properties();
        try {
            fileInputStream = getClass().getResourceAsStream("/sql.properties");
            properties.load(fileInputStream);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (Exception e) {
            logger.error("File not found", e);
        }
    }

    public static String get(String key) {
        return instance.properties.getProperty(key);
    }
}

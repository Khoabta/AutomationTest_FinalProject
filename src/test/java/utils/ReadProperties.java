package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    public static String getPropertyValue(String propertyKey) {
        String value = "";
        try {
            Properties prop = new Properties();
            File configFile = new File("config.properties");
            InputStream stream = new FileInputStream(configFile);
            prop.load(stream);
            value = prop.getProperty(propertyKey);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return value;
    }
}

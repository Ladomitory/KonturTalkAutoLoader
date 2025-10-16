package ru.ladomitory.kontur.talkApi.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

    private static final ClassLoader classLoader = PropertiesReader.class.getClassLoader();
    private static final String MAIN_PROPERTIES_FILE = "central";
    private static final String PROPERTIES_FILE_TYPE = ".properties";
    private static final Map<String, Properties> propertiesMap = new HashMap<>();
    static Logger logger = LogManager.getLogger(PropertiesReader.class);
    static {
        loadProperties(MAIN_PROPERTIES_FILE);
    }

    public static Properties loadProperties(String propertiesFile) {
        if (propertiesFile == null) {
            propertiesFile = MAIN_PROPERTIES_FILE;
        }
        if (propertiesMap.containsKey(propertiesFile)) {
            return propertiesMap.get(propertiesFile);
        }
        URL propertiesResource = classLoader.getResource(propertiesFile + PROPERTIES_FILE_TYPE);
        if (propertiesResource == null) {
            throw new IllegalArgumentException("Error loading " + propertiesFile + PROPERTIES_FILE_TYPE);
        }
        System.out.println("app.properties path: " + propertiesResource.getPath());
        try (InputStream is = classLoader.getResourceAsStream(propertiesFile + PROPERTIES_FILE_TYPE)) {
            if (is != null) {
                System.out.printf("Loading %s from classpath\n", propertiesFile + PROPERTIES_FILE_TYPE);
                Properties properties =new Properties();
                properties.load(is);
                propertiesMap.put(propertiesFile, properties);
                System.out.printf("Loaded %s\n", propertiesFile + PROPERTIES_FILE_TYPE);
                return properties;
            }
            return null;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading " + propertiesFile + PROPERTIES_FILE_TYPE);
        }
    }

    public static boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        return getBooleanProperty(null, propertyName, defaultValue);
    }

    public static boolean getBooleanProperty(String propertiesFile, String propertyName, boolean defaultValue) {
        return getBooleanProperty(propertiesFile, propertyName, defaultValue, false);
    }

    public static boolean getBooleanProperty(String propertiesFile, String propertyName, boolean defaultValue, boolean doNotLog) {
        System.out.printf("Loading property %s from %s\n", propertyName, (propertiesFile == null ? MAIN_PROPERTIES_FILE : propertiesFile) + PROPERTIES_FILE_TYPE);
        Properties properties = loadProperties(propertiesFile);
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue != null && !"".equals(propertyValue)) {
            System.out.println(propertyName + " = \"" + (doNotLog ? "{not logged}" : propertyValue) + "\"");
        } else {
            System.out.println(propertyName + " not defined");
            return defaultValue;
        }
        return "true".equals(propertyValue);
    }

    public static String getStringProperty(String propertyName) {
        return getStringProperty(propertyName, null);
    }

    public static String getStringProperty(String propertyName, String defaultValue) {
        return getStringProperty(propertyName, defaultValue, false);
    }

    public static String getStringProperty(String propertyName, String defaultValue, boolean doNotLog) {
        return getStringProperty(propertyName, defaultValue, doNotLog, null);
    }

    public static String getStringProperty(String propertyName, String defaultValue, boolean doNotLog, String encoding) {
        return getStringProperty(null, propertyName, defaultValue, doNotLog, encoding);
    }

    public static String getStringProperty(String propertiesFile, String propertyName, String defaultValue, boolean doNotLog, String encoding) {
        System.out.printf("Loading property %s from %s\n", propertyName, (propertiesFile == null ? MAIN_PROPERTIES_FILE : propertiesFile) + PROPERTIES_FILE_TYPE);
        Properties properties = loadProperties(propertiesFile);
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue != null && !"".equals(propertyValue)) {
            System.out.println(propertyName + " = \"" + (doNotLog ? "{not logged}" : propertyValue) + "\"");
        } else {
            System.out.println(propertyName + " not defined");
            return defaultValue;
        }
        if (encoding == null || propertyValue == null) {
            return propertyValue;
        }
        try {
            return new String(propertyValue.getBytes("ISO-8859-1"), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getIntProperty(String propertyName) {
        return getIntProperty(propertyName, null);
    }

    public static int getIntProperty(String propertyName, Integer defaultValue) {
        return getIntProperty(propertyName, defaultValue, false);
    }

    public static int getIntProperty(String propertyName, Integer defaultValue, boolean doNotLog) {
        return getIntProperty(null, propertyName, defaultValue, doNotLog);
    }

    public static int getIntProperty(String propertiesFile, String propertyName, Integer defaultValue, boolean doNotLog) {
        System.out.printf("Loading property %s from %s\n", propertyName, (propertiesFile == null ? MAIN_PROPERTIES_FILE : propertiesFile) + PROPERTIES_FILE_TYPE);
        Properties properties = loadProperties(propertiesFile);
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue != null && !"".equals(propertyValue)) {
            System.out.println(propertyName + "= \"" + (doNotLog ? "{not logged" : propertyValue) + "\"");
        } else {
            System.out.println(propertyName + "not defined");
            return defaultValue;
        }
        return Integer.parseInt(propertyValue);
    }
}

package org.hello.core.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    private static final String PROPERTY_FILE_NAME = "config.properties";
    private static final Properties PROPERTIES = getProperties();
    private static Logger log = Logger.getLogger(PropertyUtils.class);

    /**
     *
     * @return
     */
    private static synchronized Properties getProperties() {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(PROPERTY_FILE_NAME));
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch(IOException e) {
            log.warn("Error reading the properties file", e);
            throw new RuntimeException("Error loading the properties file "+e);
        }
    }

    public static String get(Environment param) {
        if(System.getProperty(param.getKey())!=null && !System.getProperty(param.getKey()).trim().equals("")) {
            return System.getProperty(param.getKey());
        } else {
            String value = PROPERTIES.getProperty(param.getKey());
            return (!(value == null || value.trim().equals(""))) ? value : StringUtils.EMPTY;
        }
    }

    public static int getInt(Environment param) {
        return Integer.parseInt(get(param).trim());
    }

    public static long getLong(Environment param) {
        return Long.parseLong(get(param).trim());
    }

    public static double getDouble(Environment param) {
        return Double.parseDouble(get(param).trim());
    }

    public static boolean getBoolean(Environment param) {
        return Boolean.parseBoolean(get(param).trim());
    }
}
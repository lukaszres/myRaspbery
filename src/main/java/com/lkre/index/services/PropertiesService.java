package com.lkre.index.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

    private static final String fileName = "config.properties";
    private InputStream inputStream;
    private Properties properties;

    public PropertiesService() throws IOException {
        properties = new Properties();
        readProperties();
    }

    public String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

    private void readProperties() throws IOException {
        inputStream = getClass().getClassLoader()
                                .getResourceAsStream(fileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
        }
    }
}

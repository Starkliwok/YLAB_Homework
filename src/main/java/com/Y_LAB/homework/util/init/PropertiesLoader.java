package com.Y_LAB.homework.util.init;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties properties;

    private PropertiesLoader(){}
    public static Properties getProperties(String path){
        if (properties == null) {
            properties = new Properties();
            try(FileInputStream in = new FileInputStream(path)){
                properties.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return properties;
    }
}

package com.sunsky;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws Exception{
        Properties properties = new Properties();

        properties.load(Test.class.getClassLoader().getResourceAsStream("jdbc.properties"));

        System.out.println(properties.getProperty("jdbc.username"));

        Map map = new HashMap<String,String>();
    }
}

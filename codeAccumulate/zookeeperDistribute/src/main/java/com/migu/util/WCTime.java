package com.migu.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class WCTime {

    private static Map<String,String> pidTime = new HashMap<>();

    static {
        InputStream inputStream = WCTime.class.getClassLoader().getResourceAsStream("wcinfo.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = null;

        try {
            while((line = bufferedReader.readLine()) != null){
                String[] array = line.split("\\|");
                pidTime.put(array[0],array[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static Map<String,String> getPidTime(){
        return pidTime;
    }

}

package com.sunsky;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileJava {

    public static List<String> getFileContent(String path){
        List<String> list = new ArrayList<String>();

        BufferedReader bufferReader = null;
        try {
            File file = new File(path);
            FileInputStream input = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(input);
            bufferReader = new BufferedReader(inputStreamReader);
            String data = null;
            while((data = bufferReader.readLine()) != null){
                System.out.println(data);
                list.add(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(bufferReader != null){
                try {
                    bufferReader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void main(String[] args){
        List<String> list = ReadFileJava.getFileContent("D:\\logs\\birp.log");
        for(String lt:list){
            System.out.println(lt);
        }
    }
}

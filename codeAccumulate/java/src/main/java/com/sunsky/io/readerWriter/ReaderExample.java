package com.sunsky.io.readerWriter;

import java.io.*;

public class ReaderExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";
        //readByChar(path);
        readBybytes(path);
    }

    public static void readByChar(String path) throws Exception{ //use char to read file
        FileReader fileReader = new FileReader(path);

        BufferedReader reader = new BufferedReader(fileReader);

        String str;
        while ((str = reader.readLine()) != null){
            System.out.println(str);
        }

        reader.close();
    }

    public static void readBybytes(String path) throws Exception{
        FileInputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        InputStreamReader isr = new InputStreamReader(bis,"utf-8");
        BufferedReader br = new BufferedReader(isr);

        String str ;
        while((str = br.readLine()) != null){
            System.out.println(str);
        }

        br.close();
    }

}

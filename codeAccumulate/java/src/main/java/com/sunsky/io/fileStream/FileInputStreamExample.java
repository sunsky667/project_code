package com.sunsky.io.fileStream;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class FileInputStreamExample {
    public static void main(String[] args) throws Exception{
//        read("d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat");
        readBytes("d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdf.dat");

    }

    public static void read(String path) throws Exception{
        InputStream inputStream = new FileInputStream(path);
        int d = -1;
        while((d = inputStream.read()) != -1){
            System.out.println((char)d);
        }
        inputStream.close();
    }

    public static void readBytes(String path) throws Exception{
        InputStream inputStream = new FileInputStream(path);
        byte[] buff = new byte[10];
        int len = -1;
        while((len = inputStream.read(buff)) != -1){
            //System.out.println(len);
            System.out.print(new String (buff,0,len));
        }
    }
}

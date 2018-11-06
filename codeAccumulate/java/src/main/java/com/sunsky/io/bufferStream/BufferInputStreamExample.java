package com.sunsky.io.bufferStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * BufferInputStream是字节处理流，需依赖于字节节点流
 */
public class BufferInputStreamExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";
        read(path);
        readStr(path);
    }
    public static void read(String path) throws Exception{
        InputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        int d = -1;
        while ((d = bis.read()) != -1){
            System.out.println((char)d);
        }
        bis.close();
    }

    public static void readStr(String path) throws Exception{
        InputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        int len = -1;
        byte[] buf = new byte[100];
        while ((len = bis.read(buf)) != -1){
            System.out.println(new String(buf,0,len,"utf-8"));
        }
        bis.close();
    }
}

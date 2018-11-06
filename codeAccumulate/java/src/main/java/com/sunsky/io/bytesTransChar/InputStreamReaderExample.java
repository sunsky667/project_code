package com.sunsky.io.bytesTransChar;

import java.io.*;

public class InputStreamReaderExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";
        read(path);
    }

    public static void read(String path) throws Exception{
        InputStream in = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        InputStreamReader isr = new InputStreamReader(bis,"utf-8");
        int b = -1;
        while((b = isr.read()) != -1){
            System.out.println((char)b);
        }
    }
}

package com.sunsky.io.bufferStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BufferOutputStreamExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";
        write(path);
    }

    public static void write(String path) throws Exception{
        OutputStream out = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        bos.write("this is fucking message fuck fuck".getBytes("utf-8"));
        bos.close();
        System.out.println("write finish ");
    }
}

package com.sunsky.io.fileStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputStreamExample {
    public static void main(String[] args) throws Exception{
        write("d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat");
    }
    public static void write(String path) throws Exception{
        OutputStream out = new FileOutputStream(path);
        out.write("this is fucking message".getBytes("utf-8"));
        System.out.println("write finish");
    }
}

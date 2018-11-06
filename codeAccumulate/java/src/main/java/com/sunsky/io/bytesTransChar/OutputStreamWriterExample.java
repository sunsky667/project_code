package com.sunsky.io.bytesTransChar;

import java.io.*;

public class OutputStreamWriterExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";;
        write(path);
    }

    public static void write(String path) throws Exception{
        OutputStream out = new FileOutputStream(path);
        OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");
        osw.write("this is a fucking message , fuck this message !!".toCharArray());
        osw.close();
    }
}

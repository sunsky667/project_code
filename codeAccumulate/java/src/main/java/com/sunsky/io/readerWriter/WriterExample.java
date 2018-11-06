package com.sunsky.io.readerWriter;

import java.io.*;

public class WriterExample {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"file.dat";
        //bufferWriter(path);
        printWriter(path);
    }

    public static void bufferWriter(String path) throws Exception{
        FileWriter fileWriter = new FileWriter(path,true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write("this is fucking message");
        bw.newLine();
        bw.write("fuck you ");
        bw.flush();
        bw.close();
    }

    public static void printWriter(String path) throws Exception{
        FileOutputStream out = new FileOutputStream(path,true);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        OutputStreamWriter osw = new OutputStreamWriter(bos,"utf-8");
        PrintWriter pw = new PrintWriter(osw);
        pw.println("this is fucking message !!");
        pw.println("hehe");
        pw.close();
    }
}

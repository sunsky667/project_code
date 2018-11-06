package com.sunsky.io.randomAccessFile;

import java.io.File;
import java.io.RandomAccessFile;

public class RandomAccessFileExample {
    public static void main(String[] args) throws Exception{
//        randomWrite("d:"+ File.separatorChar+"tmp"+File.separatorChar);
//        randomRead("d:"+ File.separatorChar+"tmp"+File.separatorChar);
//        randomWriteString("d:"+ File.separatorChar+"tmp"+File.separatorChar);
        randomReadString("d:"+ File.separatorChar+"tmp"+File.separatorChar);
    }

    public static void randomWrite(String path) throws Exception{
        RandomAccessFile rdf = new RandomAccessFile(path + "rdf.dat","rw");
        rdf.write(1);  //TODO can append if the randomAccessFile not close
        rdf.write(97);
        rdf.write(37);
        System.out.println("random write finish");
        rdf.close();
    }

    public static void randomRead(String path) throws Exception{  //only can read first int
        RandomAccessFile rdf = new RandomAccessFile(path + "rdf.dat","r");
        int readValue = rdf.read();
        System.out.println(readValue);
        rdf.close();

    }

    public static void randomWriteString(String path) throws Exception{
        RandomAccessFile rdf = new RandomAccessFile(path + "rdf.dat","rw");
        String str = "红鲤鱼与绿鲤鱼与驴";
        byte[] bytes = str.getBytes("utf-8");
        rdf.write(bytes);
        System.out.println("write str success ");
        rdf.close();
    }

    public static void randomReadString(String path) throws Exception{
        RandomAccessFile rdf = new RandomAccessFile(path + "rdf.dat","r");
        byte[] buf = new byte[100];
        int readLength = rdf.read(buf);
        System.out.println("read length is "+readLength);
        String readStr = new String(buf,0,readLength,"utf-8");
        System.out.println(readStr);
        rdf.seek(6);
        System.out.println(rdf.getFilePointer());
        byte[] buf1 = new byte[100];
        int len1 = rdf.read(buf1);
        System.out.println(new String(buf1,0,len1,"utf-8"));
        rdf.close();
    }
}

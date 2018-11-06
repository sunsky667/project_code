package com.sunsky.io.randomAccessFile;

import java.io.File;
import java.io.RandomAccessFile;

public class SeekOpt {

    public static void main(String[] args) throws Exception{
        opSeek("d:"+ File.separatorChar+"tmp"+File.separatorChar);
    }

    public static void opSeek(String path) throws Exception{
        RandomAccessFile rdf = new RandomAccessFile(path + "rdf.dat","r");
        //first time read
        byte[] buf = new byte[100];
        int readLength = rdf.read(buf);
        System.out.println("read length is "+readLength);
        String readStr = new String(buf,0,readLength,"utf-8");
        System.out.println(readStr);

        //move seek
        rdf.seek(6);
        //get pointer
        System.out.println("after remove seek , file point is : "+rdf.getFilePointer());

        byte[] buf1 = new byte[100];
        int len1 = rdf.read(buf1); //read again from move seek position
        System.out.println(new String(buf1,0,len1,"utf-8"));
        rdf.close();
    }
}

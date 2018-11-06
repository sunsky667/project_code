package com.sunsky.io.randomAccessFile;

import java.io.File;
import java.io.RandomAccessFile;

public class CopyFileNoBuf {
    public static void main(String[] args) throws Exception{
        String src = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdf.dat";
        String dst = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdfcp.dat";
        copy(src,dst);
    }

    public static void copy(String srcName,String dstName) throws Exception{
        RandomAccessFile src = new RandomAccessFile(srcName,"r");
        RandomAccessFile dst = new RandomAccessFile(dstName,"rw");
        int b = -1;
        long start = System.currentTimeMillis();
        while((b = src.read()) != -1){
            dst.write(b);
        }
        long end = System.currentTimeMillis();
        System.out.println("copy finish , take time : "+ (end-start));
        src.close();
        dst.close();
    }
}

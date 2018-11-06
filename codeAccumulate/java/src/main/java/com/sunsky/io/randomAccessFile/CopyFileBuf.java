package com.sunsky.io.randomAccessFile;

import java.io.File;
import java.io.RandomAccessFile;

public class CopyFileBuf {
    public static void main(String[] args) throws Exception{
        String src = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdf.dat";
        String dst = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdfcp.dat";
        copyBuf(src,dst);
    }

    public static void copyBuf(String srcName,String dstName) throws Exception{
        RandomAccessFile src = new RandomAccessFile(srcName,"r");
        RandomAccessFile dst = new RandomAccessFile(dstName,"rw");

        int len = -1;
        byte[] buf = new byte[10*1024];
        long start = System.currentTimeMillis();
        while((len = src.read(buf)) != -1){
            dst.write(buf,0,len);
        }
        long end = System.currentTimeMillis();
        System.out.println("copy finish , take time : "+(start-end));
        src.close();
        dst.close();
    }
}

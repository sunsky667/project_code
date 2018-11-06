package com.sunsky.io.fileChannel;

import java.io.*;
import java.nio.channels.Channels;

public class FileChannel {
    public static void main(String[] args) throws Exception{
//        readChannel();
        readStringWithRandom();  //fuck this , this fucking stupid use iso-8895-1 code
    }

    public static void readChannel() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:"+ File.separatorChar+"tmp"+File.separatorChar+ "rdf.dat","r");
        InputStream inputStream = Channels.newInputStream(randomAccessFile.getChannel());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str ;
        while ((str = reader.readLine()) != null){
            System.out.println("read str is : "+str);
            System.out.println("file point is : "+randomAccessFile.getFilePointer());
        }
    }

    public static void readStringWithRandom() throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:"+ File.separatorChar+"tmp"+File.separatorChar+ "rdf.dat","r");

        String str;

        while ((str = randomAccessFile.readLine()) != null){

            /**
             * RandomAccessFile 的readLine方法得到的str是ISO-8859-1格式
             */
            String newStr = new String(str.getBytes("ISO-8859-1"),"utf-8");

            System.out.println("read str is : "+newStr);
            System.out.println("file point is : "+randomAccessFile.getFilePointer());
        }
    }
}

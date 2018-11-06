package com.sunsky.nio.filechanneltransferFrom;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelTransfer {

    public static void main(String[] args) throws Exception{

        readFile();
    }


    /**
     * 从birp.log文件拷贝到dest.txt文件，中间用fileChannel对接
     * @throws Exception
     */
    public static void readFile() throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile("d:\\logs\\birp.log","rw");
        FileChannel fromFileChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("d:\\logs\\dest.txt","rw");
        FileChannel toFileChannel = toFile.getChannel();

        long position = 0;
        long count = fromFileChannel.size();

        //transferFrom()方法
//        toFileChannel.transferFrom(fromFileChannel,position,count);
        //transferTo()方法
        System.out.println("=====count is ====="+count);
        fromFileChannel.transferTo(position,count,toFileChannel);
        toFileChannel.close();
        fromFile.close();
    }
}

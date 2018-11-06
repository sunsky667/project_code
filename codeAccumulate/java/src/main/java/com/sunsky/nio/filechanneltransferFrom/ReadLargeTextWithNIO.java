package com.sunsky.nio.filechanneltransferFrom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

public class ReadLargeTextWithNIO {

    public static void main(String[] args) throws Exception {
//        copyFile();
        splitFile();

    }

    public static void copyFile() throws Exception {
        FileInputStream inputStream = new FileInputStream("d:\\logs\\birp.log");
        FileChannel fileChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("d:\\logs\\copyout");
        FileChannel fileChannelOut = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(25);
        byteBuffer.clear();
        int readLen = -1;
        while((readLen = fileChannel.read(byteBuffer)) != -1){
//            System.out.println("before flip , byteBuffer position : "+byteBuffer.position()+" limit : "+byteBuffer.limit());
            byteBuffer.flip();
//            System.out.println("after flip , byteBuffer position : "+byteBuffer.position()+" limit : "+byteBuffer.limit());
            fileChannelOut.write(byteBuffer);
            byteBuffer.clear();
        }

        inputStream.close();
        outputStream.close();
    }

    public static void splitFile() throws Exception{

        FileInputStream fileInputStream = new FileInputStream("d:\\logs\\birp.log");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*50);
        int readLen = -1;
        byteBuffer.clear();
        int fileIndex = 1;

        while((readLen = fileChannel.read(byteBuffer)) != -1){

            FileOutputStream fileOutputStream = new FileOutputStream("d:\\logs\\splitfile\\"+ fileIndex+".sp");
            FileChannel outChannel = fileOutputStream.getChannel();

            byteBuffer.flip();
            outChannel.write(byteBuffer);

            byteBuffer.clear();
            fileOutputStream.close();
            fileIndex ++;
        }
        fileInputStream.close();
    }

}

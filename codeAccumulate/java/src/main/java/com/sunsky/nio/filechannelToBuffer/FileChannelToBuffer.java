package com.sunsky.nio.filechannelToBuffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * _______________
 * |             |
 * |    buffer   |--------------|
 * |_____________|              |
 *                              |
 * _______________              |
 * |             |              |          |------------|
 * |    buffer   |--------------|--------->|  channel   |
 * |_____________|              |          |------------|
 *                              |
 * _______________              |
 * |             |              |
 * |    buffer   |--------------|
 * |_____________|
 *
 */
public class FileChannelToBuffer {

    public static void main(String[] args) throws Exception{

        readFile();
//        writeFile();
    }


    /**
     * -----------          -----------
     * |  buffer | =======> | channel |
     * -----------          -----------
     *
     * -----------          -----------
     * | channel | =======> |  buffer |
     * -----------          -----------
     *
     * @throws Exception
     */
    public static void readFile() throws Exception{

        /**
         * 使用Buffer读写数据一般遵循以下四个步骤：
         1.写入数据到Buffer
         2.调用flip()方法
         3.从Buffer中读取数据
         4.调用clear()方法或者compact()方法

         当向buffer写入数据时，buffer会记录下写了多少数据。
         一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
         在读模式下，可以读取之前写入到buffer的所有数据。
         一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
         有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。
         compact()方法只会清除已经读过的数据。
         任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
         */
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdf.dat","r");

        FileChannel fileChannel = randomAccessFile.getChannel();

        //create buffer with capacity of 256 bytes
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);

        //System.out.println("before read , fileChannel position : "+fileChannel.position());
        //TODO 通过FileChannel读取数据到ByteBuffer
        //int byteRead = fileChannel.read(byteBuffer);
        //System.out.println("after read , fileChannel position : "+fileChannel.position());

        //byteRead != -1

        int byteRead = 0;
        while((byteRead = fileChannel.read(byteBuffer)) != -1){  //判断当读取的字节大小不为-1时（即读取到内容）
//            System.out.println("read bytes : "+byteRead);
//
//            System.out.println("==============position value of byteBuffer before flip=============="+byteBuffer.position());
//            System.out.println("==============limit value of byteBuffer before flip=============="+byteBuffer.limit());

            //TODO 一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
            byteBuffer.flip();

//            System.out.println("==============position value of byteBuffer after flip==============="+byteBuffer.position());
//            System.out.println("==============limit value of byteBuffer after flip==============="+byteBuffer.limit());

            //while方法是每个字节读取
//            while (byteBuffer.hasRemaining()){  //如果有剩下的元素
//                System.out.println("read content from file : "+(char)byteBuffer.get());  // read 1 byte at a time
//            }

            //read all data from byteBuffer
           // System.out.println("read content from file : " + new String(byteBuffer.array(),byteBuffer.position(),byteBuffer.limit()));

            System.out.print(new String(byteBuffer.array(),byteBuffer.position(),byteBuffer.limit()));


            byteBuffer.clear();  //读取完毕，清理掉byteBuffer，make buffer ready for writing
            //byteRead = -1;

        }

        fileChannel.close();
        randomAccessFile.close();
    }

    /**
     *
     * -----------          -----------
     * |  buffer | =======> | channel |
     * -----------          -----------
     *
     * @throws Exception
     */
    public static void writeFile() throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\logs\\writefile.txt","rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        String str = "this is a string write to buffer" + System.currentTimeMillis();

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.clear();
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        while(byteBuffer.hasRemaining()){
            fileChannel.write(byteBuffer);
        }

        fileChannel.close();
    }
}

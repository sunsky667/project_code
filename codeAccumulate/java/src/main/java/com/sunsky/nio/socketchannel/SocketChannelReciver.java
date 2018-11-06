package com.sunsky.nio.socketchannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelReciver {

    public static void main(String[] args) throws Exception{

        readSocketChannel();

    }

    public static void readSocketChannel() throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
        while(!socketChannel.finishConnect()){
            System.out.println("not connect !!");
            Thread.sleep(1000);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        int readByte = socketChannel.read(byteBuffer);

        while (readByte != -1){
            System.out.println((char) byteBuffer.get());
        }

        socketChannel.close();
    }


}

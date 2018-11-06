package com.sunsky.nio.socketchannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws Exception{
        handler();
    }


    public static void handler() throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            if(socketChannel != null){
                System.out.println("get client connect");

                //TODO 写模式
                int readByte = socketChannel.read(byteBuffer);
                System.out.println("read bytes is : "+readByte);

                // TODO change to read mode
                while (readByte != -1){
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()){

                        System.out.println("read char "+(char)byteBuffer.get());
                    }
                    byteBuffer.clear();
                }
            }
        }
    }
}

package com.sunsky.nio.socketNIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) {
        try {

            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost",8088));
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);

            socketChannel.write(ByteBuffer.wrap("this is client send message \r\n".getBytes()));

            while(true){
                byteBuffer.clear();
                int readBytes = socketChannel.read(byteBuffer);
                if(readBytes > 0){
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(),0,readBytes));
                    socketChannel.close();
                    break;
                }
            }

        }catch (Exception e){

        }
    }
}

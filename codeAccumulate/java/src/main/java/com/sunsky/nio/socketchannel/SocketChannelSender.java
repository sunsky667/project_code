package com.sunsky.nio.socketchannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelSender {

    public static void main(String[] args) throws Exception{
        writeSocketChannel();
    }


    public static void writeSocketChannel() throws Exception{
        String str = "this is a message from socket ! ";
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.clear();
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }
        socketChannel.close();
    }

}

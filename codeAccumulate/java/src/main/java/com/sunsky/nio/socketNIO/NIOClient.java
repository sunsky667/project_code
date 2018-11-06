package com.sunsky.nio.socketNIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        if(socketChannel.connect(new InetSocketAddress("127.0.0.1",8088))){
            socketChannel.register(selector, SelectionKey.OP_READ);
            socketChannel.write(ByteBuffer.wrap("this is client send message \r\n".getBytes()));

        }else{
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }

        while (true){

            selector.select(1000);
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            System.out.println("=========="+keys.size());

            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if(key.isConnectable()){
                        System.out.println("connectionable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        if(channel.finishConnect()){
                            System.out.println(channel.getRemoteAddress().toString());
                            channel.register(selector,SelectionKey.OP_READ);
                            socketChannel.write(ByteBuffer.wrap("this is client send message @@@@@@@@@@ \r\n".getBytes()));
                        }else{
                            System.out.println("==============fuck====================");
                        }
                    }
                    if(key.isWritable()){
                        System.out.println("writable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();
                        buffer.put("this is client send message @@@@@@@@@@ \r\n".getBytes());
                        buffer.flip();
                        channel.write(buffer);
                        channel.register(selector,SelectionKey.OP_READ);
                    }

                    if(key.isReadable()){
                        System.out.println("readable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        while(channel.read(buffer) > 0){
                            buffer.flip();
                            System.out.println("read from server : "+ new String(buffer.array()));
                        }
                        channel.register(selector,SelectionKey.OP_WRITE);
                    }
                }catch (Exception e){
                    e.printStackTrace();

                    key.cancel();
                    key.channel().close();
                }

            }
        }
    }

}

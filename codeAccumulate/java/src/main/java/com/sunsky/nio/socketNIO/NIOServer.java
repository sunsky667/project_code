package com.sunsky.nio.socketNIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws Exception{
        //获取ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //获取ServerSocket
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8088);
        //ServerSocket绑定端口
        serverSocket.bind(inetSocketAddress);
        //获取Selector
        Selector selector = Selector.open();
        //channel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){
            try {
                selector.select();
            }catch (Exception e){
                e.printStackTrace();
                break;
            }

            //获取selector下的所有SelectionKey
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            //遍历所有SelectionKey
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    if(key.isAcceptable()){
                        System.out.println("acceptable");
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        System.out.println("Accepted connection from : " + client);
                    }

                    if(key.isReadable()){
                        System.out.println("readable");
                        SocketChannel client = (SocketChannel) key.channel();
                        int len = 0;
                        byteBuffer.clear();
                        while ((len = client.read(byteBuffer)) > 0){
                            System.out.println("get len from client : "+len);
                            byteBuffer.flip();
                            System.out.println(new String (byteBuffer.array()));
                        }
                        client.register(selector,SelectionKey.OP_WRITE);
                    }

                    if(key.isWritable() && key.isValid()){
                        System.out.println("writable");
                        SocketChannel client = (SocketChannel)key.channel();
                        byteBuffer.clear();
                        byteBuffer.put("this is a message from server \r\n".getBytes());
                        byteBuffer.flip();
                        client.write(byteBuffer);
                        client.register(selector,SelectionKey.OP_READ);
                    }
                }catch (Exception e){
                    key.cancel();
                    key.channel().close();
                }
            }
        }
    }

}

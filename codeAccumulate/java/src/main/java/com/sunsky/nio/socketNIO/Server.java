package com.sunsky.nio.socketNIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TM 有 BUG，长期收不到客户端发过来的数据
 */
public class Server {
    public static void main(String[] args) throws Exception{
        //建立ServerSocketChannel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //设置服务端口
        serverChannel.socket().bind(new InetSocketAddress(8088));
        //设置阻塞状态
        serverChannel.configureBlocking(false);
        //建立一个Selector
        Selector selector = Selector.open();
        //将serverChannel注册到Selector上去
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){

            //selector接收
            selector.select();
            //接收的所有的key
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            //遍历key
            while(iterator.hasNext()){
                //遍历
                SelectionKey readyKey = iterator.next();
                //若key状态是accept
                if(readyKey.isAcceptable()){
                    System.out.println("accept");
                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    //从readyKey里得到ServerSocketChannel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) readyKey.channel();
                    //从ServerSocketChannel里得到socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置socketChannel阻塞状态  这里如果设置false，server端有可能接受不到数据
                    socketChannel.configureBlocking(true);
                   // socketChannel.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                    //读取数据到ByteBuffer
                    int readLen = socketChannel.read(byteBuffer);
                    System.out.println(readLen);
                    System.out.println(byteBuffer.position());
                    if(readLen > 0){
                        //开始读取
                        byteBuffer.flip();
                        System.out.println("accept string : "+new String(byteBuffer.array(),0,readLen));
                    }
                    //写入数据到client端
                    socketChannel.write(ByteBuffer.wrap("服务器接收到了客户端的数据".getBytes()));
                }else if(readyKey.isReadable()){
                    //key状态是readable
                    System.out.println("readable");
                }else if(readyKey.isWritable()){
                    //key状态是writable
                    System.out.println("writable");
                }
                //手动移除key
                iterator.remove();
            }
        }
    }
}

package com.sunsky.socket.service;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sunsky on 2017/6/26.
 */
public class SocketService {
    public static void main(String[] args){
        try {
            //create a service socket
            ServerSocket serverSocket = new ServerSocket(8088);

            //socket for get client connect
            Socket socket = null;

            //count client connection
            int connectionCount = 0;

            System.out.println("start service, waiting for client connection");

            while(true){

                //accept is a blocking method
                socket = serverSocket.accept();
                ServiceSocketHander serviceSocketHander = new ServiceSocketHander(socket);
                Thread thread = new Thread(serviceSocketHander);
                thread.start();
                connectionCount ++ ;
                System.out.println("number of client connect : "+connectionCount);
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("the client ip address is :"+inetAddress.getHostAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

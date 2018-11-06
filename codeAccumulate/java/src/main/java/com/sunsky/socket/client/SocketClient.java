package com.sunsky.socket.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by sunsky on 2017/6/26.
 */
public class SocketClient {
    public static void main(String[] args){
        try {
            //client create socket
            Socket socket = new Socket("localhost",8088);

            //client send message to service
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("this message is send from client ");
            System.out.println("send message to server success");
            printWriter.flush();
            socket.shutdownOutput();

            //client get message from service
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            System.out.println("waiting for receive message from server");
            String messge = null;
            while ((messge = bufferedReader.readLine()) != null){  //这里是阻塞状态
                System.out.println("client get message from service " + messge);
            }
            System.out.println("=====================");

            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(inputStreamReader !=  null){
                inputStreamReader.close();
            }
            if(inputStream != null){
                inputStream.close();
            }
            if(printWriter != null){
                printWriter.close();
            }
            if(outputStream != null){
                outputStream.close();
            }
            if(socket != null){
                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

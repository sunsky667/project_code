package com.sunsky.socket.service;

import java.io.*;
import java.net.Socket;

/**
 * Created by sunsky on 2017/6/26.
 */
public class ServiceSocketHander implements Runnable{

    private Socket socket;

    public ServiceSocketHander(Socket socket){
        this.socket = socket;
    }

    public void run() {
        //Input
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        //Output
        OutputStream outputStream = null;
        PrintWriter printWriter = null;

        try {
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String message = null;
            while ((message = bufferedReader.readLine()) != null){
                System.out.println("client send message is : "+message);
            }
            socket.shutdownInput();

            Thread.sleep(9000);

            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write("welcome , this message is send by service");
            printWriter.flush();

        }catch (Exception e){
           e.printStackTrace();
        }finally {
            try {
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
}

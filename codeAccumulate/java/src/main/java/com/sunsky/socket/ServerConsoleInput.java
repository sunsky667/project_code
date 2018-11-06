package com.sunsky.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConsoleInput {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream outputStream = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;

        InputStreamReader isr = null;
        BufferedReader bf = null;

        try {
            serverSocket = new ServerSocket(9999);
            socket = serverSocket.accept();

            outputStream = socket.getOutputStream();
            osw = new OutputStreamWriter(outputStream,"utf-8");
            pw = new PrintWriter(osw,true);

            isr = new InputStreamReader(System.in,"utf-8");
            bf = new BufferedReader(isr);

            String str;
            while((str = bf.readLine()) != null){
                if("close console".equals(str)){
                    break;
                }
                pw.println(str);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bf != null){
                try {
                    bf.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(isr != null){
                try {
                    isr.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(pw != null){
                try {
                    pw.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(osw != null){
                try {
                    osw.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(outputStream != null){
                try {
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(socket != null){
                try {
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(serverSocket != null){
                try {
                    serverSocket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}

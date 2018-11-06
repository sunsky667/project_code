package com.sunsky.io.consoleOpt;

import java.io.*;

public class ReadConsoleToFile {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"readConsole.dat";
        readToFile(path);
    }

    public static void readToFile(String path) throws Exception{

        InputStreamReader isr = new InputStreamReader(System.in,"utf-8");
        BufferedReader bf = new BufferedReader(isr);

        FileOutputStream out = new FileOutputStream(path,true);
        OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");
        PrintWriter pw = new PrintWriter(osw,true);

        String str;
        while((str = bf.readLine()) != null){
            if("exit".equals(str)){
                break;
            }
            pw.println(str);
        }

        bf.close();
        pw.close();
    }
}

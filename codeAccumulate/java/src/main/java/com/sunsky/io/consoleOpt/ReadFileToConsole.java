package com.sunsky.io.consoleOpt;

import java.io.*;

public class ReadFileToConsole {
    public static void main(String[] args) throws Exception{
        String path = "d:"+ File.separatorChar+"tmp"+File.separatorChar+"rdf.dat";
        readFiletoConsole(path);
    }

    public static void readFiletoConsole(String path) throws Exception{
        //input from file
        FileInputStream in = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        //output to console
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter pw = new PrintWriter(osw);

        String str;
        while ((str = br.readLine()) != null){
            pw.println(str);
        }

        br.close();
        pw.close();
    }
}

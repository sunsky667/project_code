package com.sunsky;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class KeshengJson {

    public static void main(String[] args) throws Exception{
        new KeshengJson().deal();
    }

    public void deal() throws Exception{
        File file  = new File("D:\\keshengjsonmore.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        File outFile = new File("D:\\errorjson.txt");
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(outFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);

        String str ;

        while((str = bufferedReader.readLine()) != null ){
            String json = str.split("\\|\\|")[0];
            try{
                JSONObject convertedValue = JSON.parseObject(json);
            }catch (Exception e){
                System.out.println(json);
                printWriter.write(str);
                printWriter.println();
            }
        }

        bufferedReader.close();
        printWriter.close();
    }
}

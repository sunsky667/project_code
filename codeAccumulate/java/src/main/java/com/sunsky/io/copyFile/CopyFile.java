package com.sunsky.io.copyFile;

import java.io.*;

public class CopyFile {

    public static void main(String[] args) throws Exception{
        String src = "d:\\HyperLogLogInApacheHive-master.zip";
        String dst = "D:\\tmp\\HyperLogLogInApacheHive-master.zip";
        copy(src,dst);
    }

    public static void copy(String src,String dst){
        File srcFile = new File(src);
        File dstFile = new File(dst);

        byte[] buffer = new byte[1024];

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(srcFile);

            outputStream = new FileOutputStream(dstFile);

            int len = 0;

            while((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
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
        }

    }

}

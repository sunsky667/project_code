package com.sunsky;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class ReadCoder {
    public static void main(String[] args) throws Exception{
//        File file = new File("D:\\error.dat");
//        FileInputStream input = new FileInputStream(file);
//
//        int l =0;
//        while ((l = input.read()) != -1){
//            System.out.println((char) l + " ================== "+l);
//
//        }

        String url = "/pad/getRsaKey?_support=10001001&appVersion=6.0.1&device=iPad&dname=iPad&height=768&mac=f52b6bbadd77591df5e625328be9ffb0071e2a2e&osType=ios&osVersion=9.300000&seqId=3da40b349336de0c8a8cec22bd9c8edc&ticket=&width=1024";

        List<NameValuePair> list =  URLEncodedUtils.parse("/pad/getRsaKey_support=10001001&appVersion=6.0.1&device=iPad&dname=iPad&height=768&mac=f52b6bbadd77591df5e625328be9ffb0071e2a2e&osType=ios&osVersion=9.300000&seqId=3da40b349336de0c8a8cec22bd9c8edc&ticket=&width=1024", Charset.defaultCharset());

        for(NameValuePair nameValuePair:list){
            System.out.println(nameValuePair.getName()+"===="+nameValuePair.getValue());
        }


        System.out.println(getPath(url));

    }

    public static String getPath(String log){
        String log_data = log.substring(0,log.indexOf('?'));
        return log_data;
    }
}

package com.sunsky.decode;

import java.net.URLDecoder;

public class Main {
    public static void main(String[] args) throws Exception{
//        System.out.println(URLDecoder.decode("%E6%B2%B3%E6%BA%90", "utf-8"));
        //String de = URLDecoder.decode("ac%253D2018Q3SSRQWDEJ%2526subac%253Dtool%2526did%253Dff9061e4-98b5-8da7-1f9f-21b1a1e73d5c%2526url%253Dhttps%25253A%25252F%25252Fwww.mgtv.com%25252Fact%25252F2018%25252Fh5%25252F5b9219fe705c572f093520e0%25252F%25253Ftc%25253DDOeoQtXLOIug%252526rankId%25253DrankId11%252523%25252F1%2526ref%253Dhttps%25253A%25252F%25252Fwww.mgtv.com%25252Fact%25252F2018%25252Fh5%25252F5b9219fe705c572f093520e0%25252F%25253Ftc%25253DDOeoQtXLOIug%2526bid%253D21.1.2%2526sessionid%253Dc502c424-72bc-063d-798d-efd08ffa7229%2526ch%253D%2526uuid%253D%2526uvip%253D%2526pref%253D%2526abroad%253D%2526suuid%253Dafde334f-bf92-2ce0-3339-5cf436c43c2a%2526time%253D20180912100408%2526termid%253D4%2526pix%253D360*760%2526ver%253Djmcp-1.0.0","utf-8");

        String feed = URLDecoder.decode("fantuanId%3Db21036bd9581b81d2e376ea08adbca63","utf-8");
        System.out.println(URLDecoder.decode(feed,"utf-8"));
    }
}

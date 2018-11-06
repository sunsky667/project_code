package com.sunsky;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws Exception{
        long memory = Runtime.getRuntime().maxMemory();
        System.out.println(memory/1024/1024);

        URI uri = new URI("https://oa.hunantv.com/seeyon/main.do?method=main");
        System.out.println(uri.getHost());

        Map<String,String> map = new HashMap<>();
        map.put("a","1");
        System.out.println(map.get("b"));
    }
}

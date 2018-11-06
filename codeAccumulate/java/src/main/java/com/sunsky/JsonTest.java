package com.sunsky;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class JsonTest {
    public static void main(String[] args){
        String line = "[{\"time\": \"2017110208\", \"flow\": 500},{\"time\": \"2017110209\", \"flow\": 1000},{\"time\": \"2017110210\", \"flow\": 1500},{\"time\": \"2017110211\", \"flow\": 2000},{\"time\": \"2017110212\", \"flow\": 2500},{\"time\": \"2017110213\", \"flow\": 3000}]";

        String line1 = "[{\"businessId\":\"B9000101601\",\"chnIdSource\":\"9002_10220000000\",\"chnName\":\"\\xE7\\x9C\\x81\\xE5\\x85\\xAC\\xE5\\x8F\\xB8\\xE5\\xBC\\x80\\xE9\\x80\\x9A\\xE7\\x95\\x99\\xE5\\xAD\\x98\",\"lastOrderTime\":\"20141128132845\",\"oprLogin\":\"TEST\",\"orderStatus\":\"1\",\"productId\":\"2028594730\",\"productName\":\"\\xE5\\x92\\x8C\\xE5\\xA8\\xB1\\xE4\\xB9\\x90\\xE4\\xBD\\x93\\xE9\\xAA\\x8C\\xE5\\x8C\\x85\",\"servNumber\":\"13401000000\",\"statisDay\":\"20160229\",\"subBusiId\":\"S9000102409\",\"termProdId\":\"9999\",\"termProdName\":\"\\x5C\\x5CN\"}] ";


        String test = "{\"name\":\"ddd\",\"aget\":12}";

        Object object = JSON.parse(line);

        Map<String,Object> map = (Map<String, Object>) JSON.parse(test);
        System.out.println(map.get("name"));


        if(object instanceof JSONObject){
            System.out.println("object");
        }else if(object instanceof JSONArray){
            System.out.println("array");
        }
        System.out.println("================="+object);

        JSONArray array = JSON.parseArray(line);


        Iterator<Object> iterator = array.iterator();

        while (iterator.hasNext()){
            Map maps = (Map) JSON.parse(iterator.next().toString());
            System.out.println(maps.get("time"));
        }

        List<Map<String,String>> list = new ArrayList<Map<String, String>>();


        Map<String,String> result = new HashMap<String, String>();
        result.put("time","20171106");
        result.put("flow","2000");
        list.add(result);

        Map<String,String> result1 = new HashMap<String, String>();
        result1.put("time","20171107");
        result1.put("flow","2500");
        list.add(result1);

        String jsonStr = JSON.toJSONString(list);

        System.out.println(jsonStr);

        JSONArray array1 = JSON.parseArray(line1);

        Iterator<Object> iterator1 = array1.iterator();

        while (iterator1.hasNext()){
            Map maps = (Map) JSON.parse(iterator1.next().toString());
            System.out.println(maps.get("businessId"));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssFFF");

        String daystr = simpleDateFormat.format(new Date());
        System.out.println(daystr);

    }
}

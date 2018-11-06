package com.sunsky.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

public class AppRedis {

    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        Pipeline pipeline = jedis.pipelined();

        Map<String,Response<Set<String>>> result = new HashMap<String, Response<Set<String>>>();

        List<String> keys = new ArrayList<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");

        for(String key:keys){
            Response<Set<String>> response = pipeline.smembers(key);
            result.put(key,response);
        }
        pipeline.sync();

        System.out.println(result.size());

        for(Map.Entry<String,Response<Set<String>>> rs : result.entrySet()){
            Set<String> set = rs.getValue().get();
            for(String str : set){
                System.out.println(rs.getKey()+" "+str);
            }
        }

    }

}

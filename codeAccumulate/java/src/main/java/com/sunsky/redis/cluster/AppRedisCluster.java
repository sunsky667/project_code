package com.sunsky.redis.cluster;

import redis.clients.jedis.*;

import java.util.Set;

public class AppRedisCluster {

    public static void main(String[] args) {
        /**************test****************/
//        JedisCluster cluster = RedisClusterUtil.getCluster();
//        System.out.println(cluster);
//
//        Map<String,JedisPool> nodeMap = cluster.getClusterNodes();
//
//        String anyHost = nodeMap.keySet().iterator().next();
//
//        System.out.println(anyHost);
//        TreeMap<Long,String> slotHostMap = getSlotHostMap(anyHost);
//
//        //获取槽号
//        int slot = JedisClusterCRC16.getSlot("a");
//
//        //获取到对应的Jedis对象
//
//        Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot));
//
//        Jedis jedis = nodeMap.get(entry.getValue()).getResource();
//
//        Set<String> rs = jedis.smembers("a");
//
//        for(String s : rs){
//            System.out.println(s);
//        }

        /*****************use RedisClusterUtil*******************/
//        Jedis jedis = RedisClusterUtil.getJedisByKey("a");
//        Pipeline pp = jedis.pipelined();
//        Response<Set<String>> response = pp.smembers("a");
//        pp.sync();
//
//        Set<String> set = response.get();
//
//        for(String str : set){
//            System.out.println(str);
//        }
//        jedis.close();




        /*******use RedisCluster*********/
        Jedis jedis = RedisCluster.getJedisByKey("a");
        Pipeline pp = jedis.pipelined();
        Response<Set<String>> response = pp.smembers("a");
        pp.sync();

        Set<String> set = response.get();

        for(String str : set){
            System.out.println(str);
        }
        jedis.close();

        Jedis jedis1 = RedisCluster.getJedisByKey("a");
        Pipeline pp1 = jedis1.pipelined();
        Response<Set<String>> response1 = pp1.smembers("a");
        pp1.sync();

        Set<String> set1 = response1.get();

        for(String str : set1){
            System.out.println(str);
        }
        jedis1.close();

    }



}

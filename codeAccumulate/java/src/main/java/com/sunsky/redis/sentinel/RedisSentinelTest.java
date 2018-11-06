package com.sunsky.redis.sentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;

import java.util.HashSet;
import java.util.Set;

public class RedisSentinelTest {
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "192.168.80.128:63791";
        String hostAndPort2 = "192.168.80.128:63792";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);

        String clusterName = "master-1";
        String password = "grs";

        JedisSentinelPool redisSentinelJedisPool = new JedisSentinelPool(clusterName,sentinels,password);

        Jedis jedis = null;
        try {
            jedis = redisSentinelJedisPool.getResource();
            jedis.set("key", "value");
            System.out.println(jedis.get("a"));
            System.out.println(jedis.get("key"));
            System.out.println(jedis.smembers("d").size());

//            Pipeline pipeline = jedis.pipelined();
//            for(int i = 0 ;i<10;i++){
//                pipeline.sadd("d",String.valueOf(i));
//            }
//            pipeline.sync();
//
//            pipeline.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisSentinelJedisPool.returnBrokenResource(jedis);
        }

        redisSentinelJedisPool.close();
    }
}

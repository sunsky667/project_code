package com.sunsky.redis.cluster;

import java.util.*;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.util.JedisClusterCRC16;

@SuppressWarnings("all")
public class RedisClusterUtil {

	/**
	 * get jedis cluster
	 * @return JedisCluster
	 */
	public static JedisCluster getCluster(){

		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16001));
		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16002));
		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16003));
		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16004));
		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16005));
		jedisClusterNodes.add(new HostAndPort("192.168.80.128", 16006));


		int maxTotal = 100;
		int minIdle = 8;
		int maxIdle = 50;
//      int maxWaitMillis = -1;
		int maxWaitMillis = 1000;
		Boolean testOnBorrow = true;
		Boolean testOnReturn = true;
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setTestOnReturn(testOnReturn);

		JedisCluster jeidsCluster = new JedisCluster(jedisClusterNodes, 300000, 6, poolConfig);
		return jeidsCluster;
	}

	/**
	 *
	 * @param anyHostAndPortStr
	 * @return
	 */
	private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
		TreeMap<Long, String> tree = new TreeMap<Long, String>();
		String parts[] = anyHostAndPortStr.split(":");
		HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
		try{
			Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
			List<Object> list = jedis.clusterSlots();
			for (Object object : list) {
				List<Object> list1 = (List<Object>) object;
				List<Object> master = (List<Object>) list1.get(2);
				String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
				tree.put((Long) list1.get(0), hostAndPort);
				tree.put((Long) list1.get(1), hostAndPort);
			}
			jedis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tree;
	}

	/**
	 * get jedis from cluster by key
	 * @param key
	 * @return
	 */
	public static Jedis getJedisByKey(String key){
		//get cluster
		JedisCluster cluster = RedisClusterUtil.getCluster();
		//get cluster nodes
        Map<String,JedisPool> nodeMap = cluster.getClusterNodes();
		//get any node from cluster node
        String anyHost = nodeMap.keySet().iterator().next();
		//get all slot and host
        TreeMap<Long,String> slotHostMap = getSlotHostMap(anyHost);
		//根据获取槽号
		int slot = JedisClusterCRC16.getSlot(key);
		//获取到对应的Jedis对象
		Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot));
		Jedis jedis = nodeMap.get(entry.getValue()).getResource();
		return jedis;
	}


}

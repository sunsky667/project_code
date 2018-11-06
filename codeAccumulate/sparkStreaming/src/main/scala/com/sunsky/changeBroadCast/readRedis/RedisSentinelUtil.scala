package com.sunsky.changeBroadCast.readRedis

import java.util

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisSentinelPool}

object RedisSentinelUtil extends Serializable {

  private var jedisSentinelPool: JedisSentinelPool = null

  def getJedisSentinelPool(): JedisSentinelPool = {
    if(jedisSentinelPool == null){
      makePool()
    }
    jedisSentinelPool
  }

  private def makePool() : Unit = {

    val sentinels: util.Set[String] = new util.HashSet[String]

    val hostAndPort1: String = "192.168.80.128:63791"
    val hostAndPort2: String = "192.168.80.128:63792"
    sentinels.add(hostAndPort1)
    sentinels.add(hostAndPort2)

    val clusterName: String = "master-1"
    val password: String = "grs"

    if(jedisSentinelPool == null){
      jedisSentinelPool = new JedisSentinelPool(clusterName,sentinels, getPoolConfig(), password)
    }

    val hook = new Thread {
      override def run = jedisSentinelPool.destroy()
    }
    Runtime.getRuntime.addShutdownHook(hook)
  }

  def getJedisClient(): Jedis = {
    getJedisSentinelPool().getResource
  }

  def getPoolConfig(): GenericObjectPoolConfig = {
    val maxTotal = 1000
    val minIdle = 8
    val maxIdle = 100
    val maxWaitMillis = -1
    val testOnBorrow = true
    val testOnReturn = false
    val poolConfig = new GenericObjectPoolConfig()
    poolConfig.setMaxIdle(maxIdle)
    poolConfig.setMaxTotal(maxTotal)
    poolConfig.setMinIdle(minIdle)
    poolConfig.setMaxWaitMillis(maxWaitMillis)
    poolConfig.setTestOnBorrow(testOnBorrow)
    poolConfig.setTestOnReturn(testOnReturn)
    poolConfig
  }

}

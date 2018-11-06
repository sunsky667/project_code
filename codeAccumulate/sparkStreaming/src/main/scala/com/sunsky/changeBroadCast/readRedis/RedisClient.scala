package com.sunsky.changeBroadCast.readRedis

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}

/**
  * Created by sunsky on 2017/2/14.
  */
object RedisClient extends Serializable{

  @transient var pool: JedisPool = null

  def getPool(): JedisPool = {
    if (pool != null) pool
    else makePool()
  }

  def getClient(): Jedis = {
    getPool().getResource
  }

  def makePool(): JedisPool = {

    val host = "192.168.80.128"
    val port = "6379"
    val timeout = "30000"

    println("# redis's host:%s, port:%s".format(host, port))
    if (pool == null)
      pool = new JedisPool(getPoolConfig(), host, port.toInt, timeout.toInt)
    val hook = new Thread {
      override def run() = {
        println("===============destroy pool===============")
        pool.destroy()
      }
    }
    sys.addShutdownHook(hook.run)
    pool
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

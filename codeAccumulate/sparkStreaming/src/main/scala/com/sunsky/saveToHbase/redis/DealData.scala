package com.sunsky.saveToHbase.redis

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import redis.clients.jedis.{Jedis, Pipeline, Response}

import scala.collection.mutable.ListBuffer

object DealData {

  def deal(iterator: Iterator[((String),(List[String],Int))]) = {
    var jedis: Jedis = null
    var pp: Pipeline = null
    import scala.collection.JavaConversions._
    try{

      jedis = RedisClient.getClient()
      pp = jedis.pipelined()

      import scala.collection.mutable.ListBuffer
      val puts = ListBuffer[Put]()

      iterator.foreach(
        data => {
          val list = ListBuffer[Response[java.lang.Long]]()
          var count: Long = 0  //record replicate
          val redisKey = getDayStr()+"_"+data._1
          for (field <- data._2._1)
            list.add(pp.hincrBy(redisKey,field, 1))
          pp.sync()
          list.foreach { res =>
            if (res.asInstanceOf[Response[Long]].get() > 1) count += 1
          }
          jedis.expire(redisKey,120)
          val put = new Put(Bytes.toBytes(getTimeStr+"_"+data._1))
          put.addColumn("info".getBytes(),"uv".getBytes(),(data._2._2 - count).toString.getBytes())
          puts += put
        }
      )
      HBaseUtil.insertData("yunyin",puts.toList)
    }catch {
      case e:Exception => println("redis error " + e.printStackTrace())
    }finally {
      if (pp != null) pp.close()
      if (jedis != null) jedis.close()
    }
  }


  def getTimeStr() : String = {
    val sdf = new SimpleDateFormat("yyyyMMddHHmm")
    val date = new Date()
    sdf.format(date)
  }

  def getDayStr() : String = {
    val sdf = new SimpleDateFormat("yyyyMMdd")
    val date = new Date()
    sdf.format(date)
  }
}

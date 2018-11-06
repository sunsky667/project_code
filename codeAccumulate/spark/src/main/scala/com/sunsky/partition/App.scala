package com.sunsky.partition

import java.net.URL

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("partition")
      .setMaster("local[3]")

    val sparkContext = new SparkContext(sparkConf)

    val hostArray = Array("http://www.baidu.com",
                          "http://www.youku.com",
                          "http://www.tudou.com",
                          "http://www.iteblog.com",
                          "http://www.qq.com",
                          "http://www.qq.com",
                          "http://www.qq.com",
                          "http://www.163.com")

    val rdd = sparkContext.parallelize(hostArray)

    println("rdd partition is : "+rdd.getNumPartitions)

    //获取所有有URL的域名，去重
    val urls = rdd.map(line =>{
      val url = line
      val urls = new URL(url)
      val host = urls.getHost
      host
    }).distinct().collect()

    urls.foreach(println)

    val pairesRdd = rdd.map(line => (line,1))

    println("paire rdd partition is : "+pairesRdd.getNumPartitions)

    val newRdd = pairesRdd.partitionBy(new HostPartitioner(urls))

    println("repartition rdd is : "+newRdd.getNumPartitions)

    newRdd.foreach(println)
  }

}

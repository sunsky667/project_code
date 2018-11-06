package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/18.
  */
object FileLength {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("word count").setMaster("local") //集群可以不指定setMaster()
    //setMaster(spark://Master:7077)
    val sc = new SparkContext(conf)
    val lines = sc.textFile("D://scala//workspace//spark-1.6.0-bin-hadoop2.6//README.md")

    val lineslength = lines.map(line => line.length)

    val length = lineslength.reduce(_+_)

    println(length)
  }
}

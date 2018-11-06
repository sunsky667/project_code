package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Word Count!
 *
 */
object App {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("word count").setMaster("local") //集群可以不指定setMaster()
    //setMaster(spark://Master:7077)
    val sc = new SparkContext(conf)

    val lines = sc.textFile("D://logs//dest.txt")

    val words = lines.flatMap{line => line.split(" ")}

    println(words.getNumPartitions)

    val pairs = words.map{word => (word ,1)}

    val count = pairs.reduceByKey(_+_)

    count.foreach(result => println(result._1+"  : "+ result._2))

  }
}

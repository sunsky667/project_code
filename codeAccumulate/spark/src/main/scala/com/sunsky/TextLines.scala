package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/18.
  */
object TextLines {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("word count").setMaster("local") //集群可以不指定setMaster()
    //setMaster(spark://Master:7077)
    val sc = new SparkContext(conf)
    val lines = sc.textFile("D://logs//birp.log")

    val  mapRDD = lines.map(line => (line,1))

    val resultRDD = mapRDD.reduceByKey(_+_)

    resultRDD.collect().foreach(result => println(result._1+" : "+ result._2))
  }
}

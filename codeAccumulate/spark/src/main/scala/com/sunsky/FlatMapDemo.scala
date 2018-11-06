package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/6/8.
  */
object FlatMapDemo {
  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("flatmap")
//    val rdd = sc.makeRDD(Array(("A","1"),("B","2"),("C","3"),("A","4")),2)
//    rdd.foreach(
//      println
//    )
//
//    rdd.flatMap(word => Array(word._1)).foreach(println)


    val rdd1 = sc.makeRDD(Array("001|ch1|prod1|3","001|ch2|prod1|3","001|ch3|prod1|3","001|ch1|prod2|3",
                                "001|ch1|prod3|3","001|ch1|prod4|3","001|ch2|prod1|3","001|ch1|prod4|3",
                                "001|ch2|prod1|3","001|ch3|prod1|3","001|ch3|prod1|2","001|ch1|prod1|1"))

    val resultRDD = rdd1.flatMap(line => getTu(line)).reduceByKey(_+_)

    rdd1.map(line => line.split("\\|")).foreach(println)
    rdd1.flatMap(line => line.split("\\|")).foreach(println)
    resultRDD.filter(line => line._1._3 == "ch").foreach(println)

    rdd1.map(getTupe).flatMap(r => Array(r._1._1,r._1._2,r._2._1))
      .foreach(rs => println("==========> "+rs))

  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }

  def getTu(line:String)={
    val sb = line.split("\\|")
    val userId = sb(0)
    val channelId = sb(1)
    val productId = sb(2)
    val flow = sb(3).toInt
    Array(((userId,channelId,"ch"),flow),((userId,productId,"pd"),flow))
  }

  def getTupe(line:String): ((String,String),(String,String)) ={
    val ary = line.split("\\|")
    val a = ary(0)
    val b = ary(1)
    val c = ary(2)
    val d = ary(3)
    ((a,b),(c,d))
  }
}

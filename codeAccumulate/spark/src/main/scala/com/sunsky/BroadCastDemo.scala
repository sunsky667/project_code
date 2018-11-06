package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/19.
  */
object BroadCastDemo {
  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("BroadCast")
    //broadCast(sc)
    accumulator(sc)
    sc.stop()
  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }

  def broadCast(sc:SparkContext) = {
    val number = 10
    val broadcastNumber = sc.broadcast(number)
    val data = sc.parallelize(1 to 10000)
    val bn = data.map(_ * broadcastNumber.value)
    bn.collect().foreach(println)
  }

  def accumulator(sc:SparkContext) = {
    val sum = sc.accumulator(0)  //not use in spark2.0
    val data = sc.parallelize(1 to 5)
    data.foreach(item => sum += item)
    println(sum)
  }
}

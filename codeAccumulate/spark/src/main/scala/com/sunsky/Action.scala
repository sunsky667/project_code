package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/19.
  */
object Action {
  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("Action")
    //reduceAction(sc)
    //countAction(sc)
    //takeAction(sc)
    countByKeyAction(sc)
    sc.stop()
  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }

  def reduceAction(sc : SparkContext) = {
    val numbersRDD = sc.parallelize(1 to 100)
    val sum = numbersRDD.reduce((x,y) => x+y)
    println(sum)
  }

  def countAction(sc : SparkContext) = {
    val numbersRDD = sc.parallelize(1 to 100)
    val count = numbersRDD.count()
    println(count)
  }

  def takeAction(sc : SparkContext) = {
    val numbersRDD = sc.parallelize(1 to 100)
    val topN = numbersRDD.take(3)
    topN.foreach(println)
  }

  def countByKeyAction(sc : SparkContext) = {
    val numbers = Array(Tuple2(1,100),Tuple2(2,95),Tuple2(2,65),Tuple2(3,70),Tuple2(3,80))
    val numRDD = sc.parallelize(numbers)
    numRDD.countByKey().foreach(println)
  }
}

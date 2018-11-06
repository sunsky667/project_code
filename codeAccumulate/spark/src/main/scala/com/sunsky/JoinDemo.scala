package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/5/3.
  */
object JoinDemo {

  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("join")
    val rdd1 = sc.makeRDD(Array(("A","1"),("B","2"),("C","3"),("A","4")),2)
    val rdd2 = sc.makeRDD(Array(("A","a"),("C","c"),("D","d"),("A","S")),2)
    val result = rdd1.join(rdd2)
    result.foreach(
//      item =>{
//        println(item._2._2)
//      }
      println
    )

  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}

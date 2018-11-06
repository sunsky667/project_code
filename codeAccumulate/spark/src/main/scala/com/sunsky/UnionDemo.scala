package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/5/4.
  */
object UnionDemo {

  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("union")
    val rdd1 = sc.makeRDD(List(("A","a"),("B","b"),("C","c"),("D","d")))
    val rdd2 = sc.makeRDD(List(("A","a"),("D","d"),("E","e"),("F","f")))
    val rdd = rdd1.union(rdd2)
    val c1 = rdd.map(record => (record,1)).reduceByKey(_+_)
    val c2 = rdd1.map(record => (record,1)).reduceByKey(_+_)
    val sb = c1.join(c2)
    sb.map(item =>{
      val percenct:Float = item._2._2.toFloat/item._2._1.toFloat
      (item._1,percenct)
    }).foreach(
      println
    )
//    reduceresult.foreach(
//      println
//    )
  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}

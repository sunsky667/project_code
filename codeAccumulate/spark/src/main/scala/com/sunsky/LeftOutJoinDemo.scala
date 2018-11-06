package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/5/3.
  */
object LeftOutJoinDemo {

  def main(args: Array[String]): Unit = {
    val sc = getSparkContext("leftOutJoin")
    val rdd1 = sc.makeRDD(Array(("A","1"),("B","2"),("C","3")),2)
    val rdd2 = sc.makeRDD(Array(("A","a"),("C","c"),("D","d"),("A","A")),2)

    val result = rdd1.leftOuterJoin(rdd2)

    result.foreach(
      println
//      item => {
//        if(item._2._2 == None){
//          println(item._1 + "====================fuck=================")
//        }
//      }
    )
  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}

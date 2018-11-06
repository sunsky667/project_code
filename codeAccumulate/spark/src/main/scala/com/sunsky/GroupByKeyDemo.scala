package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

object GroupByKeyDemo {
   def main(args: Array[String]): Unit = {
    val sc = getSparkContext("join")
    val rdd1 = sc.makeRDD(Array(("A",1),("B",2),("C",3),("A",4)),2).reduceByKey(_+_)
    val rdd2 = sc.makeRDD(Array(("A",18),("C",19),("D",20),("A",44),("E",55)),2).reduceByKey(_+_)
    val result = rdd2.leftOuterJoin(rdd1)
    result.foreach(
//      item =>{
//        println(item._2._2)
//      }
      println
    )

     result.mapValues(value => value._1.toFloat/(value._1+value._2.getOrElse(0)).toFloat).foreach(println)


  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}

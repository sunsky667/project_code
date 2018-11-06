package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/21.
  */
object TopNSimple {
  def main(args: Array[String]): Unit = {
    val sc = getContext("simple topN")
    simpleTopN(sc)
    sc.stop()
  }

  def getContext(name : String) = {
    val conf = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(conf)
    sc
  }

  def simpleTopN(sc : SparkContext) = {
    val lines = sc.textFile("D://file//topN.dat")
    val pairsRDD = lines.map(line =>(line.toInt,line))
    val soredRDD = pairsRDD.sortByKey(false).map(pair => pair._2)
    soredRDD.take(5).foreach(println)
  }

}

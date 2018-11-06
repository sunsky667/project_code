package com.sunsky.secondSort

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/20.
  * first key sort then the value sort
  */
object SecondarySortApp {
  def main(args: Array[String]): Unit = {
    val sc = getContext("Secondary Order")
    secondarySort(sc)
    sc.stop()
  }

  def getContext(name : String) = {
    val conf = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(conf)
    sc
  }

  def secondarySort(sc : SparkContext) = {
    val lines = sc.textFile("D://file//sort.dat")
    val pairWithSortkey = lines.map(
      line => (new SecondarySortKey(line.split(" ")(0).toInt,line.split(" ")(1).toInt),line)
    )

    val sorted = pairWithSortkey.sortByKey(false)

    val sortedResult = sorted.map(line => line._2)

    sortedResult.collect().foreach(println)
  }
}

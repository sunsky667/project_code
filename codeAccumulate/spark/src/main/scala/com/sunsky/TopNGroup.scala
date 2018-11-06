package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/22.
  */
object TopNGroup {
  def main(args: Array[String]): Unit = {
    val sc = getContext("group topN")
    groupTopN(sc)
    sc.stop()
  }

  def getContext(name : String) = {
    val conf = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(conf)
    sc
  }

  def groupTopN(sc : SparkContext) = {
    val lines = sc.textFile("D://file//topNGroup.dat")
    val pairsRDD = lines.map(line =>(line.split(" ")(0),line.split(" ")(1).toInt))
    val groupRDD = pairsRDD.groupByKey()

    val sortedRDD = groupRDD.map(item => {
      val groupKey = item._1
      val value = item._2
      val sortValues=value.toList.sortWith(_>_).take(4);
      (groupKey,sortValues)
    })

    val sortedKeyRDD = sortedRDD.sortBy(record=>record._1, false, 1)

    sortedKeyRDD.collect().foreach(println)
  }
}

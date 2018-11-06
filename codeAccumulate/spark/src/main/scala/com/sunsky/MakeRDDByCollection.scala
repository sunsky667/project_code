package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/16.
  */
object MakeRDDByCollection {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("word count").setMaster("local")
    val sc = new SparkContext(conf)
    val numbers = 1 to 100
    val rdd = sc.parallelize(numbers)
    val sum = rdd.reduce(_+_) //1+2=3 3+3=6 6+4=10 10+5 = 15
    println("the sum is "+sum)
  }
}

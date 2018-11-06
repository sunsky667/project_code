package com.sunsky.updateStateByKeyApp

import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext, Time}

/**
  * Created by sunsky on 2017/6/28.
  */
object UpdateStateByKeyDemo {
  def caculate(sparkStreamingContext:StreamingContext) ={

    sparkStreamingContext.checkpoint("hdfs://192.168.80.128:9000/checkPoint")

    val lines = sparkStreamingContext.socketTextStream("192.168.80.128",9999)

    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word,1)).reduceByKey(_+_)

    val result = pairs.updateStateByKey((currValues:Seq[Int],preValue:Option[Int])=>{
        val currValue = currValues.sum  //将目前值相加
        Some(currValue + preValue.getOrElse(0))  //目前值的和加上历史值
    })

    //直接输出结果
    //result.print()

    result.map( word => (word._1,word._2)).print()

    //result.saveAsTextFiles("wordcount")

//    result.filter(_ => _.)
  }
}

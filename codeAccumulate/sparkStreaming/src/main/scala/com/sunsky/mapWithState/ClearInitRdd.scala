package com.sunsky.mapWithState

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming._

/**
  * Created by sunsky on 2017/7/12.
  */
object ClearInitRdd {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("mapWithState").setMaster("local[3]")

    val ssc = new StreamingContext(sparkConf,Minutes(1))

    val dayStr = getDayStr()

    //val ssc = StreamingContext.getOrCreate("hdfs://192.168.80.128:9000/sunsky",()=> new StreamingContext(sparkConf,Minutes(1)))

    ssc.checkpoint("hdfs://192.168.80.128:9000/sunsky")

    val initialRDD:RDD[(String,Int)] = ssc.sparkContext.emptyRDD

    val initialRDD1:RDD[((String,String),Int)] = ssc.sparkContext.emptyRDD

    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.print()

    val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }

    val mapstageRdd = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_).map(record => (record._1,1))
      .mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD))//.filter(rd => rd._2 == 1)

    val mappingFunc1 = (word: (String,String), one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }

    val mapstageRdd1 = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_).map(record => ((record._1,getDayStr()),1))
      .mapWithState(StateSpec.function(mappingFunc1).initialState(initialRDD1))//.filter(rd => rd._2 == 1)


    val result = mapstageRdd.filter(rd => rd._2 == 1)

    val rs = result.map( record => ("samekey",record._2)).reduceByKey(_+_)

      rs.foreachRDD(
        rdd => {
          rdd.foreachPartition(
            prdd => {
              prdd.foreach(
                record => {
                  println("======================> "+record._1 + " : "+record._2)
                }
              )
            }
          )
        }
      )

    rs.print()

    ssc.start()
    ssc.awaitTermination()
  }

  def getMinStr() : String ={
    val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
    val date : Date = new Date()
    val str = sdf.format(date)
    str
  }

  def getDayStr() : String = {
    val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
    val date : Date = new Date()
    val str = sdf.format(date)
    str
  }
}

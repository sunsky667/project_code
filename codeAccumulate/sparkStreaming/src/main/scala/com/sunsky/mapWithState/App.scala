package com.sunsky.mapWithState

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.{Minutes, State, StateSpec, StreamingContext}

/**
  * Created by sunsky on 2017/7/10.
  */
object App {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("mapWithState")//.setMaster("local[*]")

    val ssc = new StreamingContext(sparkConf,Minutes(1))

    ssc.checkpoint("hdfs://node1:9000/sunsky")

    val initialRDD:RDD[(String,Int)] = ssc.sparkContext.emptyRDD

    val lines = ssc.socketTextStream(args(0),9999)

    val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }

    val result = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_).map(record => (record._1,1))
      .mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD)).filter(rd => rd._2 == 1)


    val rs = result.map( record => ("samekey",record._2)).reduceByKey(_+_)

    rs.foreachRDD(
      rdd => rdd.foreachPartition(
        prdd => {
          val dao = PvDao.apply
          prdd.foreach(
            record => {
              println("======================> "+record._1 + " : "+record._2)
              dao.insertOne(getMinStr().reverse,record._2.toString)
            }
          )
        }
      )
    )

//    rs.foreachRDD(
//      (rdd,time) => rdd.foreachPartition(
//        partitionIterator => {
//          val partitionId = TaskContext.getPartitionId()
//          val uniqueId = generateUniqueId(time.milliseconds, partitionId)
//        }
//      )
//    )

    ssc.start()
    ssc.awaitTermination()
  }

  def getMinStr() : String ={
    val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
    val date : Date = new Date()
    val str = sdf.format(date)
    str
  }

}

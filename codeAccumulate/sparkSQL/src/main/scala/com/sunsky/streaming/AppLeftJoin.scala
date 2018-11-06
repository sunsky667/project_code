package com.sunsky.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object AppLeftJoin {
  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(20))
    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    val b = sc.broadcast(sc.textFile("").collect())


    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.map(sp).filter(_ != null).foreachRDD(
      rdd => {
        val tmp = rdd.sparkContext.textFile("D:\\tmp\\weibiao").map(line => (line.split("\\|",-1)(0),line.split("\\|",-1)(1)))
        val rst = rdd.leftOuterJoin(tmp)

//        val usersDF = spark.read.load("d://file//useAndSex.parquet")
//        import spark.implicits._
//        val  sqlrdd = usersDF.map(row => (row.getString(0),row.getString(1))).rdd
//
//        sqlrdd.foreach(
//          l => println("===========> "+l._1+" ========= "+l._2)
//        )

//        rst.foreach(
//          record => println(System.nanoTime()+" key : "+record._2._1 + " value "+record._2._2)
//        )
        rst.filter(s => s._2._2 == None).foreach(
          record => println(record._2._1+"===="+record._1)

        )
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }

  def sp(line:String):(String,String) = {
    if(line != null){
      val array = line.split("\\|",-1)
      if(array.length >= 2){
        (array(0),array(1))
      }else{
        null
      }
    }else{
      null
    }
  }
}

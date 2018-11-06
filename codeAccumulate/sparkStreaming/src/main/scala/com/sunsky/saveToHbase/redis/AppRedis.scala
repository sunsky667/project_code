package com.sunsky.saveToHbase.redis

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Minutes, StreamingContext}

object AppRedis {
  def main(args: Array[String]): Unit = {
    //productid|userid|
    val sparkConf = new SparkConf().setAppName("savehbase").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Minutes(1))
    val lines = ssc.socketTextStream("192.168.80.128",9999)

    lines.print()

    def splitStr(line:String) : (String,String,Int) = {
      val strs = line.split("\\|")
      (strs(0),strs(1),1)
    }

    val uvRDD = lines.map(line => splitStr(line)).map(line => ((line._1),(line._2,line._3)))
      .combineByKey(
        (kv: (String, Int)) => (List(kv._1), kv._2),
        (lkv: (List[String], Int), kv: (String, Int)) => (kv._1 :: lkv._1, lkv._2 + kv._2),
        (lkv1: (List[String], Int), lkv2: (List[String], Int)) => (lkv1._1 ::: lkv2._1, lkv1._2 + lkv2._2),
        new HashPartitioner((ssc.sparkContext.defaultParallelism)) //(protype)(List(userid),pv)
      )

    uvRDD.foreachRDD(
      rdd => rdd.foreachPartition(
        prdd => {
          prdd.foreach(
            record => {
              println("============="+record)
            }
          )
        }
      )
    )

    uvRDD.foreachRDD(
      rdd =>{
        rdd.foreachPartition(
          prdd => DealData.deal(prdd)
        )
      }
    )


    ssc.start()
    ssc.awaitTermination()
  }
}

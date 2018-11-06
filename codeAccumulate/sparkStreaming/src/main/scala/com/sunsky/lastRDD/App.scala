package com.sunsky.lastRDD

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Minutes, StreamingContext}

/**
  * Created by sunsky on 2017/7/4.
  */
object App {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("lastRDD")//.setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Minutes(3))

    ssc.remember(Minutes(6))

    /***********************************************************************/
    val config = HBaseConfiguration.create()
    config.set(TableOutputFormat.OUTPUT_TABLE,"test")
    config.set("hbase.zookeeper.quorum","node1,node2,node3")
    config.set("zookeeper.znode.parent","/hbase")

    val job = Job.getInstance(config)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
    /***********************************************************************/


    var lastRdd:RDD[(String,Int)] = sc.emptyRDD

    val lines = ssc.socketTextStream(args(0),9999)


    val words = lines.flatMap(_.split(" "))


    val pairs = words.map(word => (word,1)).reduceByKey(_+_)


    def getMinStr(): String ={
      val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
      val date : Date = new Date()
      val str = sdf.format(date)
      str
    }

    val s = pairs.transform(
      pairs => {
        val result = pairs.join(lastRdd).filter(
          line => line._2._2 == None
        )
        lastRdd = pairs.union(lastRdd)
        result
      }
    )

    s.map(sb => ("record",1)).reduceByKey(_+_).foreachRDD(
      rdd => rdd.map(
        info => {
          val put = new Put(Bytes.toBytes(getMinStr()))
          put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("count"), Bytes.toBytes(info._2.toString))
          (new ImmutableBytesWritable,put)
        }
      ).saveAsNewAPIHadoopDataset(config)
    )

    ssc.start()
    ssc.awaitTermination()
  }
}

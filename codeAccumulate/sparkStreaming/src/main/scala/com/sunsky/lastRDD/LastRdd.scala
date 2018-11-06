package com.sunsky.lastRDD

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/7/4.
  */
object LastRdd {
  def getSSC(ip:String)={
    val sparkConf = new SparkConf().setAppName("lastRDD")//.setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Minutes(1))

    ssc.remember(Minutes(5))

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

    val lines = ssc.socketTextStream(ip,9999)

    lines.print()

    val words = lines.flatMap(_.split(" "))

    val pairs = words.map(word => (word,1)).reduceByKey(_+_)

    val s = pairs.transform(
      rdd => {
        val result = rdd.join(lastRdd).filter(
                        line => line._2._2 == None
                      )
        lastRdd = rdd.union(lastRdd)
        result
      }
    )

    def getMinStr(): String ={
      val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
      val date : Date = new Date()
      val str = sdf.format(date)
      str
    }


    s.map(line => ("record",1)).reduceByKey(_+_).foreachRDD(
      rdd => rdd.map(
        info => {
          val put = new Put(Bytes.toBytes(getMinStr()))
          put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("count"), Bytes.toBytes(info._2.toString))
          (new ImmutableBytesWritable,put)
        }
      ).saveAsNewAPIHadoopDataset(config)
    )

    ssc
  }
}

package com.sunsky.socketSource

import java.io.{BufferedInputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

/**
  * Created by sunsky on 2017/7/5.
  */
object MainApp {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("savehbase")//.setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Minutes(1))
    val lines = ssc.socketTextStream(args(0),9999)


    lines.print()

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

    val rstRdd = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_)

    lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_)
      .foreachRDD(
        rdd => rdd.foreachPartition(
          prdd => prdd.foreach(
            record => println("============================================> "+record._1)
          )
        )
      )

    def getMinStr(): String ={
      val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
      val date : Date = new Date()
      val str = sdf.format(date)
      str
    }

    lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_).foreachRDD(
      rdd => rdd.map(
        info => {
          val put = new Put(Bytes.toBytes(getMinStr()+info._1))
          put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("cl"), Bytes.toBytes(info._2.toString))
          (new ImmutableBytesWritable,put)
        }
      ).saveAsNewAPIHadoopDataset(job.getConfiguration)
    )

//
//    rstRdd.foreachRDD(
//      rdd => rdd.saveAsTextFile("hdfs://node1:9000/sunsky")
//    )

    ssc.start()
    ssc.awaitTermination()
  }
}

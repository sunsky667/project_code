package com.sunsky.saveToHbase

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Minutes, StreamingContext}

object App {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("savehbase").setMaster("local[3]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Minutes(1))
    val lines = ssc.socketTextStream("192.168.80.128",9999)


    lines.print()

    /***********************************************************************/
    val config = HBaseConfiguration.create()
    config.set(TableOutputFormat.OUTPUT_TABLE,"userinfo")
    config.set("hbase.zookeeper.quorum","192.168.80.128")
    config.set("zookeeper.znode.parent","/hbase")

    val job = Job.getInstance(config)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
    /***********************************************************************/

    def getMinStr(): String ={
      val sdf : SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm")
      val date : Date = new Date()
      val str = sdf.format(date)
      str
    }

    val rstRdd = lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_)

    lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_).foreachRDD(
      rdd => rdd.flatMap(
        info => {
          val put = new Put(Bytes.toBytes(getMinStr()+info._1))
          put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("count"), Bytes.toBytes(info._2.toString))
          val put1 = new Put(Bytes.toBytes(getMinStr()))
          put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("id"), Bytes.toBytes(info._2.toString))
          val outDatas = new Array[(ImmutableBytesWritable,Put)](2)
          outDatas(0) = (new ImmutableBytesWritable,put)
          outDatas(1) = (new ImmutableBytesWritable,put1)
//          (new ImmutableBytesWritable,put1)
          outDatas
        }
      ).saveAsNewAPIHadoopDataset(job.getConfiguration)
    )

    ssc.start()
    ssc.awaitTermination()
  }
}

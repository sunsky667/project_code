package com.sunsky

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/5/4.
  */
object ReadHbase {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\Program Files\\hadoop-common-2.2.0-bin-master")
    val hQuorm = "192.168.80.128"
    val sc = getSparkContext("read hbase")
    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum",hQuorm)
    conf.set("zookeeper.znode.parent","/hbase")
    conf.set(TableInputFormat.INPUT_TABLE,"userinfo")
    conf.set("hbase.zookeeper.property.clientPort", "2181")

    val rs = sc.newAPIHadoopRDD(conf,classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    println(rs.count())

    rs.foreach{case (_,result) =>{
      //获取行键
      val key = Bytes.toString(result.getRow)
      //通过列族和列名获取列
      val id = Bytes.toString(result.getValue("info".getBytes,"id".getBytes))
      val name = Bytes.toInt(result.getValue("info".getBytes,"name".getBytes))
      println("Row key:"+key+" Name:"+name+" Age:"+name)
    }}

    rs.map(
      item => {
        val key = Bytes.toString(item._2.getRow)
        val id = Bytes.toString(item._2.getValue("info".getBytes,"id".getBytes))
        val name = Bytes.toString(item._2.getValue("info".getBytes,"name".getBytes))
        (key,(id,name))
      }
    ).foreach(
      println
    )

  }

  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }
}

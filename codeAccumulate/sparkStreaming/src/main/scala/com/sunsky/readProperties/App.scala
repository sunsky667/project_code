package com.sunsky.readProperties

import java.io._
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext, SparkFiles}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("readproperties")//.setMaster("local[4]")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc,Seconds(5))

    /***get config***/
    val filePath = "/config.properties"

    val in = getClass.getResourceAsStream(filePath)

    val props = new Properties()
    props.load(new BufferedInputStream(in))

    props.keySet().toArray().foreach{
      x => println("key "+ x + " ==value==> "+props.getProperty(x.toString))
    }

    val ip:String = props.getProperty("ip")
    val port:String = props.getProperty("port")
    val path = props.getProperty("path")


    val in1 = getClass.getResourceAsStream("/phone.dat")
    val inputStreamReader = new InputStreamReader(in1)
    val bufferader = new BufferedReader(inputStreamReader)
    println("=================fuck ========= "+bufferader.readLine())
    val str = bufferader.readLine()
    val bcstr = sc.broadcast(str)

    val fuck = bcstr.value

    val list = ReadFile.getFileContent("/home/sunsky/SparkApplication/readproperties/phone.dat")
    val bclist = sc.broadcast(list)

    val map = ConfigUtil.getConfig()

//    val file = new File(path)
//    val f = file.getAbsolutePath
//    println("====================ddd==========="+f)
//    val input = new FileInputStream(file)
//    val inputStreamReader = new InputStreamReader(input)
//    val bufferader = new BufferedReader(inputStreamReader)
//    println("=================fuck ========= "+bufferader.readLine())
//    bufferader.close()

//    val ip = "192.168.80.128"
//    val port = "9999"

    val lines = ssc.socketTextStream(ip,port.toInt)

    lines.print()

    lines.flatMap(_.split(" ")).map(word => (word,1)).reduceByKey(_+_)
      .foreachRDD(
        rdd => rdd.foreachPartition(
          prdd => prdd.foreach(
            record => {
              println("============================================> " + record._1 + " == " + record._2)
              println("====================bcstr===================> "+fuck)
              bclist.value.foreach(
                record => println("======================record==============="+record)
              )
//              println("=================read phone data ===========> "+bufferader.readLine())
//              props.keySet().toArray().foreach{
//                x => println("==================key "+ x + " ==value==> "+props.getProperty(x.toString))
//              }
            }
          )
        )
      )
    ssc.start()
    ssc.awaitTermination()
  }
}

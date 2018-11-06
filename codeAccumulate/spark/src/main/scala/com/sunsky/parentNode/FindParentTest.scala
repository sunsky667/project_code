package com.sunsky.parentNode

import org.apache.spark.{SparkConf, SparkContext}

object FindParentTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("word count").setMaster("local") //集群可以不指定setMaster()
    //setMaster(spark://Master:7077)
    val sc = new SparkContext(conf)

    val lines = sc.textFile("D://logs//node.txt")

    val mapRDD = lines.map(line => parseNode(line))

//    val distinctRDD = mapRDD.distinct()

    Parents.init(mapRDD)

    val resultRDD = mapRDD.map(node => node.getId +"-"+node.getName+"-"+node.getPid+" parents : "+Parents.getParents(node.getId))

    resultRDD.collect().foreach(result => println(result))
  }


  def parseNode(line : String) : Node = {
    val splited = line.split("\\|")
    val node  = new Node()
    node.setId(splited(0))
    node.setName(splited(1))
    node.setPid(splited(2))
    node
  }



}

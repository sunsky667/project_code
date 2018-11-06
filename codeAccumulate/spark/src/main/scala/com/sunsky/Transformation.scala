package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sunsky on 2017/3/18.
  * Spark Transformation case
  */
object Transformation {
  def main(args: Array[String]): Unit = {
    val sc = sparkContext("Transformation")
    //val lines = sc.textFile("D://file//sparkdata.dat")
    //flatMapTransformation(sc)
    //groupByKeyTransformation(sc)
    //reduceByKeyTransformation(sc)
    //joinTransformation(sc)
    cogroupTransformation(sc)
    sc.stop()
  }

  def sparkContext( name : String) = {
    val conf = new SparkConf()
    conf.setAppName(name).setMaster("local") //集群可以不指定setMaster()
    //setMaster(spark://Master:7077)
    val sc = new SparkContext(conf)
    sc
  }

  def mapTransformation(sc : SparkContext) = {
    val nums = sc.parallelize(1 to 10)
    val mapped = nums.map(item => 2*item)
    mapped.collect().foreach(println)
  }

  def filterTransformation(sc : SparkContext) = {
    val nums = sc.parallelize(1 to 10)
    val filtered = nums.filter(item => item % 2 == 0)
    filtered.collect().foreach(println)
  }

  def flatMapTransformation(sc : SparkContext) = {
    val bigdata = Array("scala spark","java hadoop","java hbase")
    val bigdataString = sc.parallelize(bigdata)
    val words = bigdataString.flatMap(line => line.split(" "))
    words.collect().foreach(println)
  }

  def groupByKeyTransformation(sc: SparkContext) = {
    val data = Array(Tuple2(100,"Spark"),Tuple2(100,"zookeeper"),Tuple2(10,"hadoop"),Tuple2(80,"kafka"),Tuple2(80,"hbase"))
    val dataRDD = sc.parallelize(data)
    val grouped = dataRDD.groupByKey()
    grouped.collect().foreach(println)
  }

  def reduceByKeyTransformation(sc : SparkContext) = {
    val lines = sc.textFile("D://scala//workspace//spark-1.6.0-bin-hadoop2.6//README.md")
    val words = lines.flatMap{line => line.split(" ")}
    val pairs = words.map{word => (word ,1)}
    val count = pairs.reduceByKey(_+_)
    count.foreach(result => println(result._1+"  : "+ result._2))
  }

  def joinTransformation(sc : SparkContext) = {
    val studentNames = Array(Tuple2(1,"Spark"),Tuple2(2,"zookeeper"),Tuple2(3,"hadoop"),Tuple2(4,"kafka"),Tuple2(5,"hbase"))
    val studentScores = Array(Tuple2(1,100),Tuple2(2,95),Tuple2(3,65),Tuple2(4,70),Tuple2(5,80))

    val names = sc.parallelize(studentNames)
    val scores = sc.parallelize(studentScores)

    val nameAndscore = names.join(scores)
    nameAndscore.collect().foreach(println)
  }

  def cogroupTransformation(sc : SparkContext) = {
    val studentNames = Array(Tuple2(1,"Spark"),Tuple2(2,"zookeeper"),Tuple2(3,"hadoop"))
    val studentScores = Array(Tuple2(1,100),Tuple2(2,95),Tuple2(3,65),Tuple2(3,70),Tuple2(2,80))

    val names = sc.parallelize(studentNames)
    val scores = sc.parallelize(studentScores)

    val nameScores = names.cogroup(scores)

    nameScores.collect().foreach(println)
//    nameScores.collect().foreach(
//      item => println(item._1 + " : " + item._2._1.foreach(println) + item._2._2.foreach(println))
//    )
  }
}

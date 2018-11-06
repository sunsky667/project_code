package com.sunsky.pkgtrait

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import org.apache.spark.streaming.dstream.DStream

import scala.reflect.ClassTag

class MyDStream extends DStream(ssc = new StreamingContext(new SparkContext(new SparkConf().setAppName("savehbase").setMaster("local[3]")),Seconds(5))){
  override def slideDuration = ???

  override def dependencies = ???

  override def compute(validTime: Time) = ???

  override def map[U](mapFunc: (Nothing) => U)(implicit evidence$2: ClassTag[U]) = super.map(mapFunc)
}

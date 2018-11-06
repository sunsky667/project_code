package com.sunsky.pkgtrait

import org.apache.spark.streaming.dstream.{DStream, MappedDStream}

import scala.reflect.ClassTag

class BizFunction[T:ClassTag] (rdd : DStream[T]){

  def myMap = rdd.print()

}

object BizFunction {
  implicit def addFun(rdd : DStream[String]) = new BizFunction(rdd)
}
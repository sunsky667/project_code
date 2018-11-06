package com.sunsky.partition

import java.net.URL

import org.apache.spark.Partitioner

import scala.collection.mutable

class HostPartitioner(ins : Array[String]) extends Partitioner{

  //create mutable map
  val map = new mutable.HashMap[String,Int]()
  var count = 0

  //traversal input records and put it to mutable map
  for(rd <- ins){
    map += (rd -> count)
    count += 1
  }

  //get number of all partitions
  override def numPartitions: Int = count

  //get each keys partition
  override def getPartition(key: Any): Int = {
    val url = new URL(key.toString)
    val host = url.getHost
    map.getOrElse(host.toString,0)
  }

}

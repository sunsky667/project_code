package com.sunsky.parentNode

import org.apache.spark.rdd.RDD


object Parents{

  var realtionMap : Map[String,Node] = Map[String,Node]()

  def init(rdd: RDD[Node]) = {

    val distinctRDD = rdd.distinct()

    distinctRDD.foreach(
      record => {
        realtionMap = realtionMap.+(record.getId -> record)
      }
    )

    import scala.collection.JavaConversions

    FindParents.initFindParents(JavaConversions.mapAsJavaMap(realtionMap))
  }

  def getParents(id : String): String ={
    FindParents.getParents(id)
  }

}


package com.sunsky

import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.ArrayBuffer

object MapSideJoin {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[3]")
      .setAppName("MapSideJoin")

    val sparkContext = new SparkContext(sparkConf)

    /**
      * map-side-join
      * 取出小表中出现的用户与大表关联后取出所需要的信息
      * */
    //部分人信息(身份证,姓名)
    val peopleInfo = sparkContext.parallelize(Array(("110","lsw"),("222","yyy"))).collectAsMap()
    //全国的学生详细信息(身份证,学校名称,学号...)
    val studentAll = sparkContext.parallelize(Array(("110","s1","211"),
                                                      ("111","s2","222"),
                                                      ("112","s3","233"),
                                                      ("113","s2","244")))

    //将需要关联的小表进行关联
    val peopleBC = sparkContext.broadcast(peopleInfo)

    val f = studentAll.mapPartitions(
      itr => {
        val stuMap = peopleBC.value
        val arrayBuffer = ArrayBuffer[(String,String,String)]()
        itr.foreach{
          case (idCard,school,son) => {
          if(stuMap.contains(idCard)){
            arrayBuffer.+=((idCard, stuMap.getOrElse(idCard,""),school))
          }
        }
        }
        arrayBuffer.iterator
      }
    )


    f.foreach(println)

    val res = studentAll.mapPartitions(
      iter => {
        val stuMap = peopleBC.value
        for((idCard,school,son) <- iter if stuMap.contains(idCard)) yield (idCard, stuMap.getOrElse(idCard,""),school)
      }
    )

    res.foreach(
      println
    )

  }

}

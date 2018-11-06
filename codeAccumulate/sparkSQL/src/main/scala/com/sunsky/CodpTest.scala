package com.sunsky

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import com.sunsky.entity.Person
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object CodpTest {
  def main(args: Array[String]): Unit = {
    val con = new SparkConf().setAppName("sql").setMaster("local[3]")
    val spark = SparkSession
      .builder()
      .config(con)
      .getOrCreate()

    cal(spark)
  }

  def getDate(): String = {
    val sdf = new SimpleDateFormat("yyyyMMdd")
    val date = new Date()
    sdf.format(date)
  }

  def getMin():String = {
    val sdf = new SimpleDateFormat("yyyyMMddHH")
    val date = new Date()
    val hourStr = sdf.format(date)
    val min = date.getMinutes / 5 * 5
    min match {
      case 0 => hourStr+"00"
      case 5 => hourStr+"05"
      case _ => hourStr+min.toString
    }
  }

  def cal(sparkSession: SparkSession): Unit ={
    val seq = Seq(
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,1,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,0,1),
      Contents("aaa","1","ad","电视剧","泡菊花",1000,100,0,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,1,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,0,1),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,1,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,0,1),
      Contents("aaa","1","ad","电视剧","泡菊花",1000,100,0,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,1,0),
      Contents("aaa","1","ad","电视剧","泡菊花",0,0,0,1),
      Contents("aaa","1","ad","电视剧","泡菊花",1000,200,0,0),
      Contents("aaa","1","ios","电视剧","泡菊花",0,0,1,0),
      Contents("aaa","1","ios","电视剧","泡菊花",0,0,0,1),
      Contents("aaa","1","ios","电视剧","泡菊花",1000,100,0,0),
      Contents("bbb","1","ad","电影","狼牙棒",0,0,1,0),
      Contents("bbb","1","ad","电影","狼牙棒",0,0,0,1),
      Contents("bbb","1","ad","电影","狼牙棒",10000,100,0,0)
    )

    val sQLContext = sparkSession.sqlContext
    sQLContext.udf.register("getdate",() => getDate())
    sQLContext.udf.register("getmin",() => getMin())

    val rdd = sparkSession.sparkContext.parallelize(seq)
    import sparkSession.implicits._
    val fuck = rdd.toDF()
    fuck.createOrReplaceTempView("codp_content")


//    val sql = "select " +
//      "t1.product_id," +
//      "t1.os," +
//      "t1.cid," +
//      "t1.pid," +
//      "sum(t1.cv) as scv," +
//      "sum(t1.vv) as svv," +
//      "0 as useduration," +
//      "0 as flow," +
//      "count(distinct(t1.udid)) as cvuv," +
//      "0 as cvvv " +
//      "from codp_content t1 " +
//      "where t1.cv = 1 " +
//      "group by t1.product_id,t1.os,t1.cid,t1.pid union " +
//      "select " +
//      "t2.product_id," +
//      "t2.os," +
//      "t2.cid," +
//      "t2.pid," +
//      "sum(t2.cv) as scv," +
//      "sum(t2.vv) as svv," +
//      "0 as useduration," +
//      "0 as flow," +
//      "0 as cvuv," +
//      "count(distinct(t2.udid)) as cvvv " +
//      "from codp_content t2 " +
//      "where vv = 1 " +
//      "group by t2.product_id,t2.os,t2.cid,t2.pid union " +
//      "select " +
//      "t3.product_id," +
//      "t3.os," +
//      "t3.cid," +
//      "t3.pid," +
//      "0 as scv," +
//      "0 as svv," +
//      "sum(t3.use_duration_sec) as useduration," +
//      "sum(t3.use_flow_kb) as flow ," +
//      "0 as cvuv," +
//      "0 as cvvv " +
//      "from codp_content t3 " +
//      "where vv = 0  " +
//      "and cv = 0 " +
//      "group by t3.product_id,t3.os,t3.cid,t3.pid"

    val sql = "select a.product_id as term_video_type_name," +
      "a.os," +
      "a.cid as content_class," +
      "a.pid as program_id, " +
      "sum(a.scv) as cv," +
      "sum(a.svv) as vv," +
      "sum(a.useduration) as use_duration_sec," +
      "sum(a.flow) as use_flow_kb," +
      "sum(a.cvuv) as cvuv," +
      "sum(a.cvvv) as vvuv " +
      "from ( select " +
      "t1.product_id," +
      "t1.os," +
      "t1.cid," +
      "t1.pid," +
      "sum(t1.cv) as scv," +
      "sum(t1.vv) as svv," +
      "0 as useduration," +
      "0 as flow," +
      "count(distinct(t1.udid)) as cvuv," +
      "0 as cvvv " +
      "from codp_content t1 " +
      "where t1.cv = 1 " +
      "group by t1.product_id,t1.os,t1.cid,t1.pid union " +
      "select " +
      "t2.product_id," +
      "t2.os," +
      "t2.cid," +
      "t2.pid," +
      "sum(t2.cv) as scv," +
      "sum(t2.vv) as svv," +
      "0 as useduration," +
      "0 as flow," +
      "0 as cvuv," +
      "count(distinct(t2.udid)) as cvvv " +
      "from codp_content t2 " +
      "where vv = 1 " +
      "group by t2.product_id,t2.os,t2.cid,t2.pid union " +
      "select " +
      "t3.product_id," +
      "t3.os," +
      "t3.cid," +
      "t3.pid," +
      "0 as scv," +
      "0 as svv," +
      "sum(t3.use_duration_sec) as useduration," +
      "sum(t3.use_flow_kb) as flow ," +
      "0 as cvuv," +
      "0 as cvvv " +
      "from codp_content t3 " +
      "where vv = 0  " +
      "and cv = 0 " +
      "group by t3.product_id,t3.os,t3.cid,t3.pid ) " +
      "as a group by a.product_id,a.os,a.cid,a.pid"

    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "sunsky667")
    sparkSession.sql(sql).createOrReplaceTempView("content_result")

    val resultSql = "select term_video_type_name,os,content_class,program_id,cv,vv,use_duration_sec,use_flow_kb,cvuv,vvuv,getdate() as statis_day,getmin() as statis_min from content_result"
      sparkSession.sql(resultSql).write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/bigdata","codp_content_result",connectionProperties)
  }
}

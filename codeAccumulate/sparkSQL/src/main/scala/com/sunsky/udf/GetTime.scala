package com.sunsky.udf

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.api.java.UDF1

class GetTime extends UDF1[String,String]{

  override def call(t1: String): String = {
    val sdf = new SimpleDateFormat(t1)
    val date = new Date()
    sdf.format(date)
  }

}

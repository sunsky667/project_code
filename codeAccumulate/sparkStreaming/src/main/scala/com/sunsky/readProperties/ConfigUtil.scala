package com.sunsky.readProperties

import java.io.BufferedInputStream
import java.util.Properties

object ConfigUtil {

  def getConfig():Map[String,String] = {

    var configMap = Map[String,String]()
    val filePath = "/config.properties"
    val in = getClass.getResourceAsStream(filePath)
    val props = new Properties()
    props.load(new BufferedInputStream(in))

    props.keySet().toArray().foreach{
      record => {
        configMap = configMap.+(record.toString -> props.getProperty(record.toString))
      }

    }
    configMap
  }

}

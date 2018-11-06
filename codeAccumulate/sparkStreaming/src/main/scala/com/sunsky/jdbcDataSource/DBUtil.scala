package com.sunsky.jdbcDataSource

import java.io.{File, FileInputStream, InputStream}
import java.sql.Connection
import java.util.Properties

import org.apache.commons.dbcp.BasicDataSource
import org.apache.spark.SparkFiles

/**
  * only for spark
  * use datasource to get jdbc connection
  */
object DBUtil {

  @volatile private var dataSource:BasicDataSource = null

  /**
    * init data source
    */
  private def initDataSource() : Unit = {

    try{

      val properties = new Properties()
      properties.load(new FileInputStream(new File(SparkFiles.get("db.properties"))))

      dataSource = new BasicDataSource
      dataSource.setDriverClassName("com.mysql.jdbc.Driver")
      dataSource.setUrl("jdbc:mysql://localhost:3306/bigdata?useUnicode=true&characterEncoding=utf-8")
      dataSource.setUsername("root")
      dataSource.setPassword("sunsky667")
      dataSource.setInitialSize(5)
      dataSource.setMaxActive(10)
    }catch {
      case e:Exception => e.printStackTrace()
    }

  }

  /**
    * get connection from data source
    * @return Connection
    */
  def getConnection() : Connection = {
    synchronized({
      if(dataSource == null){
        initDataSource()
      }
    })
    dataSource.getConnection
  }

  /**
    * close connection
    * @param connection
    */
  def close(connection: Connection) : Unit = {
    if(connection != null){
      try {
        connection.close()
      }catch {
        case e:Exception => e.printStackTrace()
      }
    }
  }

}

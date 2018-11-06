package com.sunsky.mapWithState

import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.slf4j.LoggerFactory

/**
  * Created by Administrator on 2016/11/17.
  */
object HBaseUtil {
  
  val logger = LoggerFactory.getLogger(this.getClass)

  def getConn(): Connection= {
    var conn: Connection = null
    val quorum = "node1,node2,node3"
    val clientPort = "2181"
    //val master = "node1:60000"

    val configuration = HBaseConfiguration.create()

    configuration.set("hbase.zookeeper.quorum", quorum)
    configuration.set("hbase.zookeeper.property.clientPort", clientPort)
    //configuration.set("hbase.master", master)

    println("quorum======> " + quorum)
    println("clientPort======> " + clientPort)
    //println("master======> " + master)
    configuration.set("zookeeper.znode.parent", "/hbase")

    try {
      conn = ConnectionFactory.createConnection(configuration)
    } catch {
      case exception: Exception =>
        logger.info(exception.getMessage)
        None
    }
    conn
  }
  def adColumn(table_name:String,row:String,family:String,qualifier:String,value:String): Unit ={
    //获取表对象
    val tableName=TableName.valueOf(table_name)
    val table=getConn().getTable(tableName)
    //put插入数据
    val put=new Put(Bytes.toBytes(row))
    put.addColumn(family.getBytes(), qualifier.getBytes(), value.getBytes())
    table.put(put)
  }
  //获取表
  def getTable(tableName: String):Table = {
    try {
      val userTable = TableName.valueOf(tableName)
      val table = getConn().getTable(userTable)
      table
    } catch {
      case exception: Exception =>
        println(exception.printStackTrace())
        getConn().close()
        null
    }/*finally {
      getConn().close()
    }*/
  }
  //构造put对象插入数据
  def insertData(tableName: String, puts: List[Put]): Unit = {
    // 引入隐式转换
    import scala.collection.JavaConversions._
    // 获取table，然后调用put函数直接传入参数
    //this.getTable(tableName).put(puts)
    val table = getTable(tableName)
    table.put(puts)
    table.close()
  }
  /**
    * 插入数据
    *  tableName
    *  datas        格式
    *              {"rowkey1": {"family1": {"column1": "value1","column2", "value2",,,,}
    *                           "family2": {.......}
    *              {"rowkey2": {},
    */
  def insertData(tableName: String, datas: Map[String, Map[String, Map[String, String]]]): Unit = {
    import scala.collection.mutable.ListBuffer
    val puts = ListBuffer[Put]()
    datas.foreach {
      case (rowkey, value) => {
        val put = new Put(Bytes.toBytes(rowkey))
        // 循环添加value
        value.foreach {
          case (family, value) => {
            // 循环添加列
            for (elem <- value) {
              put.addColumn(Bytes.toBytes(family), Bytes.toBytes(elem._1), Bytes.toBytes(elem._2))
            }
          }
        }
        // 将添加完value的结果保存到puts集合中
        puts += put
      }
    }
    // 数据插入
    this.insertData(tableName, puts.toList)
  }
  def closeConnection(): Unit = {
    if(!getConn().isClosed) getConn().close()
  }
}
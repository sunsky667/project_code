package com.sunsky.mapWithState

import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Put, Table}
import org.apache.hadoop.hbase.util.Bytes

/**
  * Created by sunsky on 2017/2/14.
  */
object PvDao {
  private val dao: PvDao = new PvDao()

  def apply: PvDao = dao
}

class PvDao private extends Serializable {
  @transient val tn = "test"
  @transient val cf = "cf"
  @transient val cqn = "count"

  def insertOne(rk: String, pv: String): Unit = {
    var con: Connection = null
    var table: Table = null
    try {
      con = HBaseUtil.getConn()
      table = con.getTable(TableName.valueOf(tn))
      val put = new Put(Bytes.toBytes(rk))
      put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cqn), Bytes.toBytes(pv))
      table.put(put)
    } catch {
      case e: Exception => println("insert error ................ "
        + e.printStackTrace())
    } finally {
      if (table != null)
        table.close()
      if (con != null)
        con.close()
    }
  }

  /**
    *
    * def insertData(rowKey : String ,column:String ,columnQulifier : Array[String],columValue:Array[String]): Unit ={
    * val con: Connection = HbUtil.getCon()
    * val table: Table = con.getTable(TableName.valueOf(tn))
    * try {
    * val put = new Put(Bytes.toBytes(rowKey))
    * for(i <- 0 until columnQulifier.length){
    *put.addColumn(Bytes.toBytes(column),columnQulifier(i).getBytes,columValue(i).getBytes)
    * }
    * //put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cqn), Bytes.toBytes(pv))
    *table.put(put)
    * } catch {
    * case e: Exception => println("insert error ................ "
    * + e.printStackTrace())
    * } finally {
    * if (table != null)
    *table.close()
    * if (con != null)
    *con.close()
    * }
    * }
    */
}

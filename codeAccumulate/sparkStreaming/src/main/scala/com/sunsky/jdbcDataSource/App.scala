package com.sunsky.jdbcDataSource

object App {

  def main(args: Array[String]): Unit = {
    val connection = DBUtil.getConnection()
    val sql = "select os,cid,pid from codp_content"

    val preparedStatement = connection.prepareStatement(sql)

    val resultSet = preparedStatement.executeQuery

    while ( {
      resultSet.next
    }) System.out.println(resultSet.getString("os"))

    DBUtil.close(connection)
  }

}

package com.sunsky.connectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {
    public static void main(String[] args) throws Exception{
        Connection connection = JdbcDataSource.getConnection();

        String sql = "select os,cid,pid from codp_content";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("os"));
        }

        JdbcDataSource.close(connection);
    }
}

package com.sunsky.annotation.databaseConnectInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception{
        CustomAnnotationProcessor.processor(DBUtil.class);
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from codp_content");
        while (resultSet.next()){
            System.out.println(resultSet.getString("os"));
        }
    }
}

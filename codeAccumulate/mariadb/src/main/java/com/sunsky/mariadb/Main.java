package com.sunsky.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) throws Exception{
        Connection connection = DBUtil.getCon();
        System.out.println(connection);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from user");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }


    }
}

package com.sunsky.annotation.databaseConnectInfo;

import java.sql.Connection;

public class DBUtil {

    private DBUtil(){

    }

    @CustomConnection(url = "jdbc:mysql://localhost:3306/bigdata?useUnicode=true&characterEncoding=utf-8",
            driverClass = "com.mysql.jdbc.Driver",
            username = "root",
            password = "sunsky667")
    private static Connection connection;


    public static Connection getConnection() {
        return connection;
    }
}

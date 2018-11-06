package com.sunsky.mariadb;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static BasicDataSource basicDataSouce;

    static {
        Properties properties = new Properties();
        try {

            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String passwd = properties.getProperty("db.password");
            String intSize = properties.getProperty("db.intsize");
            String maxSize = properties.getProperty("db.maxsize");

            basicDataSouce = new BasicDataSource();
            basicDataSouce.setDriverClassName(driver);
            basicDataSouce.setUrl(url);
            basicDataSouce.setUsername(user);
            basicDataSouce.setPassword(passwd);
            basicDataSouce.setInitialSize(Integer.parseInt(intSize));
            basicDataSouce.setMaxActive(Integer.parseInt(maxSize));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getCon() throws SQLException{
        return  basicDataSouce.getConnection();
    }

    public static void closeCon(Connection connection) {
        try {
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.sunsky.connectionPool;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 *
 <!-- https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp -->
 <dependency>
 <groupId>commons-dbcp</groupId>
 <artifactId>commons-dbcp</artifactId>
 <version>1.4</version>
 </dependency>
 *
 */

public class JdbcDataSource {

    private static BasicDataSource basicDataSource;

    static {
        Properties properties = new Properties();
        try {

            //get the input steam
            properties.load(JdbcDataSource.class.getClassLoader().getResourceAsStream("db.properties"));
            //get the value from properties file
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String pwd = properties.getProperty("jdbc.password");
            String initSize = properties.getProperty("jdbc.initSize");
            String maxSize = properties.getProperty("jdbc.maxSize");

            //create data source
            basicDataSource = new BasicDataSource();
            //init the data source
            basicDataSource.setDriverClassName(driver);
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(user);
            basicDataSource.setPassword(pwd);
            basicDataSource.setInitialSize(Integer.parseInt(initSize));
            basicDataSource.setMaxActive(Integer.parseInt(maxSize));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

    /**
     * close the connection
     * @param con
     */
    public static void close(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


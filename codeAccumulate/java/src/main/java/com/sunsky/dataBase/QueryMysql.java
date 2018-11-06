package com.sunsky.dataBase;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryMysql {
    private static Logger log = Logger.getLogger(ConnectPool.class);
    private static BasicDataSource bs = null;

    /**
     * 创建数据源
     * @return
     */
    static {
        try {
            if(bs==null){
                bs = new BasicDataSource();
//                bs.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//                bs.setUrl("jdbc:oracle:thin:@172.16.14.202:1521/CDMPDB");
//                bs.setUsername("cdmp_dmt");
//                bs.setPassword("!2012cdmp_dmt!");
                bs.setDriverClassName("com.mysql.jdbc.Driver");
                bs.setUrl("jdbc:mysql://192.168.80.128:3306/bigdata");
                bs.setUsername("root");
                bs.setPassword("sunsky667");
                bs.setMaxActive(200);//设置最大并发数
                bs.setInitialSize(30);//数据库初始化时，创建的连接个数
                bs.setMinIdle(50);//最小空闲连接数
                bs.setMaxIdle(200);//数据库最大连接数
                bs.setMaxWait(1000);
                bs.setMinEvictableIdleTimeMillis(60*1000);//空闲连接60秒中后释放
                bs.setTimeBetweenEvictionRunsMillis(5*60*1000);//5分钟检测一次是否有死掉的线程
                bs.setTestOnBorrow(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 释放数据源
     */
    public static void shutDownDataSource() throws Exception{
        if(bs!=null){
            bs.close();
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection con = null;
        try {
            con=bs.getConnection();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return con;
    }

    /**
     * 关闭连接
     */
    public static void close(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭连接失败");
            }
        }
    }


    public static void main(String[] args){
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("select * from picload");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String operator_name = rs.getString("operator_name");
                System.out.println(operator_name);
                byte[] bytes = rs.getString("operator_name").getBytes();
                System.out.println("=============length "+bytes.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

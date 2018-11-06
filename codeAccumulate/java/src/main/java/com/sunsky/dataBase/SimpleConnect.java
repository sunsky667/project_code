package com.sunsky.dataBase;

import java.sql.*;

public class SimpleConnect {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata", "root", "sunsky667");
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) throws Exception{
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata", "root", "sunsky667");
//        Statement statement = connection.createStatement();


        for(int i=0;i<10;i++){

            Connection connection = SimpleConnect.getConnection();

            Statement statement = connection.createStatement();

            String lastday = "2017110208";

            String today = "2017110209";

            String sql = "select * from flow where time in ( "+lastday +" , "+today +" ) ";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                String time = resultSet.getString("flow");
                System.out.println(time);
            }
            connection.close();
        }


    }

}

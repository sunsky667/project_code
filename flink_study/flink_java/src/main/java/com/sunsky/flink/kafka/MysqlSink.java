package com.sunsky.flink.kafka;


import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MysqlSink extends RichSinkFunction<Tuple3<String,String,Integer>> {

    private String userName;
    private String password;
    private String driverName;
    private String url;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public MysqlSink(){
        userName = "root";
        password = "sunsky667";
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/bigdata";
    }


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName(driverName);
        try {
            connection = DriverManager.getConnection(url,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws Exception {
        try{
            if(preparedStatement != null){
                preparedStatement.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void invoke(Tuple3<String, String, Integer> value) throws Exception {
        if(connection == null){
            Class.forName(driverName);
            try {
                connection = DriverManager.getConnection(url,userName,password);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String sql = "insert into sd(ac,bc,cnt) values (?,?,?)";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,value.f0);
        preparedStatement.setString(2,value.f1);
        preparedStatement.setInt(3,value.f2);

        preparedStatement.execute();
    }

}

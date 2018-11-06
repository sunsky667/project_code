package com.sunsky.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseUtil {

    private static Configuration configuration = null;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "192.168.80.128");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(configuration);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static Table getTable(String tableName){
        Connection connection = getConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        }catch (Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public static void insertOne(String rowkey,String cf,String cl,String value) throws Exception{
        Table table = getTable("dd");
        Put put = new Put(rowkey.getBytes());
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cl),Bytes.toBytes(value));
        table.put(put);
        table.close();
    }

}

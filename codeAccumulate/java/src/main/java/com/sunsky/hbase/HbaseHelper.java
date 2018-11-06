package com.sunsky.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by wangwei on 2017/5/25.
 */
public class HbaseHelper {

    private static Logger log = LogManager.getLogger(HbaseHelper.class.getName());

    /**
     * 生产集群链接配置
     */
    private static Configuration defaultConf;
    static {
        defaultConf = HBaseConfiguration.create();
        defaultConf.set("hbase.zookeeper.quorum","10.200.60.145,10.200.60.146,10.200.60.144");
        defaultConf.set("hbase.zookeeper.property.clientPort","2181");
        defaultConf.set("hbase.master","10.200.60.145:60000");
    }

    /**
     * 测试集群链接配置
     */
    private static Configuration testConf;
    static {
        testConf = HBaseConfiguration.create();
        testConf.set("hbase.zookeeper.quorum","10.200.65.51,10.200.65.62,10.200.65.52");
        testConf.set("hbase.zookeeper.property.clientPort","2181");
        testConf.set("hbase.master","10.200.65.52:60000");
    }

    /**
     * 本地链接集群配置
     */
    private static Configuration localConf;
    static {
        localConf = HBaseConfiguration.create();
        localConf.set("hbase.zookeeper.quorum","192.168.128.129");
        localConf.set("hbase.zookeeper.property.clientPort","2181");
        localConf.set("hbase.master","192.168.128.129:60000");
    }

    /**
     * 获取hbase链接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = null;

        try {
            //TODO
            //connection = ConnectionFactory.createConnection(localConf);
            connection = ConnectionFactory.createConnection(defaultConf);
            //connection = ConnectionFactory.createConnection(testConf);

        } catch (IOException e) {
            log.info("hbase get connection error : " + e.getMessage());
        }
       return connection;
    }


    /**
     * 关闭Hbase连接
     * @param t
     * @param con
     */
    public static void close(Table t, Connection con){

        try {
            if(t != null){
                t.close();
            }
            if(con != null){
                t.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Cell[] get(String tableName,String rowKey){
        Connection connection = HbaseHelper.getConnection();

        Table table = null;
        Cell[] cells = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            cells = result.rawCells();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HbaseHelper.close(table,connection);
        }
        return cells;
    }

    public static ResultScanner findByStartAndEndRow(String tableName,String startRow,String endRow){
        Connection connection = null;
        ResultScanner scanner = null;
        Table table = null;

        try {
            connection = getConnection();
            table = connection.getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();
            scan.setCaching(100);
            scan.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));

            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(endRow));

            scanner = table.getScanner(scan);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
        return scanner ;

    }

    public static ResultScanner findByRowFilter(String tableName,String subStr){
        Connection connection = null;
        ResultScanner scanner = null;
        Table table = null;

        try {
            connection = getConnection();
            table = connection.getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();

            scan.addColumn(Bytes.toBytes("info"),Bytes.toBytes("record"));
            Filter filter=new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(subStr));

            scan.setFilter(filter);

            scanner = table.getScanner(scan);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
        return scanner ;

    }

    public static void insertRow(String tableName,String rowKey,String colFamily,String col,String val){
        Connection connection = getConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col),Bytes.toBytes(val));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
    }

    /**
     *
     * @param tableName
     * @param colFamily
     * @param col
     * @param data
     *
     * data ==>
     * Map(rowkey value)
     */
    public static void insertBatchRow(String tableName,String colFamily,String col,Map<String ,String> data){
        Connection connection = getConnection();
        Table table = null;
        try {

            table = connection.getTable(TableName.valueOf(tableName));
            List<Put> puts = new ArrayList<Put>();

            for(Map.Entry<String,String> entry : data.entrySet()){
                Put put = new Put(Bytes.toBytes(entry.getKey()));
                put.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col),Bytes.toBytes(entry.getValue()));
                puts.add(put);
            }
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
    }

    public static void insertRow(String tableName,String rowKey,String colFamily,Map<String,String> map){
        Connection connection = getConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            for (Map.Entry<String,String> s : map.entrySet()) {
                 put.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(s.getKey()),Bytes.toBytes(s.getValue()));
            }

            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
    }

    public static void insertRow(String tableName,String rowKey,String colFamily,String timestemp,Map<String,String> map){
        Connection connection = getConnection();
        Table table = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            Date date = dateFormat.parse(timestemp);
            Long ts = date.getTime();
            for (Map.Entry<String,String> s : map.entrySet()) {
                put.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(s.getKey()),ts,Bytes.toBytes(s.getValue()));
            }
            table.put(put);
        } catch (IOException e) {
            log.info("insert error !" + e.getMessage());
        } catch (ParseException e) {
            log.info("time parse error!" + e.getMessage());
        }finally {
            close(table,connection);
        }
    }

    public static void deleteRows(String tableName,List<String> list){
        Connection connection = getConnection();
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            List<Delete> delList = new ArrayList<Delete>();

            for (String s : list) {

                Delete delete = new Delete(Bytes.toBytes(s));
                delList.add(delete);
            }
            table.delete(delList);
        } catch (IOException e) {
            log.info("hbase delete error : " + e.getMessage());
        } finally {
            close(table,connection);
        }

    }

    public static ResultScanner findByTs(String tableName,String time){
        Connection connection = getConnection();
        List<Long> list = new ArrayList<Long>();
        ResultScanner scanner = null;
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date parse = dateFormat.parse(time);
            long ts = parse.getTime();
            list.add(ts);
            Scan scan = new Scan();
            scan.setCaching(1000);
            Filter filter = new TimestampsFilter(list);
            scan.setFilter(filter);
            scanner = table.getScanner(scan);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            close(table,connection);
        }
        return scanner;
    }


    /*****************add by liulili*********************/
    public static ResultScanner findByRegRowKey(String tableName,String regexStr){
        Connection connection = null;
        HTable hTable = null;
        ResultScanner scanner = null;
        try {
            connection  = getConnection();;
            hTable = (HTable)connection.getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();

            Filter filter= new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(regexStr));
            scan.setFilter(filter);

            scanner = hTable.getScanner(scan);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(hTable,connection);
        }
        return scanner;
    }

}

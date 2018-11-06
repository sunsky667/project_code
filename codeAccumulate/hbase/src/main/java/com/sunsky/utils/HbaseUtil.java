package com.sunsky.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sunsky on 2017/4/24.
 */
public class HbaseUtil {

    private static org.apache.log4j.Logger log = LogManager.getLogger(HbaseUtil.class.getName());

    private static Configuration conf = null;
    private static Configuration localConf = null;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "10.200.60.145,10.200.60.146,10.200.60.144");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.master", "10.200.60.145:60000");
    }

    static {
        localConf = HBaseConfiguration.create();
        localConf.set("hbase.zookeeper.quorum", "192.168.80.128");
        localConf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    /**
     *get HBase connection
     * @return HBase Connection
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(localConf);
        }catch (Exception e){
            log.info("Can't connect to hbase ===> " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @param table
     * @param con
     * close HBase connection
     */
    public static void close(Table table,Connection con){
        try{
            if(table != null){
                table.close();
            }
            if(con != null){
                con.close();
            }
        }catch (Exception e){
            log.warn("close connection error ===> "+e.getMessage());
        }
    }

    /**
     * query by rowkey
     * @param tableName
     * @param rowKey
     * @return Cells
     */
    public static Cell[] queryByRowKey(String tableName , String rowKey){
        Connection connection = getConnection();
        Table table = null;
        Cell[] cells = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            Result res = table.get(get);
            cells = res.rawCells();
        } catch (IOException e) {
            log.warn("query from hbase error ===> " + e.getMessage());
        } finally {
            close(table, connection);
        }
        return cells;
    }

    /**
     *query by colFamily
     * @param tableName
     * @param rowkey
     * @param colFamily
     * @return Cells
     */
    public static Cell[] queryBycolFamily(String tableName, String rowkey, String colFamily) {
        Connection connection = getConnection();
        Table table = null;
        Cell[] cells = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(Bytes.toBytes(colFamily));
            Result res = table.get(get);
            cells = res.rawCells();
        } catch (IOException e) {
            log.warn("query from hbase error ===> " + e.getMessage());
        } finally {
            close(table, connection);
        }
        return cells;
    }

    /**
     *[{"rowKey1":"rkvalue1","col1":"value1","col2":"value2"},{"rowKey2":"rkvalue2","col1":"value1","col2":"value2"}]
     * @param tableName
     * @param regRowKey
     * @return
     */
    public static List<Map<String, Object>> queryByRegRowKey(String tableName, String regRowKey) {
        Connection connection = null;
        HTable hTable = null;
        ResultScanner scanner = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            connection = getConnection();
            hTable = (HTable) connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            //设置hbase查询数据的缓存， 减少网络消耗
            scan.setCaching(10000);
            Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(regRowKey));
            scan.setFilter(filter);
            //找出所有符合正则rowkey的数据
            scanner = hTable.getScanner(scan);
            for (Result r : scanner) {
                Map<String, Object> map = new HashMap<String, Object>();
                final String rowKeyName = "rowKey";
                boolean b = true;
                for (Cell c : r.rawCells()) {
                    if (b) {
                        b = false;
                        //put rowkey和行数据
                        map.put(rowKeyName, new String(CellUtil.cloneRow(c)));
                    }
                    //存储 列名和列值
                    map.put(new String(CellUtil.cloneQualifier(c)), new String(CellUtil.cloneValue(c)));
                }
                list.add(map);
            }
        } catch (Exception e) {
            log.warn("hbase query error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(hTable, connection);
        }
        return list;
    }

    /**
     *
     * @param tableName
     * @param startRow
     * @param stopRow
     * @return
     */
    public static List<Map<String, Object>> findByStartAndEndRow(String tableName, String startRow, String stopRow) {
        Connection connection = null;
        HTable hTable = null;
        ResultScanner scanner = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            connection = getConnection();
            hTable = (HTable) connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            //设置hbase查询数据的缓存， 减少网络消耗
            scan.setCaching(10000);
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(stopRow));
            scanner = hTable.getScanner(scan);
            for (Result r : scanner) {
                Map<String, Object> map = new HashMap<String, Object>();
                final String rowKeyName = "rowKey";
                boolean b = true;
                for (Cell c : r.rawCells()) {
                    if (b) {
                        b = false;
                        //put rowkey和行数据
                        map.put(rowKeyName, new String(CellUtil.cloneRow(c)));
                    }
                    //存储 列名和列值
                    map.put(new String(CellUtil.cloneQualifier(c)), new String(CellUtil.cloneValue(c), "UTF-8"));
                }
                list.add(map);
            }
        } catch (Exception e) {
            log.warn("hbase scan error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(hTable, connection);
        }
        return list;
    }

    public static Object[] batchQueryRowKey(String tableName, String familyName, List<String> rowkeys) {
        Connection connection = getConnection();
        Table table = null;
        List<Row> batch = new ArrayList<Row>();
        for (String rowkey : rowkeys) {
            Get get = new Get(Bytes.toBytes(rowkey));
            get.addFamily(Bytes.toBytes(familyName));
            batch.add(get);
        }
        Object[] results = new Object[batch.size()];
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            table.batch(batch,results);
        } catch (IOException e) {
            log.warn("query hbase error : " + e.getMessage());
        } catch (InterruptedException e) {
            log.warn("query hbase error : " + e.getMessage());
        } finally {
            close(table, connection);
        }
        return results;
    }

    public static Object[] batchQueryRowKey(String tableName, List<String> rowkeys) {
        Connection connection = getConnection();
        Table table = null;
        List<Row> batch = new ArrayList<Row>();
        for (String rowkey : rowkeys) {
            Get get = new Get(Bytes.toBytes(rowkey));
            batch.add(get);
        }
        Object[] results = new Object[batch.size()];
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            table.batch(batch,results);
        } catch (IOException e) {
            log.warn("query hbase error : " + e.getMessage());
        } catch (InterruptedException e) {
            log.warn("query hbase error : " + e.getMessage());
        } finally {
            close(table, connection);
        }
        return results;
    }
}

package com.sunsky.dao.impl;

import com.sunsky.dao.HbaseDao;
import com.sunsky.dao.RowKey;
import com.sunsky.utils.HbaseUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunsky on 2017/4/24.
 */
public class HbaseDaoImpl<T> implements HbaseDao<T>{

    private static Logger log = LogManager.getLogger(HbaseDaoImpl.class.getName());

    private Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return tClass;
    }

    public List<T> getByKeys(String tableName, String familyName, List<String> rowkeys) {
        List<T> beans = new ArrayList<T>();
        if (rowkeys != null && rowkeys.size() > 0) {
            Object[] results = null;
            if (familyName == null || "".equals(familyName.trim())) {
                results = HbaseUtil.batchQueryRowKey(tableName, rowkeys);
            } else {
                results = HbaseUtil.batchQueryRowKey(tableName, familyName, rowkeys);
            }
            for (Object obj : results) {
                Result result = (Result) obj;
                T objType = null;
                try {
                    objType = getTClass().newInstance();
                    RowKey annotationRowKey = objType.getClass().getAnnotation(RowKey.class);
                    String objRowkey = annotationRowKey.value();
                    Map<String, Object> map = new HashMap<String, Object>();
                    boolean b = true;
                    for (Cell c : result.rawCells()) {
                        if (b && objRowkey != null) {
                            b = false;
                            map.put(objRowkey, new String(CellUtil.cloneRow(c)));
                        }
                        map.put(new String(CellUtil.cloneQualifier(c)), new String(CellUtil.cloneValue(c)));
                    }
                    BeanUtils.populate(objType, map);
                } catch (IllegalAccessException e) {
                    log.info("hbase get error populate bean error" + e.getMessage());
                } catch (InvocationTargetException e) {
                    log.info("hbase get error populate bean error" + e.getMessage());
                } catch (InstantiationException e) {
                    log.info("hbase get error can not newInstance error" + e.getMessage());
                }
                beans.add(objType);
            }
        }
        return beans;
    }
}

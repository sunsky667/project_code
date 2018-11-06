package com.sunsky.dao;

import java.util.List;

/**
 * Created by sunsky on 2017/4/24.
 */
public interface HbaseDao<T> {
    public List<T> getByKeys(String tableName, String familyName, List<String> keys);
}

package com.sunsky.dao;

import java.lang.annotation.*;

/**
 * Created by sunsky on 2017/4/24.
 */
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RowKey {
    public String value();
}

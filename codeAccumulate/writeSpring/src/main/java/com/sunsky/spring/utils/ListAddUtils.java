package com.sunsky.spring.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class ListAddUtils {

    /**
     * add instance to list,
     * and make sure only one instance in list
     * @param list
     * @param t
     * @param <T>
     */
    public static <T> void add(List<T> list,T t){
        Set<T> set = new HashSet<T>(list);
        if(set.add(t)){
            list.add(t);
        }
    }

}

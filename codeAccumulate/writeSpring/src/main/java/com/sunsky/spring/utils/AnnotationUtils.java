package com.sunsky.spring.utils;

/**
 * AnnotationUtils
 */
public class AnnotationUtils {

    /**
     * judge if exists or not have annotation
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T t){
        return t == null ? true : false;
    }

}

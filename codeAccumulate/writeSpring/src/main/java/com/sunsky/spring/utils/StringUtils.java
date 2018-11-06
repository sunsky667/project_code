package com.sunsky.spring.utils;

/**
 *
 */
public class StringUtils {

    /**
     * judge string is null or is ''
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        if(string == null || "".equals(string)){
            return true;
        }
        return false;
    }

}

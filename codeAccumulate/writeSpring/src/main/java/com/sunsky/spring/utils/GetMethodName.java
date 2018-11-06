package com.sunsky.spring.utils;

/**
 *
 */
public class GetMethodName {

    /**
     * generate set method
     * method start with set.
     * after set, transfer the first char to Caps
     * like 'setName(String name)'
     * @param propertyName
     * @return
     */
    public static String getSetMethodNameByField(String propertyName){
        String methodName = "set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
        return methodName;
    }

}

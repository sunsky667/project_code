package com.sunsky.jvm.ConstantOut;

import java.util.ArrayList;
import java.util.List;
/**
 * @Described：常量池内存溢出探究
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 * 没有出现溢出情况
 */
public class ConstantOutOfMemory {
    public static void main(String[] args) throws Exception{
        try {
            List<String> strs = new ArrayList<>();
            int i = 0;
            while(true){
                strs.add(String.valueOf(i++).intern());
                System.out.println(i);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}

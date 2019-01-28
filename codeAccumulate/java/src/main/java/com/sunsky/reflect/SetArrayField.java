package com.sunsky.reflect;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class SetArrayField {

    private static final int srcBufSize = 10*1024;
    private static char[] src = new char[srcBufSize];

    static {
        src[srcBufSize-1] = 'x';
    }

    //初始化一个10*1024长度的CharArrayReader
    private static CharArrayReader charArrayReader = new CharArrayReader(src);

    public static void main(String[] args) throws Exception{
        //初始化BufferedReader
        BufferedReader bufferedReader = new BufferedReader(charArrayReader);
        //获取BufferedReader的Class
        Class clazz = bufferedReader.getClass();
        //获取BufferedReader的cb成员变量
        Field field = clazz.getDeclaredField("cb");
        field.setAccessible(true);
        //获取bufferedReader的cb成员变量的引用，并强制转换为char[]
        char[] cbVal = char[].class.cast(field.get(bufferedReader));
        //copy出一个新的char数组
        char[] newVal = Arrays.copyOf(cbVal,cbVal.length*2);

        field.set(bufferedReader,newVal);

        for(int i = 0;i<srcBufSize;i++){
            bufferedReader.read();
        }

        if(newVal[srcBufSize-1] == src[srcBufSize-1]){
            System.out.println(newVal.length);
        }else{
            System.out.println(cbVal.length);
        }
    }

}

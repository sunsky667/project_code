package com.sunsky.inputformat.custom;

import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class FindMaxValueMapper extends Mapper<IntWritable, ArrayWritable,IntWritable, FloatWritable>{
	private final static IntWritable one =new IntWritable(1);  
    @Override  
    protected void map(  
            IntWritable key,  
            ArrayWritable value,  
            Context context)
            throws IOException, InterruptedException {  
          
        FloatWritable[] floatArray =(FloatWritable[])value.toArray();  
        float maxfloat=floatArray[0].get();  
        float tmp;  
        /** 
         * 求一个InputSplit中的最大值 
         */  
        for (int i = 0; i < floatArray.length; i++) {  
            tmp=floatArray[i].get();  
            if (tmp>maxfloat) {  
                maxfloat=tmp;  
            }  
        }  
        /** 
         * 把一个map中的最大值输出出来 
         */  
        context.write(one, new FloatWritable(maxfloat));  
    }  
}

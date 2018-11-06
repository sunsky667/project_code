package com.sunsky.inputformat.custom;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FindMaxValueReducer extends Reducer<IntWritable, FloatWritable, Text, FloatWritable>{
	@SuppressWarnings("rawtypes")  
    @Override  
    protected void reduce(  
            IntWritable k2,  
            Iterable<FloatWritable> v2s,  
            Context context)
            throws IOException, InterruptedException {  
          
        Iterator it = v2s.iterator();  
        float maxfloat=0,tmp;  
        /** 
         * 取第一个数 
         */  
        if (it.hasNext()) {  
            maxfloat=((FloatWritable)(it.next())).get();  
        }else {  
            //集合为空时，输出迭代失败信息  
            context.write(new Text("Max float value : "), null);  
            return;  
        }  
        /** 
         * 求最大值 
         */  
        while (it.hasNext()) {  
            tmp=((FloatWritable)(it.next())).get();  
            if (tmp > maxfloat) {  
                maxfloat = tmp;  
            }  
        }  
        //把最大的那个值输出来  
        context.write(new Text("Max float value : "), new FloatWritable(maxfloat));  
    }  
}

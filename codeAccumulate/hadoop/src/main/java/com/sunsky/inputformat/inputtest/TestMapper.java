package com.sunsky.inputformat.inputtest;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TestMapper extends Mapper<IntWritable,ArrayWritable,IntWritable,IntWritable> {

    private final static IntWritable one =new IntWritable(1);

    @Override
    protected void map(IntWritable key, ArrayWritable value, Context context) throws IOException, InterruptedException {

        IntWritable[] intWritables =(IntWritable[])value.toArray();
        System.out.println("================map value size =============== "+intWritables.length);

        System.out.println("TestMapper : "+intWritables.length);
        int maxfloat=intWritables[0].get();
        int tmp;
        /**
         * 求一个InputSplit中的最大值
         */
        for (int i = 0; i < intWritables.length; i++) {
            tmp=intWritables[i].get();
            if (tmp>maxfloat) {
                maxfloat=tmp;
            }
        }
        /**
         * 把一个map中的最大值输出出来
         */
        context.write(one, new IntWritable(maxfloat));

    }
}

package com.sunsky.inputformat.inputtest;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestInputFormat extends InputFormat<IntWritable,ArrayWritable> {

    public List<InputSplit> getSplits(JobContext context) throws IOException, InterruptedException {

        int beg = 0;
        int length = 10;
        int end = beg+length-1;

        //新建一个分片列表
        ArrayList<InputSplit> splits =new ArrayList<InputSplit>();

        //前面的分片里的record数目相同
        for (int i = 0; i < 1; i++) {

            TestSplit split =new TestSplit(beg,end);
            splits.add(split);

            beg=end+1;  //分片起始位置
            end=end+length-1;  //分片结束位置
        }
        //最后一个分片，改分片里的record数目与其他分片不一样，最后最个分片的数目要大于或等于其他分片
        TestSplit split=new TestSplit(beg,20-1);
        splits.add(split);

        return splits;
    }

    public RecordReader<IntWritable, ArrayWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new TestRecordReader();
    }
}

package com.sunsky.inputformat.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class FindMaxValueInputFormat extends InputFormat<IntWritable, ArrayWritable>{
	
	public static float[] floatvalues;
	
    /** 
     * 返回一个InputSplit 集合 
     * 这个例子一共有两个InputSplit，两个map 
     * 随机产生100个 0-1 的数组，放到float数组里面 
     */  
    @Override  
    public List<InputSplit> getSplits(JobContext context) throws IOException,  
            InterruptedException {
    	//获取需要生成的数组的长度
        int NumOfValues = context.getConfiguration().getInt("NumOfValues", 100);
        //new一个存放生成数字的数组
        floatvalues = new float[NumOfValues];  
        Random rand =new Random();  
        //生成数字并放入数组 
        for (int i = 0; i < NumOfValues; i++) {  
            floatvalues[i]=rand.nextFloat();  
        }
        
        //分片数
        int NumSplits = context.getConfiguration().getInt("mapred.map.tasks", 2);  
        int beg=0;
        
        //数组长度/分片数   得到每个分片分多少数据
        int length =(int) Math.floor(NumOfValues/NumSplits);  
        //新建一个分片列表
        ArrayList<InputSplit> splits =new ArrayList<InputSplit>();  
        int end = length-1;//分片里的结束位置-1，即end-beg为每个分片里的record数目
        //前面的分片里的record数目相同
        for (int i = 0; i < NumSplits-1; i++) {  
            FindMaxValueInputSplit split =new FindMaxValueInputSplit(beg,end);  
            splits.add(split);  
              
            beg=end+1;  //分片起始位置
            end=end+length-1;  //分片结束位置
        }  
        //最后一个分片，改分片里的record数目与其他分片不一样，最后最个分片的数目要大于或等于其他分片
        FindMaxValueInputSplit split=new FindMaxValueInputSplit(beg,NumOfValues-1);  
        splits.add(split);  
          
        return splits;  
    }  
      
    /** 
     * 自定义 RecordReader 
     */  
    @Override  
    public RecordReader<IntWritable, ArrayWritable> createRecordReader(  
            InputSplit split, TaskAttemptContext context) throws IOException,  
            InterruptedException {  
        return new FindMaxValueRecordReader();  
    }  
}

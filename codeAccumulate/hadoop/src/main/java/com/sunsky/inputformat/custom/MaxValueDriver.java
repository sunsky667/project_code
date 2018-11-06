package com.sunsky.inputformat.custom;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * MapReduce自带的输入类型都是基于HDFS的，
 * 这个例子的功能是，不从HDFS上面读取输入内容，
 * 在内存中随机生成100个（0-1）float型的小数，
 * 然后求这100个小数的最大值。
 * @author sunsky
 *
 */
public class MaxValueDriver {
	 public static void main(String[] args) throws Exception {  
	        Configuration conf = new Configuration();  
	        conf.set("fs.default.name", "hdfs://ubuntu:9000");
	        Job job=Job.getInstance(conf, MaxValueDriver.class.getSimpleName());  
	        
	        job.setJarByClass(MaxValueDriver.class);  
	          
	        job.setNumReduceTasks(1);  
	          
	        job.setMapperClass(FindMaxValueMapper.class);  
	        job.setReducerClass(FindMaxValueReducer.class);  
	          
	        job.setMapOutputKeyClass(IntWritable.class);  
	        job.setMapOutputValueClass(FloatWritable.class);  
	          
	        job.setOutputKeyClass(Text.class);  
	        job.setOutputValueClass(FloatWritable.class);  
	          
	        job.setInputFormatClass(FindMaxValueInputFormat.class);  
	        job.setOutputFormatClass(TextOutputFormat.class);  
	          
	    //  FileInputFormat.setInputPaths(job, args[0]);  
	        FileOutputFormat.setOutputPath(job, new Path("/tmp/sbout"));  
	          
	        job.waitForCompletion(true);  
	          
	    }  
}

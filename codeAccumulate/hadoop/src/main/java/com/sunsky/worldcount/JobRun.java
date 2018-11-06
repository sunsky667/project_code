package com.sunsky.worldcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobRun {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		System.setProperty("hadoop.home.dir","D:\\Program Files\\hadoop-common-2.2.0-bin-master");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.80.128:9000");
		Job job = Job.getInstance(conf);
		job.setJarByClass(JobRun.class);
		job.setMapperClass(WcMapper.class);
		job.setReducerClass(WcReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setNumReduceTasks(1);
		
		FileInputFormat.addInputPath(job, new Path("/usr/input/wc"));
		FileOutputFormat.setOutputPath(job, new Path("/usr/output/wc"));
		
		System.exit(job.waitForCompletion(true) ? 0:1);
		
	}
}

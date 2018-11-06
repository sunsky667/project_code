package com.sunsky.inputformat.inputtest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.default.name", "hdfs://ubuntu:9000");
        Job job=Job.getInstance(conf, App.class.getSimpleName());

        job.setJarByClass(App.class);

        job.setNumReduceTasks(1);

        job.setMapperClass(TestMapper.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);


        job.setInputFormatClass(TestInputFormat.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileOutputFormat.setOutputPath(job, new Path("/tmp/sbout"));

        job.waitForCompletion(true);
    }
}

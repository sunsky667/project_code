package com.sunsky.inputformat.textInput;

/**
 * Hadoop内置的输入文件格式类有：
1）FileInputFormat<K,V>这个是基本的父类，自定义就直接使用它作为父类。
2）TextInputFormat<LongWritable,Text>这个是默认的数据格式类。key代表当前行数据距离文件开始的距离，value代码当前行字符串。
3）SequenceFileInputFormat<K,V>这个是序列文件输入格式，使用序列文件可以提高效率，但是不利于查看结果，建议在过程中使用序列文件，最后展示可以使用可视化输出。
4）KeyValueTextInputFormat<Text,Text>这个是读取以Tab（也即是\t）分隔的数据，每行数据如果以\t分隔，那么使用这个读入，就可以自动把\t前面的当做key，后面的当做value。
5）CombineFileInputFormat<K,V>合并大量小数据是使用。
6）MultipleInputs，多种输入，可以为每个输入指定逻辑处理的Mapper。
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		System.setProperty("hadoop.home.dir","D:\\Program Files\\hadoop-common-2.2.0-bin-master");
		Configuration configuration = new Configuration();
		configuration.set("fs.default.name", "hdfs://ubuntu:9000");
		Job job = Job.getInstance(configuration);
		job.setJarByClass(Main.class);
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setInputFormatClass(MyInputFormat.class);
		
		job.setNumReduceTasks(1);
		
		FileInputFormat.addInputPath(job, new Path("/tmp/input"));
		FileOutputFormat.setOutputPath(job, new Path("/tmp/output"));
		
		System.exit(job.waitForCompletion(true) ? 0:1 );
	}

}

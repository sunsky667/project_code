package com.sunsky.inputformat.textInput;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Text, Text, Text, Text>{

	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		System.out.println("=================="+value.toString());
		context.write(key, value);
	}
	
}

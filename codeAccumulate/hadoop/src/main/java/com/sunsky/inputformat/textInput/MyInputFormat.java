package com.sunsky.inputformat.textInput;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.LineReader;

public class MyInputFormat extends FileInputFormat<Text, Text>{


	
    /**
     * 获取RecordReader
     */
	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		return new MyRecordReader();
	}
	
	/**
	 * 定义一个RecordReader
	 * @author sunsky
	 *
	 */
	public static class MyRecordReader extends RecordReader<Text, Text>{

		//lineReader行读取
		private LineReader lineReader;  
	    private Text key = new Text();  
	    private Text value = new Text();  
	    private long start;  
	    private long end;  
	    private long currentPos;  
	    private Text line = new Text();
		
	    /**
	     * 初始化
	     */
		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			//得到文件分片
			FileSplit fileSplit = (FileSplit)split;
			Configuration config = context.getConfiguration();
			// 获取分片文件对应的完整文件
			Path path = fileSplit.getPath();
			FileSystem fs = path.getFileSystem(config);
			//得到输入流
			FSDataInputStream inputStream = fs.open(path);
			
			lineReader = new LineReader(inputStream, config);
			// 获取分片文件的开始位置
			start = fileSplit.getStart();
			// 获取分片文件的结束位置 
			end = start + fileSplit.getLength();
			//移动输入流到起始位置
			inputStream.seek(start);
			
			if(start != 0){
				start += lineReader.readLine(new Text(), 0 , (int) Math.min(Integer.MAX_VALUE, end - start));
			}
			
			currentPos = start;
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {

			if(currentPos > end){
				return false;
			}
			currentPos += lineReader.readLine(line);
			if(line.getLength() == 0){
				return false;
			}
			// 若是需要被忽略的行，直接读下一行
			if(line.toString().startsWith("ignore")){
				currentPos += lineReader.readLine(line);
			}
			String[] words = line.toString().split(",");
			if(words.length < 2){
				System.out.println("============line======= " + line.toString());
				return false;
			}
			
			key.set(words[0]);
			value.set(words[1]);
			
			return true;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			return key;
		}

		@Override
		public Text getCurrentValue() throws IOException, InterruptedException {
			return value;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			if(start == end){
				return 0.0f;
			}else{
				return Math.min(1.0f, (currentPos-start)/(float)(end -start));
			}
		}

		@Override
		public void close() throws IOException {
			lineReader.close();
		}
		
	}
	
}

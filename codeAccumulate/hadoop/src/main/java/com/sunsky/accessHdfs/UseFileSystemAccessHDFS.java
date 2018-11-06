package com.sunsky.accessHdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * 使用FileSystem访问HDFS文件系统
 * @author sunsky
 * 将本地文件系统的文件通过java-API写入到HDFS文件
 */
public class UseFileSystemAccessHDFS {

	public static void main(String[] args) throws IOException {
		String source = "D:\\file\\fuck.dat";  //本地路径read
		String destination = "hdfs://ubuntu:9000/test/access/demo1"; //HDFS路径write
		InputStream in = new BufferedInputStream(new FileInputStream(source)); //获取输入流
		
		//HDFS读写的配置文件
		Configuration conf = new Configuration();
		//调用Filesystem的create方法返回的是FSDataOutputStream对象
		//该对象不允许在文件中定位，因为HDFS只允许一个已打开的文件顺序写入或追加
		FileSystem fs = FileSystem.get(URI.create(destination),conf);
		OutputStream out = fs.create(new Path(destination), false);
		IOUtils.copyBytes(in, out, 4096, true);
		
	}

}

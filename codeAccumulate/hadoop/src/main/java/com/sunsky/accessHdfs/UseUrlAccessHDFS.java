package com.sunsky.accessHdfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

/**
 * 使用java.net.URL访问HDFS文件系统
 * 操作：显示HDFS文件夹中的文件内容
 * 1.使用java.net.URL对象打开数据流
 * 2.使用静态代码块使得java程序识别Hadoop的HDFS url
 * @author sunsky
 * 
 * HDFS的API使用
 * 1.如果要访问HDFS，HDFS客户端必须有一份HDFS的配置文件,也就是hdfs-site.xml,从而读取Namenode的信息。
 * 2.每个应用程序也必须拥有访问Hadoop程序的jar文件
 * 3.操作HDFS,也就是HDFS的读和写,最常用的类FileSystem
 * 操作：显示HDFS文件夹中的文件内容
 * 1.使用java.net.URL对象打开数据流
 * 2.使用静态代码块使得java程序识别Hadoop的HDFS url
 *
 */
public class UseUrlAccessHDFS {

	static{
		//使用java.net.URL对象打开数据流
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	public static void main(String[] args) {
		InputStream in = null;
		try {
			//识别Hadoop的HDFS url
			in = new URL("hdfs://ubuntu:9000/test/dat/spark-streaming.json").openStream();
			//利用hadoop的IOUtils拷贝数据流从输入到输出
			IOUtils.copyBytes(in, System.out, 4096,false);
		} catch (Exception e) {
			System.err.println("Error");
		} finally {
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package com.sunsky.accessHdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * file      hadoop
 * path       URI
 * File       Path
 * 			  FileSystem
 * 
 * new InputStream(new File(path))    FileSystem.get(new URI(uri),Configuration).open(new Path(uri))
 * @author sunsky
 *
 */
public class HdfsFileSystemOperation {
	
	public static void main(String[] args) throws Exception {
//		createDirection("hdfs://ubuntu:9000/test/access/demo2");
//		checkFileIsExists("hdfs://ubuntu:9000/test/access/demo2");
//		deleteDirection("hdfs://ubuntu:9000/test/access/demo2");
//		checkFileIsExists("hdfs://ubuntu:9000/test/access/demo2");
//		listFiles("hdfs://ubuntu:9000/tmp/mrout");
//		locationFile("hdfs://ubuntu:9000/test/access/demo1");
		getLines("hdfs://ubuntu:9000//tmp/input","hdfs://ubuntu:9000/tmp/mrout/");
	}
	
	/**
	 * 创建HDFS目录
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void createDirection(String path) throws IOException, URISyntaxException{
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(new URI(path),config);
		
		Path dfs = new Path(path);
		
		fs.mkdirs(dfs);
		
		System.out.println("create success");
		
	}
	
	/**
	 * 判断目录是否存在
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void checkFileIsExists(String path) throws IOException, URISyntaxException{
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(new URI(path),config);
		
		Path dfs = new Path(path);
		boolean flag = fs.exists(dfs);
		
		String exists = flag ? "exists":"not exists";
		System.out.println(exists);
	}
	
	/**
	 * 删除目录
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void deleteDirection(String path) throws IOException, URISyntaxException{
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(new URI(path),config);
		Path dfs = new Path(path);
		
		boolean deleteFlag = fs.delete(dfs,true);
		
		String delete = deleteFlag ? "delete success":"delete fail";
		
		System.out.println(delete);
	}
	
	/**
	 * 列出目录下的文件或目录名称
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void listFiles(String path) throws IOException, URISyntaxException{
		Configuration config = new Configuration();
		//获取FileSystem
		FileSystem fs = FileSystem.get(new URI(path), config);
		
		Path dfs = new Path(path);
		
		FileStatus[] fileStatus = fs.listStatus(dfs);
		
		for(FileStatus f : fileStatus){
			//get path
			System.out.println("file path : "+f.getPath().toString());
			//get block size
			System.out.println("file block size : "+f.getBlockSize());
			//get owner
			System.out.println("file owner : "+f.getOwner());
			//judge directory
			String directory = f.isDirectory() ? "directory":"not directory";
			System.out.println("is directory : "+directory);
			
		}
		
		Path path1 = new Path("hdfs://ubuntu:9000/tmp/mrout");
		//FileSystem除了用上面的方法获取，还可以用下面的方法获取
		FileSystem fs1 = path1.getFileSystem(config);
		FileStatus[] fileStatus1 = fs1.listStatus(path1);
		for(FileStatus fst : fileStatus1){
			System.out.println("file path : "+fst.getPath().toString());
			System.out.println("block size : "+fst.getBlockSize());
			System.out.println("access time : "+fst.getAccessTime());
			System.out.println("group : " + fst.getGroup());
			System.out.println("owner : "+fst.getOwner());
			System.out.println("length : "+fst.getLen());
		}
		
		URI u1 = path1.toUri();
		System.out.println("URI : "+u1.toString());
		
	}
	
	/**
	 * 文件存储的位置
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void locationFile(String path) throws IOException, URISyntaxException{
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(new URI(path), config);
		Path dfs = new Path(path);
		
		FileStatus fileStatus = fs.getFileStatus(dfs);
		
		BlockLocation[] blockLocation = fs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
		
		for(BlockLocation location:blockLocation){
			String[] hosts=location.getHosts();
			System.out.println("block_Location:"+hosts[0]);
		}
	}
	
	
	public static void getLines(String orgPath,String path) throws Exception {
		Configuration config = new Configuration();
		
		Path dfs = new Path(path);
		FileSystem fileSystem = dfs.getFileSystem(config);
		FileStatus[] fileStatus = fileSystem.listStatus(dfs);
		
		Path inputPath = new Path(orgPath);
		FileSystem inputFileSystem = inputPath.getFileSystem(config);
		FileStatus[] inputStatus = inputFileSystem.listStatus(inputPath);
		
		int badFileCount = 0;
		int mgbaFileCount = 0;
		int orgFile = 0;
		
		//读取源文件
		for(FileStatus fst : inputStatus){
			String filePath = fst.getPath().toString();
			System.out.println("file path : "+filePath);
			
			FSDataInputStream hdfsInStream = fileSystem.open(new Path(filePath));
			InputStreamReader isr = new InputStreamReader(hdfsInStream, "utf-8");  
			BufferedReader br = new BufferedReader(isr);  
			while(br.readLine()!= null){
				orgFile ++;
			}
			isr.close();
			br.close();
		}
		System.out.println("=================="+orgFile);
		
		//读取目录文件
		for(FileStatus fst : fileStatus){
			
			String filePath = fst.getPath().toString();
			System.out.println("file path : "+filePath);
			
//			FileSystem fs = FileSystem.get(new URI(filePath), config);
			
			FSDataInputStream hdfsInStream = fileSystem.open(new Path(filePath));
			InputStreamReader isr = new InputStreamReader(hdfsInStream, "utf-8");  
			BufferedReader br = new BufferedReader(isr);  
			if(filePath.contains("bad")){
				while(br.readLine()!= null){
					badFileCount ++;
				}
			}else if(filePath.contains("mgba")){
				while(br.readLine()!= null){
					mgbaFileCount ++;
				}
			}
			isr.close();
			br.close();
		}
		
		if(orgFile != badFileCount+mgbaFileCount){
			throw new InterruptedException("file records not equal");
		}
		
		System.out.println("================="+badFileCount+"######################"+mgbaFileCount);
		
		System.exit(0);
	}
}

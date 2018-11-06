package com.sunsky.inputformat.custom;

import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class FindMaxValueRecordReader extends RecordReader<IntWritable,ArrayWritable>{
	
	private int m_End;  
    private int m_Index;  
    private int m_Start;  
    private IntWritable key=null;  
    private ArrayWritable value=null;  
    private FindMaxValueInputSplit fmvsplit=null;  
      
    @Override  
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {  
        //得到分片 
    	fmvsplit=(FindMaxValueInputSplit)split;
    	//得到起始位置
        this.m_Start=fmvsplit.getM_StartIndex(); 
        //得到结束位置
        this.m_End=fmvsplit.getM_EndIndex();
        //设置index=起始位置
        this.m_Index=this.m_Start;  
    }
    
    /** 
     * value都是从分片里面取得的（文件读的是重分片里获得Path来取得输入流然后的到一行数据的）
     * 判断是否有还有值可以取
     * 输出的key为  IntWritable 
     * 输出的value为  ArrayWritable 
     * 只输出一次，一次将整个数组全部输出
     * 所以这里会调用我们指定的RecordReader中的nextKeyValue函数。
     * 这个函数就会处理或者说是初始化key和value，然后返回true，告知已经处理好了。
     * 接着就会调用getCurrentKey 和getCurrentValue获取当前的key和value值。
     */  
    @Override  
    public boolean nextKeyValue() throws IOException, InterruptedException {  
        if (key == null) {  
            key=new IntWritable();  
        }  
        if (value == null) {  
            value = new ArrayWritable(FloatWritable.class);  
        }  
        if (m_Index <= m_End) {  
            key.set(m_Index);  //存的是开始的位置  
            value=fmvsplit.getM_FloatArray();
            System.out.println("===============arraysize============="+value.get().length);
            m_Index=m_End+1;
            System.out.println("================m_Index================"+m_Index);
            return true;  
        }else {  
            return false;     
        }  
    }  
  
    /**
     * 若nextKeyValue返回true会调用该方法来获取nextKeyValue初始化了的key
     * 返回key
     */
    @Override  
    public IntWritable getCurrentKey() throws IOException, InterruptedException {  
        return key;  
    }  
  
    /**
     * 若nextKeyValue返回true会调用该方法来获取nextKeyValue初始化了的value
     * 返回value
     */
    @Override  
    public ArrayWritable getCurrentValue() throws IOException,  
            InterruptedException {  
        return value;  
    }  
  
    /**
     * 返回进度
     */
    @Override  
    public float getProgress() throws IOException, InterruptedException {  
          
        if (this.m_Index == this.m_End) {  
            return 0.0f;  
        }else {  
            return Math.min(1.0f, (this.m_Index - this.m_Start)/(float)(this.m_End-this.m_Start));  
        }  
    }  
  
  
    @Override  
    public void close() throws IOException {  
    }  
}

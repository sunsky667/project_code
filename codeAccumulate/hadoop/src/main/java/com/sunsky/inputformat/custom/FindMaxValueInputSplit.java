package com.sunsky.inputformat.custom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

/**
 * Split主要记录
 * @author sunsky
 *
 */
public class FindMaxValueInputSplit extends InputSplit implements Writable{
	
	private int m_StartIndex;  
    private int m_EndIndex;  
    private ArrayWritable m_FloatArray=new ArrayWritable(FloatWritable.class);  
      
    public FindMaxValueInputSplit(){};  
    /** 
     * 这个自定义分类主要记录了Map函数的开始索引和结束索引，第一个map处理前50个小数，第二个map后50个小数 
     * @param start 开始位置 
     * @param end 结束位置 
     */  
    public FindMaxValueInputSplit(int start,int end){  
        m_StartIndex=start;  
        m_EndIndex=end;  
        int len=m_EndIndex-m_StartIndex+1;  //计算每个split的record的数量
        int index=m_StartIndex;  
        //FloatWritable数组（split要处理的Record类型，长度是record个数）
        FloatWritable[] result = new FloatWritable[len];  
        //record的初始化是在InputFormat的getSplits初始化的
        for (int i = 0; i < result.length; i++) {  
            float f = FindMaxValueInputFormat.floatvalues[index];//取生成的float数组里的元素 
            FloatWritable fW =new FloatWritable();  
            fW.set(f);  
            result[i]=fW;  
            index++;  
        }
        //将redcords加入一个数组
        m_FloatArray.set(result);  
    }  
      
    public void write(DataOutput out) throws IOException {  
        out.writeInt(this.m_StartIndex);  
        out.writeInt(this.m_EndIndex);  
        this.m_FloatArray.write(out);  
    }  
  
  
    public void readFields(DataInput in) throws IOException {  
        this.m_StartIndex=in.readInt();  
        this.m_EndIndex=in.readInt();
        System.out.println("======================================"+m_StartIndex+" "+m_EndIndex);
        this.m_FloatArray.readFields(in);  
    }  
  
  
    /**
     * 获取分片的长度
     */
    @Override  
    public long getLength() throws IOException, InterruptedException {  
        return (this.m_EndIndex-this.m_StartIndex+1);  
    }  
  
    /**
     * 获取分片的位置信息
     */
    @Override  
    public String[] getLocations() throws IOException, InterruptedException {  
        return new String[]{"hadoop-2","hadoop-1"};  
    }  
      
    public int getM_StartIndex(){   
        return m_StartIndex;  
    }  
      
    public int getM_EndIndex(){  
        return m_EndIndex;  
    }  
      
    public ArrayWritable getM_FloatArray(){   
        return m_FloatArray;  
    }  
}

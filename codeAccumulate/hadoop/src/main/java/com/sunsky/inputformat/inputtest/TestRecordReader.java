package com.sunsky.inputformat.inputtest;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class TestRecordReader extends RecordReader<IntWritable,ArrayWritable> {

    private int start_pos;
    private int end_pos;
    private int index;
    private TestSplit split;
    private IntWritable key;
    private ArrayWritable value;

    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.split = (TestSplit)split; //得到分片
        this.start_pos = this.split.getStart_pos();//得到该分片的startpos
        this.end_pos = this.split.getEnd_pos();
        this.index = this.start_pos;
    }

    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(this.key == null){
            this.key = new IntWritable();
        }
        if(this.value == null){
            this.value = new ArrayWritable(IntWritable.class);
        }

        if(this.index <= this.end_pos){
            key.set(this.index);
            value = split.getArrayWritable();
            index = end_pos+1;
            return true;
        }else {
            return false;
        }
    }

    public IntWritable getCurrentKey() throws IOException, InterruptedException {
        return this.key;
    }

    public ArrayWritable getCurrentValue() throws IOException, InterruptedException {
        return this.value;
    }

    public float getProgress() throws IOException, InterruptedException {
        return 1.0f;
    }

    public void close() throws IOException {

    }

}

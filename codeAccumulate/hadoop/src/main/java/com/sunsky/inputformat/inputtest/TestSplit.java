package com.sunsky.inputformat.inputtest;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TestSplit extends InputSplit implements Writable {

    //split start pos
    private int start_pos;
    //split end pos
    private int end_pos;

    private ArrayWritable arrayWritable = new ArrayWritable(IntWritable.class);

    //overwrite Writable method
    public void write(DataOutput out) throws IOException {
        System.out.println("========================split write data=======================");
        out.writeInt(this.start_pos); //write start position
        out.writeInt(this.end_pos);//write end position
        this.arrayWritable.write(out);
    }

    //overwrite Writable method
    public void readFields(DataInput in) throws IOException {
        this.start_pos = in.readInt();  //read start position
        this.end_pos = in.readInt();    //read end position
        System.out.println("==============TestSplit=================="+start_pos+" "+end_pos);
        this.arrayWritable.readFields(in); //read fields
    }

    //overwrite InputSplit method
    //Get the size of the split, so that the input splits can be sorted by size
    public long getLength() throws IOException, InterruptedException {
        System.out.println("=========================get length======================"+(this.end_pos-this.start_pos+1));
        return (this.end_pos-this.start_pos+1);
    }

    //overwrite InputSplit method
    //Get the list of nodes by name where the data for the split would be local.
    public String[] getLocations() throws IOException, InterruptedException {
        System.out.println("============get locations====================");
        return new String[]{"hadoop-1","hadoop-2"};
    }

    //auto generator method


    public TestSplit() {

    }

    public TestSplit(int start_pos, int end_pos) {
        this.start_pos = start_pos;
        this.end_pos = end_pos;

        IntWritable[] intWritables = new IntWritable[end_pos-start_pos+1];

        System.out.println("===============intWritables size ==== "+intWritables.length);

        for(int i=0; i<= end_pos-start_pos;i++){
            IntWritable intWritable = new IntWritable();
            intWritable.set(3);
            intWritables[i] = intWritable;
        }

        arrayWritable.set(intWritables);
    }

    public int getStart_pos() {
        System.out.println("====================get start pos===================");
        return start_pos;
    }

    public int getEnd_pos() {
        System.out.println("=================================get end pos====================");
        return end_pos;
    }

    public ArrayWritable getArrayWritable() {
        System.out.println("=============================get writeable===========================");
        return arrayWritable;
    }
}

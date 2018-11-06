package com.sunsky.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class FileReaderSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private boolean completed = false;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {

        try {
            this.fileReader = new FileReader(map.get("wordsFile").toString());
            this.collector = spoutOutputCollector;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void nextTuple() {
        if(completed){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String str ;

        try {
            while((str=bufferedReader.readLine()) != null){
                this.collector.emit(new Values(str),str);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error reading tuple",e);
        }finally {
            completed = true;
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

}

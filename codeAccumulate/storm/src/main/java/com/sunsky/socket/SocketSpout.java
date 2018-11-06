package com.sunsky.socket;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class SocketSpout extends BaseRichSpout{

    private SpoutOutputCollector collector;
    private boolean completed = false;
    private String host;
    private int port;
    private BufferedReader bufferedReader;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            this.host = map.get("host").toString();
            this.port = Integer.parseInt(map.get("port").toString());
            this.collector = spoutOutputCollector;
            Socket socket = new Socket(this.host,this.port);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(inputStreamReader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        if(completed){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }

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

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }

}

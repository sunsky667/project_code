package com.sunsky.bolts;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class CountBolts extends BaseBasicBolt {

    private Map<String,Integer> countMap ;
    private Integer id;
    private String name;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
        this.countMap = new HashMap<String, Integer>();
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word = tuple.getString(0);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@ "+word);

        if(countMap.containsKey(word)){
            Integer cnt = countMap.get(word)+1;
            countMap.put(word,cnt);
        }else{
            countMap.put(word,1);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public void cleanup() {
        super.cleanup();
        System.out.println("============ Word Counter [ "+name+" - "+id+" ] =============");
        for(Map.Entry<String, Integer> entry : countMap.entrySet()){
            System.out.println("################## "+entry.getKey()+": "+entry.getValue());
        }
    }
}

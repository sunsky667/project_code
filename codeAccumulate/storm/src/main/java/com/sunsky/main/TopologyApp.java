package com.sunsky.main;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.sunsky.bolts.CountBolts;
import com.sunsky.bolts.SplitBolts;
import com.sunsky.spouts.FileReaderSpout;

public class TopologyApp {
    public static void main(String[] args) throws Exception{
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout("fileReader",new FileReaderSpout());
        topologyBuilder.setBolt("wordSplit",new SplitBolts()).shuffleGrouping("fileReader");
        topologyBuilder.setBolt("count",new CountBolts()).fieldsGrouping("wordSplit",new Fields("word"));

        Config config = new Config();
        config.setDebug(true);
        config.put("wordsFile", args[0]);

        if(!"local".equals(args[1])){
            config.setNumWorkers(3);
            StormSubmitter.submitTopology("wordcount", config, topologyBuilder.createTopology());
        }else{
            config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordcount", config, topologyBuilder.createTopology());
            Thread.sleep(60000);
            cluster.killTopology("wordcount");
            cluster.shutdown();

        }


    }
}

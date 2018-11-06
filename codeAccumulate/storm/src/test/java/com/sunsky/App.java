package com.sunsky;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.sunsky.bolts.CountBolts;
import com.sunsky.bolts.SplitBolts;
import com.sunsky.spouts.FileReaderSpout;

/**
 * only for local test
 */
public class App {
    public static void main(String[] args) throws Exception{
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout("fileReader",new FileReaderSpout());
        topologyBuilder.setBolt("wordSplit",new SplitBolts()).shuffleGrouping("fileReader");
        topologyBuilder.setBolt("count",new CountBolts()).fieldsGrouping("wordSplit",new Fields("word"));

        Config config = new Config();
        config.setDebug(true);
        config.put("wordsFile", "D:\\logs\\birp.log");

        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wordcount", config, topologyBuilder.createTopology());
        Thread.sleep(60000);
        cluster.killTopology("wordcount");
        cluster.shutdown();
    }
}

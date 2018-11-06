package com.sunsky;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.sunsky.socket.CountBolts;
import com.sunsky.socket.SplitBolts;
import com.sunsky.socket.SocketSpout;

public class SocketApp {
    public static void main(String[] args) throws Exception{
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout("socketReader",new SocketSpout());
        topologyBuilder.setBolt("wordSplit",new SplitBolts()).shuffleGrouping("socketReader");
        topologyBuilder.setBolt("count",new CountBolts()).fieldsGrouping("wordSplit",new Fields("word"));

        Config config = new Config();
        config.setDebug(true);
        config.put("host", "192.168.80.128");
        config.put("port", "9999");

        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wordcount", config, topologyBuilder.createTopology());
//        Thread.sleep(60000);
//        cluster.killTopology("wordcount");
//        cluster.shutdown();
    }
}

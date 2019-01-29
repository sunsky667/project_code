package com.sunsky.flink.kafka;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;


import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","ubuntu:9092");
        properties.setProperty("group.id","flink_kafka");
        properties.setProperty("zookeeper.connect","ubuntu:2181");
        FlinkKafkaConsumer08<String> kafkaSource = new FlinkKafkaConsumer08<String>("test",new SimpleStringSchema(),properties);
        kafkaSource.setStartFromLatest();

        DataStream<String> dataStream = env.addSource(kafkaSource);
        
        DataStream<Tuple2<String,Integer>> pairs = dataStream.map(new MapFunction<String, Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> map(String s) throws Exception {
                Tuple2<String,Integer> tuple2 = new Tuple2<String, Integer>(s,1);
                return tuple2;
            }
        });

        DataStream<Tuple2<String,Integer>> reduceData = pairs.keyBy(0).reduce(new ReduceFunction<Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> reduce(Tuple2<String, Integer> stringIntegerTuple2, Tuple2<String, Integer> t1) throws Exception {
                Tuple2<String,Integer> tuple2 = new Tuple2<String, Integer>(stringIntegerTuple2.f0,(stringIntegerTuple2.f1+t1.f1));
                return tuple2;
            }
        });

        DataStream<Tuple3<String,String,Integer>> resulteData = reduceData.map(new MapFunction<Tuple2<String, Integer>, Tuple3<String, String, Integer>>() {
            public Tuple3<String, String, Integer> map(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                if(stringIntegerTuple2.f0 != null){
                    String[] strings = stringIntegerTuple2.f0.split(" ");
                    if(strings.length >= 2){
                        Tuple3<String,String,Integer> tuple3 = new Tuple3<String, String, Integer>(strings[0],strings[1],stringIntegerTuple2.f1);
                        return tuple3;
                    }
                }
                return null;
            }
        });

        resulteData.addSink(new MysqlSink());

        env.execute("first");
        
    }
}

package com.sunsky.flink.fold;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * KeyedStream → DataStream
 */
public class Fold {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text = env.socketTextStream("192.168.80.128",9999);

        DataStream<String> data = text.flatMap(new FlatMapFunction<String, String>() {
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] values = s.split(" ");
                for(String value : values){
                    collector.collect(value);
                }
            }
        });

        DataStream<Tuple2<String,Integer>> pairs = data.map(new MapFunction<String, Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> map(String s) throws Exception {
                return new Tuple2<String, Integer>(s,1);
            }
        });

        pairs.keyBy(0).fold("start", new FoldFunction<Tuple2<String, Integer>, String>() {
            public String fold(String s, Tuple2<String, Integer> o) throws Exception {
                return s + o.f0;
            }
        }).print();

        env.execute("fold");
    }
}

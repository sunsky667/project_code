package com.sunsky.flink.keyBy;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * DataStream → KeyedStream
 */
public class KeyByDemo {

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

        DataStream<Tuple2<String, Integer>> pairs = data.map(new MapFunction<String, Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> map(String s) throws Exception {
                return new Tuple2<String, Integer>(s,1);
            }
        });

//        pairs.keyBy(0).sum(1).print();

        pairs.keyBy(0).timeWindow(Time.seconds(5)).sum(1).print();

        env.execute("key by");
    }

}

package com.sunsky.flink.filter;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * DataStream → DataStream
 */
public class FilterDemo {

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
        }).filter(new FilterFunction<String>() {
            public boolean filter(String s) throws Exception {
                return !"fuck".equals(s);
            }
        });

        data.print();
        env.execute("filter");
    }

}

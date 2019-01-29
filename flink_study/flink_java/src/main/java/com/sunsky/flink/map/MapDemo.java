package com.sunsky.flink.map;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

/**
 * DataStream → DataStream
 */
public class MapDemo {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text = env.socketTextStream("192.168.80.128",9999);
        DataStream<Integer> data = text.map(new MapFunction<String, Integer>() {
            public Integer map(String s) throws Exception {
                return  Integer.parseInt(s);
            }
        });

        DataStream<Integer> result = data.map(new MapFunction<Integer, Integer>() {
            public Integer map(Integer integer) throws Exception {
                return 2 * integer;
            }
        });

        result.print();
        env.execute("map");
    }


}

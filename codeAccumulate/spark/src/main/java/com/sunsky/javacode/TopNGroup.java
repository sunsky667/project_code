package com.sunsky.javacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by sunsky on 2017/3/22.
 */
public class TopNGroup {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("TopN Group");
        conf.setMaster("local");
        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<String> lines = context.textFile("D://file//topNGroup.dat");

        JavaPairRDD<String, Integer> pairs = lines.mapToPair(new PairFunction<String, String, Integer>() {
            private static final long serialVersionUID = 1L;
            public Tuple2<String, Integer> call(String t) throws Exception {
                return new Tuple2<String, Integer>(t.split(" ")[0], Integer.parseInt(t.split(" ")[1]));
            }
        });

        JavaPairRDD<String, Iterable<Integer>> groupPairs = pairs.groupByKey(); //group first

        /* order */
        JavaPairRDD<String, Iterable<Integer>> top5 = groupPairs.mapToPair(new PairFunction<Tuple2<String,Iterable<Integer>>, String, Iterable<Integer>>() {

            public Tuple2<String, Iterable<Integer>> call(Tuple2<String, Iterable<Integer>> t) throws Exception {
                Integer[] top5 = new Integer[5];
                String groupKey = t._1;
                Iterator<Integer> groupValue = t._2.iterator();

                while(groupValue.hasNext()){
                    Integer value = groupValue.next();
                    for(int i=0;i<top5.length;i++){
                        if(top5[i] == null){
                            top5[i] = value;
                            break;
                        }else if(value > top5[i]){
                            for(int j=top5.length-1; j>i; j--){
                                top5[j] = top5[j-1]; //类似冒泡排序，将后面的元素往后移动，然后再原位置上插入一个数据
                            }
                            top5[i] = value;
                            break;
                        }
                    }
                }
                return new Tuple2<String, Iterable<Integer>>(groupKey, Arrays.asList(top5));
            }
        });

        top5.foreach(new VoidFunction<Tuple2<String,Iterable<Integer>>>() {

            public void call(Tuple2<String, Iterable<Integer>> t) throws Exception {
                System.out.println("groupKey : "+ t._1);
                Iterator<Integer> value = t._2.iterator();
                while(value.hasNext()){
                    System.out.println(value.next());
                }
                System.out.println("=====================================================");
            }
        });
        context.stop();
    }
}

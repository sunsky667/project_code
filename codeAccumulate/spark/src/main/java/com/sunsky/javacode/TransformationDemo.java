package com.sunsky.javacode;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by sunsky on 2017/3/19.
 */
public class TransformationDemo {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("word count");
        conf.setMaster("local");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> lines = context.textFile("D://scala//workspace//spark-1.6.0-bin-hadoop2.6//README.md");

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            public Iterator<String> call(String line) throws Exception {
                //return (Iterator<String>) Arrays.asList(line.split(" "));
                return Arrays.asList(line.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {

            public Tuple2<String, Integer> call(String t) throws Exception {
                return new Tuple2<String, Integer>(t ,1);
            }
        });

        JavaPairRDD<String, Integer> wordsCount = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {

            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1+v2;
            }
        });

        wordsCount.foreach(new VoidFunction<Tuple2<String,Integer>>() {

            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t._1+" : "+t._2);
            }
        });

        context.close();
    }

}

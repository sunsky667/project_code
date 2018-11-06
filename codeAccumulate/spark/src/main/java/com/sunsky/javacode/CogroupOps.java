package com.sunsky.javacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sunsky on 2017/3/19.
 *
 */
public class CogroupOps {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("word count");
        conf.setMaster("local");
        JavaSparkContext context = new JavaSparkContext(conf);

        //Array((id,name))
        List<Tuple2<Integer,String>> namesList = Arrays.asList(
                new Tuple2<Integer,String>(1,"Spark"),
                new Tuple2<Integer,String>(2,"Kafka"),
                new Tuple2<Integer,String>(3,"Hadoop"),
                new Tuple2<Integer,String>(4,"Zookeeper"),
                new Tuple2<Integer,String>(5,"HBase")
        );

        //Array((id,score))
        List<Tuple2<Integer,Integer>> scoresList = Arrays.asList(
                new Tuple2<Integer,Integer>(1,100),
                new Tuple2<Integer,Integer>(2,90),
                new Tuple2<Integer,Integer>(3,70),
                new Tuple2<Integer,Integer>(4,95),
                new Tuple2<Integer,Integer>(5,86),
                new Tuple2<Integer,Integer>(2,80),
                new Tuple2<Integer,Integer>(4,88)
        );

        JavaPairRDD<Integer,String> names = context.parallelizePairs(namesList);
        JavaPairRDD<Integer,Integer> scores = context.parallelizePairs(scoresList);

        //result --> (id,Iterable(name),Iterable(score))
        JavaPairRDD<Integer,Tuple2<Iterable<String>,Iterable<Integer>>> nameScores = names.cogroup(scores);

        nameScores.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>>(){

            public void call(Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> t) throws Exception {
                System.out.println("StudentID: " + t._1);
                System.out.println("StudentName: "+t._2._1);
                System.out.println("StudentScore: "+t._2._2);
                System.out.println("==================================================");
            }

        });
    }
}

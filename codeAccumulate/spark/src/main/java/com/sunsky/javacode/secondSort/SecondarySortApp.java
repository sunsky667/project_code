package com.sunsky.javacode.secondSort;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

/**
 * Created by sunsky on 2017/3/20.
 */
public class SecondarySortApp {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("second sort");
        conf.setMaster("local");
        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> lines = context.textFile("D://file//sort.dat");

        JavaPairRDD<SecondaryOrderKey,String> paris = lines.mapToPair(new PairFunction<String, SecondaryOrderKey, String>() {

            public Tuple2<SecondaryOrderKey, String> call(String line) throws Exception {
                String[] splited = line.split(" ");
                SecondaryOrderKey key = new SecondaryOrderKey(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
                return new Tuple2<SecondaryOrderKey, String>(key, line);
            }
        });

        JavaPairRDD<SecondaryOrderKey,String> sorted = paris.sortByKey();

        JavaRDD secondarySorted = sorted.map(new Function<Tuple2<SecondaryOrderKey,String>, String>() {

            public String call(Tuple2<SecondaryOrderKey, String> v1) throws Exception {
                return v1._2;
            }
        });

        secondarySorted.foreach(new VoidFunction<String>(){

            public void call(String t) throws Exception {
                System.out.println(t);
            }

        });
    }
}

package com.sunsky.es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchDemo {

    public final static String HOST = "192.168.80.128";
    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300

    public static void getClient() throws Exception{

        Settings settings = Settings.builder().put("cluster.name", "es1").build();

        //创建客户端
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), 9300));

        System.out.println("Elasticsearch connect info:" + client.toString());

        //关闭客户端
        client.close();
    }

    public static void index() throws Exception{
        Settings settings = Settings.builder().put("cluster.name", "es1").build();

        //创建客户端
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), 9300));

        System.out.println("Elasticsearch connect info:" + client.toString());

        Map source = new HashMap();
        source.put("user","kimchy");
        source.put("postDate",new Date());
        source.put("message","trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("twitter", "tweet", "1").setSource(source).get();

        System.out.println(response.getId()+ ":"+ response.getType()+" : "+response.status());

        //关闭客户端
        client.close();
    }

    public static void get() throws Exception{
        Settings settings = Settings.builder().put("cluster.name", "es1").build();
        //创建客户端
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), 9300));
        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        // 从索引库获取数据
        System.out.println("the content is :"+response.getSourceAsString());
        System.out.println(response.getId()+" : "+response.getIndex()+":"+response.getSource());
        client.close();
    }

    public static void main(String[] args) throws Exception{
//        index();
        get();
    }
}

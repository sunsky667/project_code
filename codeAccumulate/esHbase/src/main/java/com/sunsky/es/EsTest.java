package com.sunsky.es;

import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EsTest {

    public static final String HOST = "192.168.80.128";
    public static final int PORT = 9300;

    private TransportClient client = null;

    /**
     * get es client
     * @throws Exception
     */
    @Before
    public void getClient() throws Exception{
        Settings settings = Settings.builder().put("cluster.name", "es1").build();
        client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
        System.out.println("client is : "+client.toString());
    }

    /**
     * add index
     * @throws Exception
     */
    @Test
    public void addIndex() throws Exception{
        IndexResponse response = client.prepareIndex("msg","tweet","1")
                .setSource(
                        XContentFactory.jsonBuilder()
                        .startObject()
                        .field("userName","张三")
                        .field("sendDate",new Date())
                        .field("msg","你好李四")
                        .endObject()

                ).get();
        System.out.printf("index name : %s , index type : %s , index id : %s ",response.getIndex(),response.getType(),response.getId());
    }


    /**
     * add json to index
     */
    @Test
    public void addJsonToIndex(){

        String jsonStr = "{" +
                    "\"userName\":\"张三\"," +
                    "\"sendDate\":\"2017-11-30\"," +
                    "\"msg\":\"你好李四\"" +
                "}";

        IndexResponse response = client.prepareIndex("wexin","tweet").setSource(jsonStr, XContentType.JSON).get();

        System.out.printf("index name : %s , index type : %s , index id : %s ",response.getIndex(),response.getType(),response.getId());
        System.out.println();
    }

    /**
     * add map to index
     */
    @Test
    public void addMapToIndex(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userName","张三");
        map.put("sendDate",new Date());
        map.put("msg","你好李四");

        IndexResponse response = client.prepareIndex("momo","tweet","1").setSource(map).get();
        System.out.printf("index name : %s , index type : %s , index id : %s ,status is : %s",response.getIndex(),response.getType(),response.getId(),response.status());
    }

    /**
     * add json object to index
     */
    @Test
    public void addJsonObjectToIndex(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName","张三");
        jsonObject.addProperty("sendDate","2017-11-23");
        jsonObject.addProperty("msg","你好李四");
        System.out.println(jsonObject);

        IndexResponse response = client.prepareIndex("msg","tweet","2").setSource(jsonObject.toString(),XContentType.JSON).get();

        System.out.printf("index name : %s , index type : %s , index id : %s ,status is : %s",response.getIndex(),response.getType(),response.getId(),response.status());
    }

    /**
     * get data from index
     */
    @Test
    public void getDataFromIndex(){
        GetResponse response = client.prepareGet("msg","tweet","1").get();
        System.out.println("get data is : "+response.getSourceAsString());
    }

    /**
     * update data to index
     */
    @Test
    public void updateData(){
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("userName", "王五");
        jsonObject.addProperty("sendDate", "2008-08-08");
        jsonObject.addProperty("msg","你好,张三，好久不见");
        UpdateResponse response = client.prepareUpdate("msg","tweet","1")
                .setDoc(jsonObject.toString(),XContentType.JSON).get();

        System.out.printf("index name : %s , index type : %s , index id : %s ,status is : %s",response.getIndex(),response.getType(),response.getId(),response.status());
    }

    /**
     * delete data from index
     */
    @Test
    public void deleteData(){
        DeleteResponse response = client.prepareDelete("msg","tweet","2").get();
        System.out.printf("index name : %s , index type : %s , index id : %s ,status is : %s",response.getIndex(),response.getType(),response.getId(),response.status());
    }

    /**
     * mutiGet
     */
    @Test
    public void mutiGet(){
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("msg","tweet","1","2")
                .add("momo","tweet","1")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println("muti get data is : "+json);
            }
        }
    }

    /**
     * search
     */
    @Test
    public void search(){
        SearchResponse searchResponse = client.prepareSearch("msg","momo")
                .setTypes("tweet")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //.setQuery(QueryBuilders.termQuery("sendDate", "2017-11-23"))
                //.setQuery(QueryBuilders.existsQuery("sendDate"))
                .setQuery(QueryBuilders.termsQuery("sendDate","2017-11-23","2008-08-08"))
                .setFrom(0).setSize(60).setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();

        SearchHit[] hits2 = hits.getHits();

        for(SearchHit searchHit : hits2){
            System.out.println(searchHit.getSourceAsString());
        }

    }

    /**
     * close es client
     */
    @After
    public void closeClient(){
        if(client != null){
            System.out.println("close client");
            client.close();
        }
    }

}

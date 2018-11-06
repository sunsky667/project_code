package com.sunsky.readKafkaWithLowerConsumer;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;

import java.util.HashMap;
import java.util.Map;

public class KafkaUtil {
    /**
     * 根据时间戳找到某个客户端消费的offset
     *
     * @param consumer SimpleConsumer
     * @param topic topic
     * @param partition 分区
     * @param clientName 客户端名称
     * @param whichTime 时间戳
     * @return offset
     */
    public static long getLastOffset(SimpleConsumer consumer, String topic, int partition, String clientName, long whichTime) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo =
                new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        OffsetRequest request = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
        if(response.hasError()){
            return 0;
        }

        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }
}

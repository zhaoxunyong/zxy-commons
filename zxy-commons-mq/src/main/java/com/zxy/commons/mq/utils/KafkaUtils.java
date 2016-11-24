/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zxy.commons.mq.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.kafka.common.protocol.SecurityProtocol;
import org.apache.kafka.common.security.JaasUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.mq.partition.KafkaPartition;
import com.zxy.commons.mq.prop.KafkaConsumerConfigProperties;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.cluster.Broker;
import kafka.cluster.BrokerEndPoint;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.utils.ZKGroupTopicDirs;
import kafka.utils.ZkUtils;
import scala.Option;
import scala.collection.JavaConversions;
import scala.collection.Seq;

/** 
 * Kafka utils
 * 
 * <p>
 * <a href="KafkaUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public final class KafkaUtils {
    private final static String ZOOKEEPER_CONNECT = KafkaConsumerConfigProperties.getInstance().getZookeeperConnect();
    private final static ZkUtils ZK_UTILS;
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaUtils.class);
//    private final static CuratorFramework CLIENT;
    
    static {
//        CLIENT = CuratorFrameworkFactory.builder()
//                .connectString(ZOOKEEPER_CONNECT)
//                .retryPolicy(new RetryNTimes(10, 5000))
//                .connectionTimeoutMs(50000).sessionTimeoutMs(50000).build();
//        CLIENT.start();
        
        ZK_UTILS = ZkUtils.apply(ZOOKEEPER_CONNECT, 50000, 50000, JaasUtils.isZkSecurityEnabled());
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
//                CloseableUtils.closeQuietly(CLIENT);
                ZK_UTILS.close();
            }
        });
    }
    
    private KafkaUtils() {}
    
    private static String getTopicPath(String topic) {
        return "/brokers/topics/"+topic;
    }
    
    private static String getOffsetPath(String topic, String groupId, int partition) {
        return "/consumers/"+groupId+"/offsets/"+topic+"/" + partition;
    }
    
    private static Map<String, Seq<Object>> getPartitionsForTopics(String topic) {
        return JavaConversions.mapAsJavaMap(
                ZK_UTILS.getPartitionsForTopics(JavaConversions.asScalaBuffer(Collections.singletonList(topic))));
    }
    
    /**
     * Create topic
     * 
     * @param topic topic
     * @param partitions partitions number
     * @param replication replication number
     */
    public static void createTopic(String topic, int partitions, int replication) {
        Properties topicConfig = new Properties(); // add per-topic configurations settings here
        AdminUtils.createTopic(ZK_UTILS, topic, partitions, replication, topicConfig, RackAwareMode.Enforced$.MODULE$);
    }
    
    /**
     * Topic exists
     * 
     * @param topic topic
     * @return topic exists
     */
    public static boolean topicExists(String topic) {
        return AdminUtils.topicExists(ZK_UTILS, topic);
    }
    
    /**
     * Delete topic
     * 
     * @param topic topic
     */
    public static void deleteTopic(String topic) {
        AdminUtils.deleteTopic(ZK_UTILS, topic);
    }
    
    /**
     * Get partition number
     * 
     * @param topic topic
     * @return partition number
    */
    public static int getPartitionNums(String topic) {
        try {
            // /brokers/topics/[topic]
//            byte[] bytes = CLIENT.getData().forPath("/brokers/topics/"+topic);
//            KafkaPartition kafkaPartition = JsonUtils.toObject(BytesUtils.toString(bytes), KafkaPartition.class);
            String topicStr = ZK_UTILS.zkClient().readData(getTopicPath(topic));
            KafkaPartition kafkaPartition = JsonUtils.toObject(topicStr, KafkaPartition.class);
            return kafkaPartition.getPartitions().size();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return 0;
    }
    
    /**
     * Seek all offset
     * 
     * @param topic topic
     * @param groupId groupId
     * @param offset offset
    */
    public static void seekAllPartition(String topic, String groupId, long offset) {
        int partitionNums = getPartitionNums(topic);
        if(partitionNums > 0) {
            for (int i = 0; i < partitionNums; i++) {
                try {
                    // /consumers/[groupId]/offsets/[topic]/[partitionId]
                    String path = getOffsetPath(topic, groupId, i);
                    // offset必须用String
//                    CLIENT.setData().forPath(path, BytesUtils.toBytes(String.valueOf(offset)));
                    ZK_UTILS.zkClient().writeData(path, String.valueOf(offset));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
    
    /**
     * Seek partiton offset
     * 
     * @param topic topic
     * @param groupId groupId
     * @param partition partition
     * @param offset offset
    */
    public static void seekPartition(String topic, String groupId, int partition, long offset) {
        try {
            // /consumers/[groupId]/offsets/[topic]/[partitionId]
            String path = getOffsetPath(topic, groupId, partition);
            // offset必须用String
//            CLIENT.setData().forPath(path, BytesUtils.toBytes(String.valueOf(offset)));
            ZK_UTILS.zkClient().writeData(path, String.valueOf(offset));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    /**
     * Get partition offset
     * 
     * @param topic topic
     * @param groupId groupId
     * @param partition partition
     * @return parition offset
    */
    public static Long getOffset(String topic, String groupId, int partition) {
//        try {
//            // /consumers/[groupId]/offsets/[topic]/[partitionId]
//            String path = "/consumers/"+groupId+"/offsets/"+topic+"/" + partition;
//            String offsetStr = ZK_UTILS.zkClient().readData(path);
//            return NumberUtils.toLong(offsetStr);
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return 0;
        ZKGroupTopicDirs topicDirs = new ZKGroupTopicDirs(groupId, topic);
        String partitionOffsetPath = topicDirs.consumerOffsetDir() + "/" + partition;
        Option<String> maybeOffset = ZK_UTILS.readDataMaybeNull(partitionOffsetPath)._1();
        return maybeOffset.isDefined() ? Long.parseLong(maybeOffset.get()) : null;
    }

    /**
     * 获取某个topci所有partition的偏移量
     * 
     * @param topic topic
     * @param groupId groupId
     * @return 所有partition的偏移量，Key为partion序号，Value为结果
    */
    public static Map<Integer,Long> getOffsets(String topic, String groupId) {
        Map<Integer,Long> returnMap = Maps.newHashMap();
        Map<String, Seq<Object>> ptMaps = getPartitionsForTopics(topic);
        if(ptMaps!=null && !ptMaps.isEmpty()) {
            for(Entry<String, Seq<Object>> map : ptMaps.entrySet()) {
//                String topic = map.getKey();
                List<Object> value = JavaConversions.seqAsJavaList(map.getValue());
                if(value != null && !value.isEmpty()) {
                    for(Object obj : value) {
                        int partition = Integer.parseInt(obj.toString());
                        Long offset = getOffset(topic, groupId, partition);
//                        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
                        returnMap.put(partition, offset);
                    }
                }
            }
        }
        return returnMap;
    }
    
    /**
     * Get logsize
     * 
     * @param topic topic
     * @param partition partition
     * @return logsize
     */
    public static Long getLogsize(String topic, int partition) {
        return getOffset(topic, partition, kafka.api.OffsetRequest.LatestTime());
    }
    
    /**
     * 获取某个topci所有partition的logsize
     * 
     * @param topic topic
     * @return 所有partition的logsize，Key为partion序号，Value为结果
    */
    public static Map<Integer,Long> getLogsizes(String topic) {
        Map<Integer,Long> returnMap = Maps.newHashMap();
        Map<String, Seq<Object>> ptMaps = getPartitionsForTopics(topic);
        if(ptMaps!=null && !ptMaps.isEmpty()) {
            for(Entry<String, Seq<Object>> map : ptMaps.entrySet()) {
//                String topic = map.getKey();
                List<Object> value = JavaConversions.seqAsJavaList(map.getValue());
                if(value != null && !value.isEmpty()) {
                    for(Object obj : value) {
                        int partition = Integer.parseInt(obj.toString());
                        Long logsize = getLogsize(topic, partition);
//                        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
                        returnMap.put(partition, logsize);
                    }
                }
            }
        }
        return returnMap;
    }
    
    /**
     * Get lag numer
     * 
     * @param topic topic
     * @param groupId groupId
     * @param partition partition
     * @return Lag numer
     */
    public static long getLag(String topic, String groupId, int partition) {
        Long logsize = getLogsize(topic, partition);
        Long offset = getOffset(topic, groupId, partition);
        if(logsize != null && offset != null && logsize >= offset) {
            return logsize - offset;
        }
        return 0L;
    }
    
    /**
     * 获取某个topci所有partition的lag
     * 
     * @param topic topic
     * @param groupId groupId
     * @return 所有partition的lag，Key为partion序号，Value为结果
    */
    public static Map<Integer,Long> getLags(String topic, String groupId) {
        Map<Integer,Long> returnMap = Maps.newHashMap();
        Map<String, Seq<Object>> ptMaps = getPartitionsForTopics(topic);
        if(ptMaps!=null && !ptMaps.isEmpty()) {
            for(Entry<String, Seq<Object>> map : ptMaps.entrySet()) {
//                String topic = map.getKey();
                List<Object> value = JavaConversions.seqAsJavaList(map.getValue());
                if(value != null && !value.isEmpty()) {
                    for(Object obj : value) {
                        int partition = Integer.parseInt(obj.toString());
                        long lag = getLag(topic, groupId, partition);
//                        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
                        returnMap.put(partition, lag);
                    }
                }
            }
        }
        return returnMap;
    }
    
    /**
     * Get offset
     * 
     * @param topic topic
     * @param partition partition
     * @param currentTime currentTime
     * @return offset
     */
    private static Long getOffset(String topic, int partition, long currentTime) {
//        String clientName = "Client_" + topic + "_" + partition;
//        PartitionMetadata metadata = KafkaUtils.findLeader(topic, partition);
        Broker leadBroker = KafkaUtils.findLeader(topic, partition);
        Preconditions.checkNotNull(leadBroker, "Can't find metadata for Topic and Partition. Exiting");
        BrokerEndPoint endPoint = leadBroker.getBrokerEndPoint(SecurityProtocol.PLAINTEXT);
        Preconditions.checkNotNull(endPoint, "Can't find Leader for Topic and Partition. Exiting");
        SimpleConsumer consumer = new SimpleConsumer(endPoint.host(), endPoint.port(), 10000, 100000, "ConsumerOffsetChecker");

        try {
            TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
            Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
            requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(currentTime, 1));

            kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(requestInfo,
                    kafka.api.OffsetRequest.CurrentVersion(), "");
            OffsetResponse response = consumer.getOffsetsBefore(request);
            long[] offsets = response.offsets(topic, partition);
            if(offsets != null && offsets.length >0) {
                return offsets[0];
            }
            return null;
        } finally {
            if(consumer != null) {
                consumer.close();
            }
        }
    }
  
    /**
     * Find leader
     * 
     * @param topic topic
     * @param partition partition
     * @return Leader broker
     */
    private static Broker findLeader(String topic, int partition) {
        int leaderID;
        Broker leadBroker = null;
        try {
            leaderID = (Integer) ZK_UTILS.getLeaderForPartition(topic, partition).get();
            leadBroker = ZK_UTILS.getBrokerInfo(leaderID).get();

        } catch (Exception e) {
            LOGGER.error("Exception from ZkClient. message: {} ", e.getMessage(), e);
        }

        return leadBroker;
    }
  
///**
// * Get brokers
// * 
// * @return broker url.
// */
//public static Set<String> getBrokers() {
//    Set<String> brokerHosts = new HashSet<String>();
//    List<Broker> clusters = JavaConversions.seqAsJavaList(ZK_UTILS.getAllBrokersInCluster());
//    for (Broker broker : clusters) {
//        BrokerEndPoint endPoint = broker.getBrokerEndPoint(SecurityProtocol.PLAINTEXT);
//        brokerHosts.add(endPoint.host() + ":" + endPoint.port());
//    }
//    return brokerHosts;
//}
    
//  /**
//   * Find leader
//   * @param topic topic
//   * @param partition partition
//   * @return Leader partition metadata
//  */
//  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
//  public static PartitionMetadata findLeader(String topic, int partition) {
//      Set<String> seedBrokers = getBrokers();
//      PartitionMetadata returnMetaData = null;
//      loop: for (String seed : seedBrokers) {
//          SimpleConsumer consumer = null;
//          try {
//              String host = StringUtils.substringBefore(seed, ":");
//              int port = Integer.parseInt(StringUtils.substringAfter(seed, ":"));
//              consumer = new SimpleConsumer(host, port, 100000, 64 * 1024, "leaderLookup");
//              List<String> topics = Collections.singletonList(topic);
//              TopicMetadataRequest req = new TopicMetadataRequest(topics);
//              kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
//
//              List<TopicMetadata> metaData = resp.topicsMetadata();
//              for (TopicMetadata item : metaData) {
//                  for (PartitionMetadata part : item.partitionsMetadata()) {
//                      if (part.partitionId() == partition) {
//                          returnMetaData = part;
//                          break loop;
//                      }
//                  }
//              }
//          } catch (Exception e) {
//              LOGGER.error("Error communicating with Broker [" + seed + "] to find Leader for [" + topic + ", " + partition + "] Reason: " + e.getMessage(), e);
//          } finally {
//              if (consumer != null) {
//                  consumer.close();
//              }
//          }
//      }
//      return returnMetaData;
//  }
}

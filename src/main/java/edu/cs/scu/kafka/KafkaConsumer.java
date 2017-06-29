package edu.cs.scu.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by lch on 2017/6/29.
 */
public class KafkaConsumer {
    private Properties properties;


    public KafkaConsumer() {
        super();
        setProperties();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties() {
        Properties props = new Properties();
        props.put("topic","my-test");
        props.put("bootstrap.servers", "120.24.238.195:9092,120.25.162.32:9092,112.74.114.226:9092,112.74.124.150:9092");// 该地址是集群的子集，用来探测集群。
        props.put("group.id", "group1");// cousumer的分组id
        props.put("enable.auto.commit", "true");// 自动提交offsets
        props.put("auto.commit.interval.ms", "1000");// 每隔1s，自动提交offsets
        props.put("session.timeout.ms", "30000");// Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");// 反序列化器
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.properties = props;
    }

    /**写了两个recieve()函数，第一个可以返回值，第二个是直接在函数里面写分析逻辑
     * recieve（）相比较recieve1（）在效率应该要好点，minBatchSize可以根据需要修改，一次性取出这么多数据，便于分析。
     * */
    public List<String> receive1() {
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(properties.getProperty("topic")));
        List<String> buffer = new ArrayList<String>();
        String msg = "";
        while (true) {
            System.err.println("consumer receive------------------");
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record.value());
            }
            consumer.close();
            return buffer;
        }

    }
    public void receive() {
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(properties.getProperty("topic")));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
                System.err.println(buffer.size() + "----->" + record);

            }
            if (buffer.size() >= minBatchSize) {
                //analyseData(); //在这里写分析逻辑
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}

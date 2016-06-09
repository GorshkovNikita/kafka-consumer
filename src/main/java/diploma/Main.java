package diploma;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.23:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //consumer.subscribe(new ArrayList<String>() {{ add("my-replicated-topic"); }});
        consumer.assign(new ArrayList<TopicPartition>() {{ add(new TopicPartition("my-replicated-topic", 0)); }});
        while (true) {
            System.out.println("-----------------------------------------------------------------");
            ConsumerRecords<String, String> records = consumer.poll(100);
            //if (records != null) {
                System.out.println("-----------------------------------------------------------------");
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                }
            //}
        }
    }
}

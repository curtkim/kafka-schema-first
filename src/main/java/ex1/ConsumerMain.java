package ex1;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.IntStream;

public class ConsumerMain {

  public static void main(String[] argv){
    String bootstrapServers = "localhost:9092";
    String schemaRegistryUrl = "http://localhost:8081";

    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "KafkaExampleAvroConsumer.client");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleAvroConsumer");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());

    //Use Kafka Avro Deserializer.
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

    //Use Specific Record or else you get Avro GenericRecord.
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "false");

    //Schema registry location.
    props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);


    KafkaConsumer consumer = new KafkaConsumer<>(props);

    consumer.subscribe(Collections.singletonList(ProduceMain.topic));

    IntStream.range(1, 50).forEach(index -> {

      final ConsumerRecords<Long, GenericRecord> records = consumer.poll(100);

      if (records.count() == 0) {
        System.out.println("None found");
      } else records.forEach(record -> {

        GenericRecord taxiRec = record.value();

        System.out.printf("%s %d %d %s \n", record.topic(),
            record.partition(), record.offset(), taxiRec);
      });
    });
  }
}

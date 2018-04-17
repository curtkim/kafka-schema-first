package ex1;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class ProduceMain {

  static String topic = "taxi.driver.location";

  public static void main(String[] argv){
    String bootstrapServers = "localhost:9092";
    String schemaRegistryUrl = "http://localhost:8081";

    //final SpecificAvroSerde ordersSerde = createSerde(schemaRegistryUrl);


    final Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
    props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

    final Map<String, String> serdeConfig = Collections.singletonMap(
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);


//    GenericAvroSerializer gSerializer = new GenericAvroSerializer();
//
//    final SpecificAvroSerializer<Taxi> playEventSerializer = new SpecificAvroSerializer<>();
//    playEventSerializer.configure(serdeConfig, false);
//
//    final TaxiSerializer taxiSerializer = new TaxiSerializer();
//    taxiSerializer.configure(serdeConfig, false);
//    KafkaAvroSerializer serializer = new KafkaAvroSerializer();
//    serializer.configure(serdeConfig, false);

    final KafkaProducer<Long, GenericRecord> taxiProducer = new KafkaProducer<>(props);

    for(long i = 0; i < 100; i++)
      taxiProducer.send(new ProducerRecord<>(topic, i, newTaxi(i)));

    taxiProducer.close();
  }

  private static <VT extends SpecificRecord> SpecificAvroSerde<VT> createSerde(final String schemaRegistryUrl) {
    final SpecificAvroSerde<VT> serde = new SpecificAvroSerde<>();
    final Map<String, String> serdeConfig = Collections.singletonMap(
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    serde.configure(serdeConfig, false);
    return serde;
  }

  static GenericRecord newTaxi(long id){
    Schema schema = ReflectData.get().getSchema(Taxi.class);
    final GenericRecordBuilder taxiBuilder = new GenericRecordBuilder(schema);
    taxiBuilder.set("id", id);
    taxiBuilder.set("lat", 37.0);
    taxiBuilder.set("lng", 127.0);

    taxiBuilder.set("dispatchable", false);
    return taxiBuilder.build();
  }
}

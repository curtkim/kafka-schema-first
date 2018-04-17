package ex1;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class TaxiSerializer implements Serializer<Taxi> {

  Schema schema;

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    schema = ReflectData.get().getSchema(Taxi.class);
  }

  @Override
  public byte[] serialize(String topic, Taxi data) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    DatumWriter<Taxi> datumWriter = new ReflectDatumWriter<>(Taxi.class);
    DataFileWriter<Taxi> dataFileWriter = new DataFileWriter<>(datumWriter);
    try {
      dataFileWriter.create(schema, buffer);
      dataFileWriter.append(data);
      dataFileWriter.close();
      return buffer.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {

  }
}

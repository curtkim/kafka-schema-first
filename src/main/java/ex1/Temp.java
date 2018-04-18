package ex1;

import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class Temp {

  public static void main(String[] argv){
    Schema schema = ReflectData.get().getSchema(Taxi.class);
    System.out.println(schema);
  }

}

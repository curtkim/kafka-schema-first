package ex1;

import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.Nullable;

public class Taxi {
  int id;
  double lng;
  double lat;

  @AvroDefault("false")
  boolean dispatchable;

  @Nullable
  Route route;
}
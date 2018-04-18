package ex1;

import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.AvroIgnore;
import org.apache.avro.reflect.AvroName;
import org.apache.avro.reflect.Nullable;

public class Taxi {
  int id;
  double lng;
  double lat;
  Kind kind = Kind.MEDIUM;

  @AvroName("creditcard_enabled")
  boolean creditcardEnabled;

  @AvroDefault("false")
  boolean dispatchable;

  @AvroIgnore
  int angle = -1;

  @Nullable
  Route route;
}
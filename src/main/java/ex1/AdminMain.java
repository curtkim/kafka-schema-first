package ex1;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;

import java.util.Arrays;
import java.util.Properties;

public class AdminMain {

  public static void main(String[] args){
    Properties props = new Properties();
    props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    AdminClient adminClient = AdminClient.create(props);
    DescribeTopicsResult res = adminClient.describeTopics(Arrays.asList(ProduceMain.topic));
    System.out.println(res.all());
  }
}

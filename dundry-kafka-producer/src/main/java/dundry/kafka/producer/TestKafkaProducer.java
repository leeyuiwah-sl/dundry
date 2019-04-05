package dundry.kafka.producer;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import dundry.kafka.common.Constants;
import dundry.kafka.producer.ProducerFactory;

public class TestKafkaProducer {

    public static void main(String[] args) {
        runProducer();

    }

    static void runProducer() {
        Producer<Long, String> producer = ProducerFactory.createProducer();
        System.out.format("Prepared to send message from Topic: %s ...%n", Constants.TOPIC_NAME);
        System.out.format("Producer about to sent %d records%n", Constants.MESSAGE_COUNT);
        for (long key = 0; key < Constants.MESSAGE_COUNT; key++) {
            final Integer PARTITION = 0;    // for now we use only one partition
            String value = String.format("%4d claps", key+1);
            
            // key is index; value is clap
            ProducerRecord<Long, String> record 
                = new ProducerRecord<>(Constants.TOPIC_NAME, PARTITION, key, value);
            try {
                RecordMetadata metadata = producer.send(record).get();
                String msg = String.format("Sending record with key %d to partition %s "
                        + "with offset %d: %s%n",
                        key, metadata.partition(), metadata.offset(), value);
                System.out.print(msg);
            } catch (ExecutionException e) {
                     System.out.println("Error in sending record");
                     System.out.println(e);
            } catch (InterruptedException e) {
                      System.out.println("Error in sending record");
                      System.out.println(e);
            }
         }
        System.out.format("%nDone!%n");
    }    
}

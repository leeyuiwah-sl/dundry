package dundry.kafka.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import dundry.kafka.common.Constants;

public class TestKafkaConsumer {

    public static void main(String[] args) {
        runConsumer();
    }

    static void runConsumer() {
        Consumer<Long, String> consumer = ConsumerFactory.createConsumer();
        int noMessageFound = 0;
        System.out.format("Prepared to receive message from Topic: %s ...%n", Constants.TOPIC_NAME);
        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000)); 

            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > Constants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // No message found for so many times, exit  
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                String msg = String.format("Receiving record with key %d from partition %s with offset %d: %s%n",
                        record.key(), record.partition(), record.offset(), record.value());
                System.out.print(msg);
            });

            consumer.commitAsync();
        }

        consumer.close();
    }    
}

package com.tata.producer.utils;

import com.tata.producer.model.Product;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class KafkaProducer {

    private KafkaTemplate<String, Product> kafkaTemplate;
    private String topic;

    public KafkaProducer(KafkaTemplate<String, Product> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public ListenableFuture<SendResult<String, Product>> send(Product product) {
        return kafkaTemplate.send(topic, product);
    }
}

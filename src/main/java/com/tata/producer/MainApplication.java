package com.tata.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tata.producer.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException {

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert to customer object
        Product product = objectMapper.readValue(new File("C:\\Users\\user\\Desktop\\projects\\KafkaProducer\\src\\main\\java\\com\\tata\\producer\\product.json"), Product.class);

        //print customer details
        System.out.println(product);
    }
}

package com.tata.producer.service;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import com.tata.producer.model.Date;
import com.tata.producer.model.Metafields;
import com.tata.producer.model.Price;
import com.tata.producer.model.Product;
import com.tata.producer.utils.Helper;
import com.tata.producer.utils.KafkaProducer;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductPublisher {
    private static final Logger log = LoggerFactory.getLogger(ProductPublisher.class);

    private KafkaProducer producer;
    private Helper helper;

    public ProductPublisher(KafkaProducer kafkaProducer, Helper helper) {
        this.producer = kafkaProducer;
        this.helper = helper;
    }

    public int prepareProductionData() {
        List<String> sellerIds = helper.generateData(5,100,false,true);
        List<String> titles = helper.generateData(-1,100,true,true);
        List<String> pageTitles = helper.generateData(-1,100,true,true);
        List<String> descriptions = helper.generateData(-1,100,true,true);
        List<String> manufacturers = helper.generateData(5,100,false,false);
        List<Integer> priceMinValues = helper.generatedata(100,100,1000);
        List<Integer> priceMaxValues = helper.generatedata(100,1000,10000);
        List<Integer> capacityValues = helper.generatedata(100,100,1000);
        List<Integer> ratingValues = helper.generatedata(6,1,6);

        int failed = 0;
        for(int i=0; i<100000; i++) {
            boolean success = punlishToKafka( sellerIds,  titles,  pageTitles,  descriptions,  manufacturers,  priceMinValues,  priceMaxValues,  capacityValues,  ratingValues);
            if(!success) {
                failed++;
            }
        }
        return failed;
    }

    public boolean punlishToKafka(List<String> sellerIds, List<String> titles, List<String> pageTitles, List<String> descriptions, List<String> manufacturers, List<Integer> priceMinValues, List<Integer> priceMaxValues, List<Integer> capacityValues, List<Integer> ratingValues) {
        int minPrice = helper.getRandomValue(priceMinValues);
        int maxPrice = helper.getRandomValue(priceMaxValues);
        String range = minPrice + "-" + maxPrice;
        Price price = new Price(range,minPrice,maxPrice);
        int capacity = helper.getRandomValue(capacityValues);
        int rating = helper.getRandomValue(ratingValues);
        List<Metafields> metafields = new ArrayList<>();
        Metafields metafield = new Metafields("Capacity", Integer.toString(capacity));
        Metafields metafield1 = new Metafields("Rating", Integer.toString(rating));
        metafields.add(metafield);
        metafields.add(metafield1);
        java.util.Date date = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> map = new HashMap<>();
        map.put("status","new");
        Product product = new Product(helper.generateNumber(15),
                helper.getRandomValue(sellerIds),
                helper.getRandomValue(titles),
                helper.getRandomValue(pageTitles),
                helper.getRandomValue(descriptions),
                helper.getRandomValue(manufacturers),
                price,
                false,false,false,
                metafields,
                true,true,
                new Date(formatter.format(date)),new Date(formatter.format(date)),new Date(formatter.format(date)),
                map);
        try {
            sendToKafka(producer, product);
        } catch (Exception ex) {
            log.error("Exception while publishing to kafka "+ product.getProduct_id(), ex);
            return false;
        }
        return true;
    }

    public void sendToKafka(KafkaProducer kafkaProducer, Product product) throws Exception {
        log.debug("Writing into topic {}, product_id {}", kafkaProducer.getTopic(), product.getProduct_id());
        kafkaProducer.send(product).get();
    }
}

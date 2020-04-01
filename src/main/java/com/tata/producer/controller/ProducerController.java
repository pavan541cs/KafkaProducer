package com.tata.producer.controller;

import com.tata.producer.service.ProductPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class ProducerController {

    @Autowired
    ProductPublisher productPublisher;

    @PostMapping("/autopublish")
    public String autopost() {
        int failed = productPublisher.prepareProductionData();
        if (failed>0) {
            return "Publish failed for " + failed + "products";
        }
        return "Publishing Successful";
    }
}

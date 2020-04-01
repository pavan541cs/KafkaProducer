package com.tata.producer.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Helper {
    public String generateNumber(int length) {
        return RandomStringUtils.random(length,false,true);
    }

    public List<String> generateData(int length, int count, boolean letters, boolean numbers) {
        List<String> data = new ArrayList<String>();
        for(int i=0; i<count; i++) {
            if (length == -1) {
                int len = RandomUtils.nextInt(5,20);
                String ran = RandomStringUtils.random(len, letters, numbers);
                data.add(ran);
            } else {
                String ran = RandomStringUtils.random(length, letters, numbers);
                data.add(ran);
            }
        }
        return data;
    }

    public List<Integer> generatedata(int count, int min, int max) {
        List<Integer> data = new ArrayList<Integer>();
        for(int i=0; i<count; i++) {
            int price = RandomUtils.nextInt(min, max);
            data.add(price);
        }
        return data;
    }

    public <T> T getRandomValue(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}

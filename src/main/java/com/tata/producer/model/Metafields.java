package com.tata.producer.model;

public class Metafields {
    String key;
    String value;

    public Metafields() {
        super();
    }

    public Metafields(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

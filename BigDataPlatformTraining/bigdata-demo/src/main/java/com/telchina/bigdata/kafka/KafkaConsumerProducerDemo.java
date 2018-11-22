package com.telchina.bigdata.kafka;

public class KafkaConsumerProducerDemo {
    public static void main(String args[]) {
        Producer producerThread = new Producer(KafkaProperties.TOPIC, false);
        producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}

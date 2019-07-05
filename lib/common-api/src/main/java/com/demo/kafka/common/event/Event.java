package com.demo.kafka.common.event;

public interface Event {

    Integer partition();

    String topic();
}

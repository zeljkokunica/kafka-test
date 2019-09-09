package com.demo.kafka.common.event;

public interface Event {

    Integer partition();

    default Long version() {
        return 1L;
    }

    String topic();
}

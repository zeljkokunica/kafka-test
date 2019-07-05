package com.demo.kafka.common.event;

public final class Partitioner {

    public static Integer partition(final String value, final Integer partitions) {
        return value.charAt(0) % partitions;
    }

    public static Integer partition(final Integer value, final Integer partitions) {
        return value % partitions;
    }
}

package com.demo.kafka.common;

import lombok.Value;

import java.time.Instant;

@Value
public final class Audit {
    private final String initiatorId;
    private final String ownerId;
    private final Instant time;
}

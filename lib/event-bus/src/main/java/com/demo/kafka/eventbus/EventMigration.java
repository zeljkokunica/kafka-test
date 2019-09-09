package com.demo.kafka.eventbus;

import com.demo.kafka.common.event.Event;
import com.fasterxml.jackson.databind.JsonNode;

public interface EventMigration {

    Class<? extends Event> supportedEvent();

    JsonNode migrate(Long fromVersion, JsonNode read);
}

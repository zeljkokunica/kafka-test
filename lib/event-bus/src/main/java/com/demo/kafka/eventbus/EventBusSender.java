package com.demo.kafka.eventbus;

import com.demo.kafka.common.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;

public final class EventBusSender {

    private final KafkaTemplate kafkaTemplate;

    private final ObjectMapper objectMapper;

    public EventBusSender(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = JsonSerializationProvider.objectMapper();
    }

    /**
     * Immediately send event
     */
    public void sendEventImmediate(final Event event) {
        try {
            final ApplicationEvent applicationEvent = new ApplicationEvent(
                    event.getClass().getName(),
                    objectMapper.writeValueAsString(event));
            kafkaTemplate.send(event.topic(), event.partition(), event.getClass().getSimpleName(), objectMapper.writeValueAsString(applicationEvent));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send event on transaction commit
     */
    public void sendEvent(final Event event) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                public void afterCommit() {
                    sendEventImmediate(event);
                }
            });
        } else {
            sendEventImmediate(event);
        }
    }

    /**
     * Send events on transaction commit
     */
    public void sendEvents(final Collection<Event> events) {
        events.forEach(this::sendEvent);
    }

}

package com.demo.kafka.eventbus;

import com.demo.kafka.common.event.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;

public final class EventBus {

    private final KafkaTemplate kafkaTemplate;

    public EventBus(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Immediately send event
     */
    public void sendEventImmediate(final Event event) {
        kafkaTemplate.send(event.topic(), event.partition(), event.getClass().getSimpleName(), event);
    }

    /**
     * Send event on transaction commit
     */
    public void sendEvent(final Event event) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            public void afterCommit() {
                sendEventImmediate(event);
            }
        });
    }

    /**
     * Send events on transaction commit
     */
    public void sendEvents(final Collection<Event> events) {
        events.forEach(this::sendEvent);
    }

}

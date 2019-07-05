package com.demo.kafka.eventbus;

import com.demo.kafka.common.event.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public final class EventBusListener<T extends Event> {

    private final Map<Class<? extends Event>, EventHandler> handlers = new HashMap<>();

    private final ObjectMapper objectMapper = JsonSerializationProvider.objectMapper();

    public <T extends Event> void registerHandler(final Class<T> eventClass, final EventHandler<T> eventHandler) {
        handlers.put(eventClass, eventHandler);
    }

    public void handleApplicationEvent(final String applicationEventBody) {
        try {
            final ApplicationEvent applicationEvent = objectMapper.readValue(applicationEventBody, ApplicationEvent.class);
            final T event = (T) objectMapper.readValue(applicationEvent.getEventContent(), Class.forName(applicationEvent.getEventName()));
            handle(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle(T event) {
        if (handlers.containsKey(event.getClass())) {
            handlers.get(event.getClass()).handleEvent(event);
        }
    }
}

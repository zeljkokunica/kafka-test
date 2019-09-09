package com.demo.kafka.eventbus;

import com.demo.kafka.common.event.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class EventBusListener<T extends Event> {

    private final Map<Class<? extends Event>, EventHandler> handlers = new HashMap<>();
    private final Map<String, EventMigration> migrations = new HashMap<>();
    private final ObjectMapper objectMapper = JsonSerializationProvider.objectMapper();

    public EventBusListener(final List<EventMigration> migrations) {
        migrations.stream().forEach(eventMigration -> registerMigration(eventMigration.supportedEvent(), eventMigration));
    }

    public <T extends Event> void registerHandler(final Class<T> eventClass, final EventHandler<T> eventHandler) {
        handlers.put(eventClass, eventHandler);
    }

    public void handleApplicationEvent(final String applicationEventBody) {
        try {
            final ApplicationEvent applicationEvent = objectMapper.readValue(applicationEventBody, ApplicationEvent.class);
            log.debug("Received application event {} version: {}", applicationEvent.getEventName(), applicationEvent.getVersion());
            final JsonNode readNode = objectMapper.readTree(applicationEvent.getEventContent());
            final JsonNode processedNode = migrations.containsKey(applicationEvent.getEventName()) ? migrations.get(applicationEvent.getEventName()).migrate(applicationEvent.getVersion(), readNode) : readNode;
            final T event = (T) objectMapper.treeToValue(processedNode, Class.forName(applicationEvent.getEventName()));
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

    private  <T extends Event> void registerMigration(final Class<T> eventClass, final EventMigration eventMigration) {
        migrations.put(eventClass.getName(), eventMigration);
    }
}

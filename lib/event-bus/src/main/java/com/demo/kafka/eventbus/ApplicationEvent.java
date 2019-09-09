package com.demo.kafka.eventbus;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class ApplicationEvent {

    private final String eventName;

    private final Long version;

    private final String eventContent;
}

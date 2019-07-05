package com.demo.kafka.eventbus;

public interface EventHandler<T> {

    void handleEvent(final T event);

}

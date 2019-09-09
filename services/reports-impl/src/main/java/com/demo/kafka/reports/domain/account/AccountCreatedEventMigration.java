package com.demo.kafka.reports.domain.account;

import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.common.event.Event;
import com.demo.kafka.eventbus.EventMigration;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class AccountCreatedEventMigration implements EventMigration {
    @Override
    public Class<? extends Event> supportedEvent() {
        return AccountEvent.AccountCreated.class;
    }

    @Override
    public JsonNode migrate(Long fromVersion, JsonNode read) {
        return read;
    }
}

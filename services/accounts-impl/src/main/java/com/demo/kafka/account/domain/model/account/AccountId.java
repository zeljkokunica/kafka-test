package com.demo.kafka.account.domain.model.account;

import lombok.Value;

import java.util.UUID;

@Value
public final class AccountId {
    private final String id;

    public static AccountId nextId() {
        return new AccountId(UUID.randomUUID().toString());
    }
}

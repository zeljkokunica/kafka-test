package com.demo.kafka.account.domain.model.account;

import com.demo.kafka.common.Audit;
import lombok.Value;

import java.time.Instant;

@Value
public final class Account {
    private AccountId id;
    private String customerId;
    private String name;
    private String number;
    private String reference;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

    public static Account create(
            final String customerId,
            final String name,
            final String number,
            final String reference,
            final Audit audit) {
        return new Account(
                AccountId.nextId(),
                customerId,
                name,
                number,
                reference,
                audit.getTime(),
                audit.getInitiatorId(),
                audit.getTime(),
                audit.getInitiatorId());
    }

    public Account update(
            final Audit audit,
            final String name,
            final String number,
            final String reference) {
        return new Account(id, customerId, name, number, reference, createdAt, createdBy, audit.getTime(), audit.getInitiatorId());
    }
}

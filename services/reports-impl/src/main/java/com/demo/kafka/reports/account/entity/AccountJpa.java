package com.demo.kafka.reports.account.entity;

import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Value
@Entity
@Table(name = "accounts")
public class AccountJpa {
    @Id
    private final String id;

    @Column(name = "customer_id")
    private final String customerId;

    @Column(name = "name")
    private final String name;

    @Column(name = "number")
    private final String number;

    @Column(name = "reference")
    private final String reference;

    @Column(name = "created_at")
    private final Instant createdAt;

    @Column(name = "created_by")
    private final String createdBy;

    @Column(name = "updated_at")
    private final Instant updatedAt;

    @Column(name = "updated_by")
    private final String updatedBy;
}

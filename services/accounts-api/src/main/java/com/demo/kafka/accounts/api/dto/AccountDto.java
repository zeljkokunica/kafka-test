package com.demo.kafka.accounts.api.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class AccountDto {
    private String id;
    private String customerId;
    private String name;
    private String number;
    private String reference;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}

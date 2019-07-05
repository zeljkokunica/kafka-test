package com.demo.kafka.accounts.api.event;

import lombok.Value;

import java.math.BigDecimal;

public interface AccountEvent {

    default Integer partitions() {
        return 10;
    }

    @Value
    final class AccountCreated implements AccountEvent {
        private AccountDetails accountDetails;
    }

    @Value
    final class AccountUpdated implements AccountEvent {
        private AccountDetails accountDetails;
    }

    @Value
    final class AccountTransactionCreated implements AccountEvent {
        private AccountDetails accountDetails;
        private final BigDecimal amount;
        private final String currencyCode;
        private final String description;
    }

    @Value
    final class AccountDetails {
        private final String accountId;
        private final String customerId;
        private final String name;
        private final String number;
        private final String reference;
    }
}

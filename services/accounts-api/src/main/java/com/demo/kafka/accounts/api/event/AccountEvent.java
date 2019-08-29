package com.demo.kafka.accounts.api.event;

import com.demo.kafka.common.Audit;
import com.demo.kafka.common.event.Event;
import com.demo.kafka.common.event.Partitioner;
import lombok.Value;

import java.math.BigDecimal;

public interface AccountEvent extends Event {

    int PARTITIONS = 10;

    String TOPIC = "account-events";

    @Override
    default String topic() {
        return TOPIC;
    }

    @Value
    final class AccountCreated implements AccountEvent {
        private final AccountDetails accountDetails;
        private final Audit audit;

        @Override
        public Integer partition() {
            return Partitioner.partition(accountDetails.customerId, PARTITIONS);
        }
    }

    @Value
    final class AccountUpdated implements AccountEvent {
        private final AccountDetails accountDetails;
        private final Audit audit;

        @Override
        public Integer partition() {
            return Partitioner.partition(accountDetails.customerId, PARTITIONS);
        }
    }

    @Value
    final class AccountTransactionCreated implements AccountEvent {
        private final AccountDetails accountDetails;
        private final BigDecimal amount;
        private final String currencyCode;
        private final String description;

        @Override
        public Integer partition() {
            return Partitioner.partition(accountDetails.customerId, PARTITIONS);
        }
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

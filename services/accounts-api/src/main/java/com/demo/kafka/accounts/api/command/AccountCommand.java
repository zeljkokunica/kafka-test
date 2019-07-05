package com.demo.kafka.accounts.api.command;

import lombok.Value;

public interface AccountCommand {

    @Value
    final class CreateAccount implements AccountCommand {
        private final String customerId;
        private final String name;
        private final String number;
        private final String reference;
    }
}

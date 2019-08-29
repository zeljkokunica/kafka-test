package com.demo.kafka.accounts.api.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

public interface AccountCommand {

    @Value
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    final class CreateAccount implements AccountCommand {
        private final String customerId;
        private final String name;
        private final String number;
        private final String reference;
    }

    @Value
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    final class UpdateAccount implements AccountCommand {
        private final String name;
        private final String number;
        private final String reference;
    }
}

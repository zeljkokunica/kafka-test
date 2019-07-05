package com.demo.kafka.account.service;

import com.demo.kafka.accounts.api.command.AccountCommand;
import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.common.Audit;
import com.demo.kafka.eventbus.EventBus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountCreateService {

    private EventBus eventBus;

    public AccountCreateService(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void createAccount(final Audit audit, final AccountCommand.CreateAccount command) {
        // todo create db and things
        final AccountEvent.AccountCreated event = new AccountEvent.AccountCreated(
                new AccountEvent.AccountDetails(
                        UUID.randomUUID().toString(),
                        command.getCustomerId(),
                        command.getName(),
                        command.getNumber(),
                        command.getReference()
                ),
                audit
        );
        eventBus.sendEvent(event);
    }


}

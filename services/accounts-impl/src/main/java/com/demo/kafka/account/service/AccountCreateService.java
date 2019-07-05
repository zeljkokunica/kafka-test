package com.demo.kafka.account.service;

import com.demo.kafka.accounts.api.command.AccountCommand;
import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.common.Audit;
import com.demo.kafka.eventbus.EventBusSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountCreateService {

    private EventBusSender eventBus;

    public AccountCreateService(final EventBusSender eventBus) {
        this.eventBus = eventBus;
    }

    @Transactional
    public AccountEvent.AccountDetails createAccount(final Audit audit, final AccountCommand.CreateAccount command) {
        final AccountEvent.AccountDetails accountDetails = new AccountEvent.AccountDetails(
                UUID.randomUUID().toString(),
                command.getCustomerId(),
                command.getName(),
                command.getNumber(),
                command.getReference()
        );
        // todo create db and things
        final AccountEvent.AccountCreated event = new AccountEvent.AccountCreated(
                accountDetails,
                audit
        );
        eventBus.sendEvent(event);
        return accountDetails;
    }


}

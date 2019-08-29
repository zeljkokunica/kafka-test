package com.demo.kafka.account.service;

import com.demo.kafka.account.domain.jpa.AccountRepository;
import com.demo.kafka.account.domain.model.account.Account;
import com.demo.kafka.account.domain.model.account.AccountId;
import com.demo.kafka.accounts.api.command.AccountCommand;
import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.common.Audit;
import com.demo.kafka.eventbus.EventBusSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountCreateService {

    private final EventBusSender eventBus;

    private final AccountRepository accountRepository;

    public AccountCreateService(
            EventBusSender eventBus,
            AccountRepository accountRepository) {
        this.eventBus = eventBus;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(final Audit audit, final AccountCommand.CreateAccount command) {
        final Account account = Account.create(
                command.getCustomerId(),
                command.getName(),
                command.getNumber(),
                command.getReference(),
                audit);
        final Account updated = accountRepository.save(account);
        sendAccountCreatedEvent(audit, updated);
        return updated;
    }

    @Transactional
    public Account updateAccount(final Audit audit, final AccountId accountId, final AccountCommand.UpdateAccount command) {
        final Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .update(audit, command.getName(), command.getNumber(), command.getReference());
        final Account updated = accountRepository.save(account);
        sendAccountUpdatedEvent(audit, updated);
        return updated;
    }

    private void sendAccountCreatedEvent(final Audit audit, final Account account) {
        final AccountEvent.AccountDetails accountDetails = new AccountEvent.AccountDetails(
                account.getId().getId(),
                account.getCustomerId(),
                account.getName(),
                account.getNumber(),
                account.getReference()
        );
        final AccountEvent.AccountCreated event = new AccountEvent.AccountCreated(
                accountDetails,
                audit
        );
        eventBus.sendEvent(event);
    }

    private void sendAccountUpdatedEvent(final Audit audit, final Account account) {
        final AccountEvent.AccountDetails accountDetails = new AccountEvent.AccountDetails(
                account.getId().getId(),
                account.getCustomerId(),
                account.getName(),
                account.getNumber(),
                account.getReference()
        );
        final AccountEvent.AccountUpdated event = new AccountEvent.AccountUpdated(
                accountDetails,
                audit
        );
        eventBus.sendEvent(event);
    }
}

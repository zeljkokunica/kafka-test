package com.demo.kafka.reports.account;

import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.eventbus.EventBusListener;
import com.demo.kafka.reports.account.entity.AccountJpa;
import com.demo.kafka.reports.account.entity.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountLogListener {

    private final AccountRepository accountRepository;

    private final EventBusListener<AccountEvent> accountEventEventBusListener;

    public AccountLogListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountEventEventBusListener = new EventBusListener<>();
        accountEventEventBusListener.registerHandler(AccountEvent.AccountCreated.class, this::onAccountCreated);
        accountEventEventBusListener
                .registerHandler(AccountEvent.AccountTransactionCreated.class, this::onAccountTransactionCreated);
    }

    @KafkaListener(id = "reports-account-log", topics = AccountEvent.TOPIC)
    public void onAccountEvent(final String event) {
        accountEventEventBusListener.handleApplicationEvent(event);
    }

    private void onAccountCreated(final AccountEvent.AccountCreated event) {
        accountRepository.save(
                new AccountJpa(
                        event.getAccountDetails().getAccountId(),
                        event.getAccountDetails().getCustomerId(),
                        event.getAccountDetails().getName(),
                        event.getAccountDetails().getNumber(),
                        event.getAccountDetails().getReference(),
                        event.getAudit().getTime(),
                        event.getAudit().getInitiatorId(),
                        event.getAudit().getTime(),
                        event.getAudit().getInitiatorId()));
        log.info("Account created event: {}", event.toString());
    }

    private void onAccountTransactionCreated(final AccountEvent.AccountTransactionCreated event) {
        log.info("Account transaction created event: {}", event.toString());
    }
}

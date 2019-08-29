package com.demo.kafka.reports.domain.account;

import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.eventbus.EventBusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountLogListener {

    private final AccountReportRepository accountRepository;

    private final EventBusListener<AccountEvent> accountEventEventBusListener;

    public AccountLogListener(AccountReportRepository accountRepository) {
        this.accountRepository = accountRepository;
        accountEventEventBusListener = new EventBusListener<>();
        accountEventEventBusListener.registerHandler(AccountEvent.AccountCreated.class, this::onAccountCreated);
        accountEventEventBusListener.registerHandler(AccountEvent.AccountUpdated.class, this::onAccountUpdated);
        accountEventEventBusListener
                .registerHandler(AccountEvent.AccountTransactionCreated.class, this::onAccountTransactionCreated);
    }

    @KafkaListener(id = "reports-accounts-logger", topics = AccountEvent.TOPIC)
    public void onAccountEvent(final String event) {
        accountEventEventBusListener.handleApplicationEvent(event);
    }

    private void onAccountCreated(final AccountEvent.AccountCreated event) {
        accountRepository.save(
                new AccountReportJpa(
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

    private void onAccountUpdated(final AccountEvent.AccountUpdated event) {
        accountRepository.save(
                new AccountReportJpa(
                        event.getAccountDetails().getAccountId(),
                        event.getAccountDetails().getCustomerId(),
                        event.getAccountDetails().getName(),
                        event.getAccountDetails().getNumber(),
                        event.getAccountDetails().getReference(),
                        event.getAudit().getTime(),
                        event.getAudit().getInitiatorId(),
                        event.getAudit().getTime(),
                        event.getAudit().getInitiatorId()));
        log.info("Account updated event: {}", event.toString());
    }

    private void onAccountTransactionCreated(final AccountEvent.AccountTransactionCreated event) {
        log.info("Account transaction created event: {}", event.toString());
    }
}

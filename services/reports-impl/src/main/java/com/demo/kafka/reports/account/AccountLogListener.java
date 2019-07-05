package com.demo.kafka.reports.account;

import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.reports.account.entity.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountLogListener {

    private final AccountRepository accountRepository;

    private final ObjectMapper objectMapper;

    public AccountLogListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(id = "reports-account-log", topics = AccountEvent.TOPIC)
    public void onAccountEvent(final String event) {
        log.info("Account event: {}", event);
    }
}

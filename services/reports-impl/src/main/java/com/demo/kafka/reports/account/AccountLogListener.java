package com.demo.kafka.reports.account;

import com.demo.kafka.accounts.api.event.AccountEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountLogListener {

    @KafkaListener(id = "reports-account-log", topics = AccountEvent.TOPIC)
    public void onAccountEvent(final AccountEvent accountEvent) {
        log.info("Account event: {}", accountEvent);
    }
}

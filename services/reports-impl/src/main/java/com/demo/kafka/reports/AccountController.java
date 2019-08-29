package com.demo.kafka.reports;

import com.demo.kafka.reports.domain.account.AccountReportJpa;
import com.demo.kafka.reports.domain.account.AccountReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/accounts", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    private final AccountReportRepository accountRepository;

    public AccountController(AccountReportRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<AccountReportJpa> getAccounts() {
        return accountRepository.findAll();
    }
}

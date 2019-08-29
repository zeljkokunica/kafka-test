package com.demo.kafka.account.service;

import com.demo.kafka.account.domain.jpa.AccountRepository;
import com.demo.kafka.account.domain.model.account.Account;
import com.demo.kafka.account.domain.model.account.AccountId;
import com.demo.kafka.common.Audit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountQueryService {
    private final AccountRepository accountRepository;

    public AccountQueryService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll(final Audit audit) {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(final Audit audit, final AccountId accountId) {
        return accountRepository.findById(accountId);
    }
}

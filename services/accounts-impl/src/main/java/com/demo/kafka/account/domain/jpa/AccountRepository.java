package com.demo.kafka.account.domain.jpa;

import com.demo.kafka.account.domain.model.account.Account;
import com.demo.kafka.account.domain.model.account.AccountId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class AccountRepository {
    private final EntityManager entityManager;

    public AccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Account save(final Account account) {
        final AccountJpa jpa = new AccountJpa(
                account.getId().getId(),
                account.getCustomerId(),
                account.getName(),
                account.getNumber(),
                account.getReference(),
                account.getCreatedAt(),
                account.getCreatedBy(),
                account.getUpdatedAt(),
                account.getUpdatedBy());
        final AccountJpa saved = entityManager.merge(jpa);
        return fromJpa(saved);
    }

    public List<Account> findAll() {
        return entityManager.createNamedQuery("AccountJpa.findAll", AccountJpa.class)
                .getResultStream()
                .map(this::fromJpa)
                .collect(Collectors.toList());
    }

    public Optional<Account> findById(final AccountId accountId) {
        return Optional.ofNullable(entityManager.find(AccountJpa.class, accountId.getId()))
                .map(this::fromJpa);
    }

    private Account fromJpa(final AccountJpa jpa) {
        return new Account(
                new AccountId(jpa.getId()),
                jpa.getCustomerId(),
                jpa.getName(),
                jpa.getNumber(),
                jpa.getReference(),
                jpa.getCreatedAt(),
                jpa.getCreatedBy(),
                jpa.getUpdatedAt(),
                jpa.getUpdatedBy());
    }
}

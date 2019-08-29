package com.demo.kafka.account.mapper;

import com.demo.kafka.account.domain.model.account.Account;
import com.demo.kafka.accounts.api.dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountMapper {

    public <T, R> List<R> mapList(final List<T> items, final Function<T, R> mapper) {
        return items.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public AccountDto map(final Account account) {
        return new AccountDto(
                account.getId().getId(),
                account.getCustomerId(),
                account.getName(),
                account.getNumber(),
                account.getReference(),
                account.getCreatedAt(),
                account.getCreatedBy(),
                account.getUpdatedAt(),
                account.getUpdatedBy());
    }
}

package com.demo.kafka.account;

import com.demo.kafka.account.domain.model.account.AccountId;
import com.demo.kafka.account.mapper.AccountMapper;
import com.demo.kafka.account.service.AccountCreateService;
import com.demo.kafka.account.service.AccountQueryService;
import com.demo.kafka.accounts.api.command.AccountCommand;
import com.demo.kafka.accounts.api.dto.AccountDto;
import com.demo.kafka.common.Audit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/accounts", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    private final AccountCreateService accountCreateService;
    private final AccountQueryService accountQueryService;
    private final AccountMapper accountMapper;

    public AccountController(AccountCreateService accountCreateService,
            AccountQueryService accountQueryService, AccountMapper accountMapper) {
        this.accountCreateService = accountCreateService;
        this.accountQueryService = accountQueryService;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public List<AccountDto> getAccounts() {
        return accountMapper.mapList(accountQueryService.findAll(Audit.test()), accountMapper::map);
    }

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable final String id) {
        return accountMapper.map(
                accountQueryService.findById(Audit.test(), new AccountId(id))
                    .orElseThrow(() -> new IllegalArgumentException("Account not found")));
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody final AccountCommand.CreateAccount command) {
        return accountMapper.map(accountCreateService.createAccount(Audit.test(), command));
    }

    @PutMapping("/{id}")
    public AccountDto updateAccount(@PathVariable final String id, @RequestBody final AccountCommand.UpdateAccount command) {
        return accountMapper.map(accountCreateService.updateAccount(Audit.test(), new AccountId(id), command));
    }

}

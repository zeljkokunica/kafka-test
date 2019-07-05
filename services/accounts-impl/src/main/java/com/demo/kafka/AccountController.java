package com.demo.kafka;

import com.demo.kafka.account.service.AccountCreateService;
import com.demo.kafka.accounts.api.command.AccountCommand;
import com.demo.kafka.accounts.api.event.AccountEvent;
import com.demo.kafka.common.Audit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/accounts", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    private final AccountCreateService accountCreateService;

    public AccountController(AccountCreateService accountCreateService) {
        this.accountCreateService = accountCreateService;
    }

    @PostMapping
    public AccountEvent.AccountDetails createAccount(@RequestBody final AccountCommand.CreateAccount command) {
        return accountCreateService.createAccount(Audit.test(), command);
    }
}

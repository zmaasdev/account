package nl.zoe.account.controller;

import jakarta.validation.Valid;
import nl.zoe.account.dto.AccountDTO;
import nl.zoe.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO create(@RequestBody @Valid AccountDTO accountDTO) {
        return this.accountService.create(accountDTO);
    }
}

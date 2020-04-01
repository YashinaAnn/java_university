package com.university.accounts.web.controllers;

import com.university.accounts.backend.dto.CreateAccountRequestDto;
import com.university.accounts.backend.service.AccountService;
import com.university.accounts.backend.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequestDto request) {
        service.createAccount(request);
        return ResponseEntity.ok("Account successfully created");
    }

    @GetMapping
    public ResponseEntity<List<Account>> getUserAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @GetMapping("/{account}")
    public ResponseEntity<Account> getUserAccount(@PathVariable("account") long account) {
        return ResponseEntity.ok(service.getAccount(account));
    }

    @PostMapping("/default/{account}")
    public void setDefault(@PathVariable("account") long account) {
        service.updateDefault(account);
    }
}

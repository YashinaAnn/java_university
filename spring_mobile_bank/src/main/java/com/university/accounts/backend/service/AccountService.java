package com.university.accounts.backend.service;

import com.university.accounts.backend.dao.AccountRepository;
import com.university.accounts.backend.dao.UserRepository;
import com.university.accounts.backend.dto.CreateAccountRequestDto;
import com.university.accounts.backend.model.Account;
import com.university.accounts.backend.model.User;
import com.university.accounts.web.exceptions.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.university.accounts.web.exceptions.ServiceError.ACCOUNT_NOT_FOUND;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public void createAccount(CreateAccountRequestDto request) {
        User user = authenticationService.getAuthenticatedUser();

        Account account = new Account(request);
        List<Account> accounts = user.getAccounts();
        if (null == accounts || accounts.isEmpty()) {
            account.setDefault(true);
        }

        user.addAccount(account);
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getAccounts();
    }

    public void updateDefault(long accountId) {
        getAccount(accountId);

        accountRepository.setIsDefaultById(getDefaultAccount().getId(), false);
        accountRepository.setIsDefaultById(accountId, true);
    }

    public Account getAccount(long accountId) {
        List<Account> accounts = getAllAccounts();
        return accounts.stream().filter(acc -> acc.getId() == accountId)
                .findFirst()
                .orElseThrow(() -> new NotFound(ACCOUNT_NOT_FOUND.message()));
    }

    Account getDefaultAccount(User user) {
        return user.getAccounts().stream().filter(Account::isDefault).findFirst()
                .orElseThrow(() -> new NotFound(ACCOUNT_NOT_FOUND.message()));
    }

    Account getDefaultAccount() {
        return getDefaultAccount(authenticationService.getAuthenticatedUser());
    }
}

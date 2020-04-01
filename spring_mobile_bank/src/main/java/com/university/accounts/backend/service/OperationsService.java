package com.university.accounts.backend.service;

import com.university.accounts.backend.dao.AccountRepository;
import com.university.accounts.backend.dao.OperationRepository;
import com.university.accounts.backend.dto.MoneyTransferRequestDto;
import com.university.accounts.backend.dto.PutMoneyRequestDto;
import com.university.accounts.backend.model.Account;
import com.university.accounts.backend.model.Operation;
import com.university.accounts.backend.model.OperationType;
import com.university.accounts.backend.model.User;
import com.university.accounts.backend.utils.CurrencyConverter;
import com.university.accounts.web.exceptions.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.university.accounts.backend.utils.Utils.getCurrentDate;
import static com.university.accounts.web.exceptions.ServiceError.NOT_ENOUGH_MONEY;

@Service
public class OperationsService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    public void putMoney(PutMoneyRequestDto request) {
        Account account = accountService.getAccount(request.getAccount());

        Operation operation = new Operation(request);
        BigDecimal amount = CurrencyConverter
                .convert(operation.getAmount(), operation.getCurrency(), account.getCurrency());
        BigDecimal amountBefore = account.getAmount();
        BigDecimal amountAfter = amountBefore.add(amount);
        accountRepository.setAmountById(operation.getAccountTo(), amountAfter);

        operation.setAmountBefore(amountBefore);
        operation.setAmountAfter(amountAfter);
        operation.setDate(getCurrentDate());
        operationRepository.save(operation);
    }

    public void transfer(MoneyTransferRequestDto request) {
        Account accountFrom = accountService.getAccount(request.getAccountId());

        Operation operationTransfer = new Operation(request);
        Operation operationReceipts = new Operation(request);
        String date = getCurrentDate();

        BigDecimal amount = CurrencyConverter.convert(operationTransfer.getAmount(),
                operationTransfer.getCurrency(), accountFrom.getCurrency());
        BigDecimal amountBefore = accountFrom.getAmount();

        if (amountBefore.compareTo(amount) < 0) {
            throw new BadRequest(NOT_ENOUGH_MONEY.message());
        }
        operationTransfer.setAmountBefore(amountBefore);
        operationTransfer.setAmountAfter(amountBefore.subtract(amount));

        User recipient = userService.findByPhone(request.getPhone());
        Account recipientAccount = accountService.getDefaultAccount(recipient);
        operationTransfer.setAccountTo(recipientAccount.getId());
        operationReceipts.setAccountTo(recipientAccount.getId());

        BigDecimal recipientAmountBefore = recipientAccount.getAmount();
        BigDecimal recipientAmountAfter = recipientAmountBefore
                .add(CurrencyConverter.convert(amount, accountFrom.getCurrency(), recipientAccount.getCurrency()));

        accountRepository.setAmountById(operationTransfer.getAccountFrom(), operationTransfer.getAmountAfter());
        accountRepository.setAmountById(operationTransfer.getAccountTo(), recipientAmountAfter);
        operationTransfer.setDate(date);
        operationRepository.save(operationTransfer);

        operationReceipts.setAmountBefore(recipientAmountBefore);
        operationReceipts.setAmountAfter(recipientAmountAfter);
        operationReceipts.setType(OperationType.RECEIPTS);
        operationReceipts.setDate(date);
        operationRepository.save(operationReceipts);
    }

    public List<Operation> getOperationsList() {
        User user = authenticationService.getAuthenticatedUser();
        List<Long> accounts = user.getAccounts().stream().map(Account::getId).collect(Collectors.toList());
        return operationRepository.findByAccounts(accounts);
    }
}

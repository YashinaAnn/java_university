package com.pack.model;

import com.pack.dto.UpdateAccountAmountRequestDto;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateAccountAmountInfo {

    private UUID token;
    private UUID accountToId;
    private UUID accountFromId;
    private BigDecimal amount;
    private AccCode accCode;

    public UpdateAccountAmountInfo(UUID token, UUID accountToId,
                                   UUID accountFromId, BigDecimal amount, AccCode accCode) {
        setToken(token);
        this.accountToId = accountToId;
        this.accountFromId = accountFromId;
        setAmount(amount);
        this.accCode = accCode;
    }

    public UpdateAccountAmountInfo(UUID token, UUID accountToId,
                                   UUID accountFromId, BigDecimal amount, String accCode) {
        setToken(token);
        this.accountToId = accountToId;
        this.accountFromId = accountFromId;
        setAmount(amount);
        setAccCode(accCode);
    }

    public UpdateAccountAmountInfo(UpdateAccountAmountRequestDto request) {
        this(request.getToken(), request.getAccountToId(), request.getAccountFromId(), request.getAmount(), request.getAccCode());
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        if (null == token) {
            throw new IllegalArgumentException("Token is null");
        }
        this.token = token;
    }

    public UUID getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(UUID accountToId) {
        this.accountToId = accountToId;
    }

    public UUID getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(UUID accountFromId) {
        this.accountFromId = accountFromId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount.doubleValue() < 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        this.amount = amount;
    }

    public AccCode getAccCode() {
        return accCode;
    }

    public void setAccCode(AccCode accCode) {
        this.accCode = accCode;
    }

    public void setAccCode(String accCode) {
        try {
            this.accCode = AccCode.valueOf(accCode.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown account code from: RUB, EURO, USD");
        }
    }
}

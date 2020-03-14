package com.pack.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class UpdateAccountAmountRequestDto implements Serializable {

    private UUID token;
    private UUID accountToId;
    private UUID accountFromId;
    private BigDecimal amount;
    private String accCode;

    public UpdateAccountAmountRequestDto(UUID token, UUID accountToId, UUID accountFromId, BigDecimal amount, String accCode) {
        this(token, accountToId, amount, accCode);
        this.accountFromId = accountFromId;
    }

    public UpdateAccountAmountRequestDto(UUID token, UUID accountToId, BigDecimal amount, String accCode) {
        this(token, accountToId, amount);
        this.accCode = accCode;
    }

    public UpdateAccountAmountRequestDto(UUID token, UUID accountToId, BigDecimal amount) {
        this.token = token;
        this.accountToId = accountToId;
        this.amount = amount;
    }

    public UpdateAccountAmountRequestDto(BigDecimal amount, String accCode) {
        this.amount = amount;
        this.accCode = accCode;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
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
        this.amount = amount;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }
}

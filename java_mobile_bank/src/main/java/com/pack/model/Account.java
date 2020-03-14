package com.pack.model;

import com.pack.dto.CreateAccountDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Account implements Serializable {

    private UUID id;
    private long clientId;
    private BigDecimal amount;
    private AccCode accCode;

    public Account(UUID id, long clientId, BigDecimal amount, AccCode accCode) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount;
        this.accCode = accCode;
    }

    public Account(String id, long clientId, BigDecimal amount, String accCode) {
        this(UUID.fromString(id), clientId, amount, AccCode.valueOf(accCode));
    }

    public Account(CreateAccountDto createAccountDto) {
        this(createAccountDto.getAmount(), createAccountDto.getAccCode());
    }

    public Account(BigDecimal amount, String accCode) {
        this(UUID.randomUUID(), amount, accCode);
    }

    public Account(UUID id, BigDecimal amount, AccCode accCode) {
        this.id = id;
        setAccCode(accCode);
        setAmount(amount);
    }

    public Account(UUID id, BigDecimal amount, String accCode) {
        this.id = id;
        setAccCode(accCode);
        setAmount(amount);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public static void validateAmount(BigDecimal money) {
        if (null == money || money.longValue() < 0) {
            throw new IllegalArgumentException("Money should be positive value");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                clientId == account.clientId &&
                Objects.equals(amount, account.amount) &&
                accCode == account.accCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, amount, accCode);
    }
}

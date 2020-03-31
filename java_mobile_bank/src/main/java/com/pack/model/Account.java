package com.pack.model;

import com.pack.dto.CreateAccountRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account implements Serializable {

    private UUID id;
    private long clientId;
    private BigDecimal amount;
    private Currency accCode;

    public Account(String id, long clientId, BigDecimal amount, String accCode) {
        this(UUID.fromString(id), clientId, amount, Currency.valueOf(accCode));
    }

    public Account(CreateAccountRequestDto createAccountDto) {
        this(createAccountDto.getAmount(), createAccountDto.getAccCode());
    }

    public Account(BigDecimal amount, String accCode) {
        this(UUID.randomUUID(), amount, accCode);
    }

    public Account(UUID id, BigDecimal amount, String accCode) {
        this.id = id;
        setAccCode(accCode);
        setAmount(amount);
    }

    public void setAccCode(String accCode) {
        try {
            this.accCode = Currency.valueOf(accCode.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown account code from: RUB, EURO, USD");
        }
    }

    public static void validateAmount(BigDecimal money) {
        if (null == money || money.longValue() < 0) {
            throw new IllegalArgumentException("Money should be positive value");
        }
    }

    public String toString() {
        return String.format("ACCOUNT: %s  AMOUNT: %s %s", id, amount, accCode);
    }
}

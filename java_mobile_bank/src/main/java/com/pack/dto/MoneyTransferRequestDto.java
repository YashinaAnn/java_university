package com.pack.dto;

import com.pack.model.AccCode;

import java.math.BigDecimal;
import java.util.UUID;

public class MoneyTransferRequestDto {

    private UUID token;
    private String phone;
    private BigDecimal amount;
    private AccCode accCode;

    public MoneyTransferRequestDto(UUID token, String phone, BigDecimal amount, AccCode accCode) {
        this.token = token;
        this.phone = phone;
        this.amount = amount;
        this.accCode = accCode;
    }

    public MoneyTransferRequestDto(UUID token, String phone, BigDecimal amount) {
        this.token = token;
        this.phone = phone;
        this.amount = amount;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}

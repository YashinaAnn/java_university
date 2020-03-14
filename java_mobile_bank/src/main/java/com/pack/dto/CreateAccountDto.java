package com.pack.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountDto implements Serializable {

    private UUID token;
    private BigDecimal amount;
    private String accCode;

    public CreateAccountDto(UUID token, String accCode) {
        this.token = token;
        this.accCode = accCode;
        this.amount = new BigDecimal(0);
    }

    public CreateAccountDto(String accCode) {
        this.accCode = accCode;
        this.amount = new BigDecimal(0);
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
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

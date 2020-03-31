package com.pack.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class TransferMoneyRequestDto implements Serializable {

    private UUID token;
    private UUID accountToId;
    private UUID accountFromId;
    private BigDecimal amount;
    private String accCode;

    public TransferMoneyRequestDto(UUID token, UUID accountToId, UUID accountFromId, BigDecimal amount, String accCode) {
        this(token, accountToId, amount, accCode);
        this.accountFromId = accountFromId;
    }

    public TransferMoneyRequestDto(UUID token, UUID accountToId, BigDecimal amount, String accCode) {
        this(token, accountToId, amount);
        this.accCode = accCode;
    }

    public TransferMoneyRequestDto(UUID token, UUID accountToId, BigDecimal amount) {
        this.token = token;
        this.accountToId = accountToId;
        this.amount = amount;
    }

    public TransferMoneyRequestDto(BigDecimal amount, String accCode) {
        this.amount = amount;
        this.accCode = accCode;
    }
}

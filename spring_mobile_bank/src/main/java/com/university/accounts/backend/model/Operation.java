package com.university.accounts.backend.model;

import com.university.accounts.backend.dto.MoneyTransferRequestDto;
import com.university.accounts.backend.dto.PutMoneyRequestDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "operation")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "date")
    private String date;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "account_from")
    private Long accountFrom;

    @NonNull
    @Column(name = "account_to")
    private Long accountTo;

    @NonNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NonNull
    @Column(name = "amount_before")
    private BigDecimal amountBefore;

    @NonNull
    @Column(name = "amount_after")
    private BigDecimal amountAfter;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    public Operation(PutMoneyRequestDto request) {
        this.accountTo = request.getAccount();
        this.amount = request.getAmount();
        this.currency = request.getCurrency();
        this.type = OperationType.REPLENISHMENT;
    }

    public Operation(MoneyTransferRequestDto request) {
        this.accountFrom = request.getAccountId();
        this.amount = request.getAmount();
        this.currency = request.getCurrency();
        this.type = OperationType.TRANSFER;
    }
}

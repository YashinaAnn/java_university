package com.pack.model;

import com.pack.dto.TransferMoneyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static com.pack.utils.Constants.DATE_FORMAT;

@NoArgsConstructor
@Getter
@Setter
public class Operation implements Serializable {

    private long id;
    private String date;
    private Currency accCode;
    private UUID accountFrom;
    private UUID accountTo;
    private BigDecimal amount;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;
    private OperationType type;

    public Operation(long id, String date, String accCode,
                     UUID accountFrom, UUID accountTo, BigDecimal amount) {
        this.id = id;
        this.date = date;
        setAccCode(accCode);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        setAmount(amount);
    }

    public Operation(long id, String date, Currency accCode,
                     UUID accountFrom, UUID accountTo, BigDecimal amount,
                     BigDecimal amountBefore, BigDecimal amountAfter, OperationType type) {
        this(id, date, accCode.name(), accountFrom, accountTo, amount);
        this.amountBefore = amountBefore;
        this.amountAfter = amountAfter;
        this.type = type;
    }

    public Operation(long id, String date, String accCode,
                     String accountFrom, String accountTo, BigDecimal amount,
                     BigDecimal amountBefore, BigDecimal amountAfter, OperationType type) {
        this(id, date,
                Currency.valueOf(accCode),
                (OperationType.REPLENISHMENT.equals(type)) ? null : UUID.fromString(accountFrom),
                UUID.fromString(accountTo),
                amount, amountBefore, amountAfter, type);
    }

    public Operation(TransferMoneyRequestDto request) {
        this(0, DATE_FORMAT.format(new Date()), request.getAccCode(),
                request.getAccountFromId(), request.getAccountToId(), request.getAmount());
        this.type = OperationType.TRANSFER;
    }

    public void setAccCode(String accCode) {
        try {
            this.accCode = Currency.valueOf(accCode.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown account code from: RUB, EURO, USD");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nDATE: ").append(date)
                .append("\n   Operation: ").append(type)
                .append("\n   Amount: ").append(amount).append(" ").append(accCode)
                .append("\n   Amount before: ").append(amountBefore)
                .append("\n   Amount after: ").append(amountAfter);
        if (OperationType.TRANSFER.equals(type)) {
            sb.append("\n   Account: ").append(getAccountFrom())
                    .append("\n   Account recipient: ******")
                    .append(getAccountTo().toString().substring(getAccountFrom().toString().length() - 4));
        } else {
            sb.append("\n   Account: ").append(accountTo);
        }
        return sb.toString();
    }
}

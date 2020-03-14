package com.pack.model;

import com.pack.dto.UpdateAccountAmountRequestDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.pack.utils.Constants.DATE_FORMAT;

public class Operation implements Serializable {

    private long id;
    private String date;
    private AccCode accCode;
    private UUID accountFrom;
    private UUID accountTo;
    private BigDecimal amount;
    private BigDecimal amountBefore;
    private BigDecimal amountAfter;

    public Operation(long id, String date, AccCode accCode,
                     UUID accountFrom, UUID accountTo, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.accCode = accCode;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Operation(long id, String date, String accCode,
                     UUID accountFrom, UUID accountTo, BigDecimal amount) {
        this.id = id;
        this.date = date;
        setAccCode(accCode);
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        setAmount(amount);
    }

    public Operation(long id, String date, AccCode accCode,
                     UUID accountFrom, UUID accountTo, BigDecimal amount,
                     BigDecimal amountBefore, BigDecimal amountAfter) {
        this(id, date, accCode, accountFrom, accountTo, amount);
        this.amountBefore = amountBefore;
        this.amountAfter = amountAfter;
    }

    public Operation(long id, String date, String accCode,
                     String accountFrom, String accountTo, BigDecimal amount,
                     BigDecimal amountBefore, BigDecimal amountAfter) {
        this(id, date, AccCode.valueOf(accCode),
                UUID.fromString(accountFrom), UUID.fromString(accountTo),
                amount, amountBefore, amountAfter);
    }

    public Operation(UpdateAccountAmountRequestDto request) {
        this(0, DATE_FORMAT.format(new Date()), request.getAccCode(),
                request.getAccountFromId(), request.getAccountToId(), request.getAmount());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public UUID getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(UUID accountFrom) {
        this.accountFrom = accountFrom;
    }

    public UUID getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(UUID accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(BigDecimal amountBefore) {
        this.amountBefore = amountBefore;
    }

    public BigDecimal getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(BigDecimal amountAfter) {
        this.amountAfter = amountAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                Objects.equals(date, operation.date) &&
                accCode == operation.accCode &&
                Objects.equals(accountFrom, operation.accountFrom) &&
                Objects.equals(accountTo, operation.accountTo) &&
                Objects.equals(amount, operation.amount) &&
                Objects.equals(amountBefore, operation.amountBefore) &&
                Objects.equals(amountAfter, operation.amountAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, accCode, accountFrom, accountTo, amount, amountBefore, amountAfter);
    }

    @Override
    public String toString() {
        String accFrom = accountFrom.toString();
        String accTo = accountTo.toString();
        return "\n\nDATE: " + date +
                "\n   Amount: " + amount +
                " " + accCode +
                "\n   Account from: *****" + accFrom.substring(accFrom.length() - 4) +
                "\n   Account to: *****" + accTo.substring(accFrom.length() - 4) +
                "\n   Amount before: " + amountBefore +
                "\n   Amount after: " + amountAfter;
    }
}

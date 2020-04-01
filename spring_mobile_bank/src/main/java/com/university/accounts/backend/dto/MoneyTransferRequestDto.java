package com.university.accounts.backend.dto;

import com.university.accounts.backend.model.Currency;
import com.university.accounts.backend.utils.SupportedCurrency;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MoneyTransferRequestDto {

    @NonNull
    private String phone;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private long accountId;

    @NonNull
    @Enumerated(EnumType.STRING)
    @SupportedCurrency
    private Currency currency;
}

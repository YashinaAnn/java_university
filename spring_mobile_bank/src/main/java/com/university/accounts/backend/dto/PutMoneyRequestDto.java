package com.university.accounts.backend.dto;

import com.university.accounts.backend.model.Currency;
import com.university.accounts.backend.utils.SupportedCurrency;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutMoneyRequestDto {

    @NonNull
    private long account;

    @NonNull
    private BigDecimal amount;

    @NonNull
    @Enumerated(EnumType.STRING)
    @SupportedCurrency
    private Currency currency;
}

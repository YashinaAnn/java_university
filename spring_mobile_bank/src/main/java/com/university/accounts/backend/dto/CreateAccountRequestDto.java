package com.university.accounts.backend.dto;

import com.university.accounts.backend.model.Currency;
import com.university.accounts.backend.utils.SupportedCurrency;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreateAccountRequestDto {

    @Enumerated(EnumType.STRING)
    @NonNull
    @SupportedCurrency
    private Currency currency;
}

package com.pack.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAccountRequestDto implements Serializable {

    private UUID token;
    private BigDecimal amount;
    private String accCode;

    public CreateAccountRequestDto(UUID token, String accCode) {
        this.token = token;
        this.accCode = accCode;
        this.amount = new BigDecimal(0);
    }

    public CreateAccountRequestDto(String accCode) {
        this.accCode = accCode;
        this.amount = new BigDecimal(0);
    }
}

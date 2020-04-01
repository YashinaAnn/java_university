package com.university.accounts.backend.model;

import com.university.accounts.backend.dto.CreateAccountRequestDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    private Long id;

    @NonNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "currency")
    private Currency currency;

    @NonNull
    @Column(name = "is_default")
    private boolean isDefault;

    public Account(CreateAccountRequestDto requestDto) {
        this.amount = new BigDecimal("0");
        this.isDefault = false;
        this.currency = requestDto.getCurrency();
    }
}

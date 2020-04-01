package com.university.accounts.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CredentialsDto {

    @NonNull
    private String name;

    @NonNull
    private String password;
}

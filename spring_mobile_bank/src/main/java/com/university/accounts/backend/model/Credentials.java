package com.university.accounts.backend.model;

import com.university.accounts.backend.dto.CredentialsDto;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Credentials implements Serializable {

    @NonNull
    private String name;

    @NonNull
    private String password;

    public Credentials(CredentialsDto credentialsDto) {
        this(credentialsDto.getName(), credentialsDto.getPassword());
    }
}

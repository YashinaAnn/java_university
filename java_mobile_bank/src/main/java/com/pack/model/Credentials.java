package com.pack.model;

import com.pack.dto.CredentialsDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credentials implements Serializable {

    @NonNull
    @NotEmpty
    private String login;
    @NonNull
    @NotEmpty
    private String password;

    public Credentials(CredentialsDto credentialsDto) {
        this(credentialsDto.getLogin(), credentialsDto.getPassword());
    }
}

package com.university.accounts.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDto {

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private String phone;

    @NonNull
    private String address;
}
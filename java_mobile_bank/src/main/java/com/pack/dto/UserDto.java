package com.pack.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String login;
    private String password;
    private String phone;
    private String address;
}

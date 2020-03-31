package com.pack.model;

import com.pack.dto.UserDto;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @NonNull
    private long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String address;
    @NonNull
    private String phone;

    public User(String login, String password, String address,  String phone) {
        this(0, login, password, address, phone);
    }

    public User(UserDto userDto) {
        this(userDto.getLogin(), userDto.getPassword(), userDto.getAddress(), userDto.getPhone());
    }

    private static boolean isCorrectPhone(String phone) {
        return (null != phone && !phone.isEmpty())
                && (phone.length() == 10 && phone.matches("[0-9]+"));
    }

    public static void validatePhone(String phone) {
        if (!isCorrectPhone(phone)) {
            throw new IllegalArgumentException("Illegal phone format");
        }
    }
}

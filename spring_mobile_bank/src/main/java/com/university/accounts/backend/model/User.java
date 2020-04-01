package com.university.accounts.backend.model;

import com.university.accounts.backend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> accounts;

    public User(UserDto userDto) {
        this(0, userDto.getLogin(), userDto.getPassword(), userDto.getAddress(), userDto.getPhone(), null);
    }

    public void addAccount(Account account) {
        if (null == accounts) {
            accounts = new ArrayList<>();
            account.setDefault(true);
        }
        accounts.add(account);
    }

    public static boolean isCorrectPhone(String phone) {
        return (null != phone && !phone.isEmpty())
                && (phone.length() == 10 && phone.matches("[0-9]+"));
    }
}

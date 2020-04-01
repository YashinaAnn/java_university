package com.university.accounts.backend.service;

import com.university.accounts.backend.dao.AccountRepository;
import com.university.accounts.backend.dao.UserRepository;
import com.university.accounts.backend.dto.CredentialsDto;
import com.university.accounts.backend.dto.UserDto;
import com.university.accounts.backend.model.Credentials;
import com.university.accounts.backend.model.User;
import com.university.accounts.web.exceptions.BadRequest;
import com.university.accounts.web.exceptions.NotFound;
import com.university.accounts.web.exceptions.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.university.accounts.web.exceptions.ServiceError.ACCOUNT_NOT_FOUND;

@Service
public class UserService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto) {
        if (!User.isCorrectPhone(userDto.getPhone())) {
            throw new BadRequest(ServiceError.INCORRECT_PHONE_FORMAT.message());
        }
        User user = new User(userDto);
        if (userRepository.existsUserByLogin(user.getLogin())) {
                throw new BadRequest(ServiceError.LOGIN_EXISTS.message());
        }
        if (userRepository.existsUserByPhone(user.getPhone())) {
            throw new BadRequest(ServiceError.PHONE_EXISTS.message());
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public String loginUser(CredentialsDto credentialsDto) throws NotFound {
        Credentials credentials = new Credentials(credentialsDto);
        authenticationService.authenticate(credentials.getName(), credentials.getPassword());
        UserDetails userDetails = authenticationService.loadUserByUsername(credentials.getName());
        return authenticationService.generateToken(userDetails);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFound(ACCOUNT_NOT_FOUND.message()));
    }
}

package com.university.accounts.backend.service;

import com.university.accounts.backend.dao.UserRepository;
import com.university.accounts.backend.model.User;
import com.university.accounts.backend.security.JwtToken;
import com.university.accounts.web.exceptions.BadRequest;
import com.university.accounts.web.exceptions.NotAuthorized;
import com.university.accounts.web.exceptions.NotFound;
import com.university.accounts.web.exceptions.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtToken jwtToken;

    public User getAuthenticatedUser() {
        String name = ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
         return userRepository.findByLoginOrPhone(name)
                 .orElseThrow(() -> new NotAuthorized(ServiceError.UNKNOWN_USER.message()));
    }

    public void authenticate(String username, String password) throws BadRequest {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadRequest(ServiceError.USER_DISABLED.message());
        } catch (BadCredentialsException e) {
            throw new BadRequest(ServiceError.INVALID_CREDENTIALS.message());
        }
    }

    public String generateToken(UserDetails userDetails) {
        return jwtToken.generateToken(userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByLoginOrPhone(name)
                    .orElseThrow(() -> new BadRequest(ServiceError.INVALID_CREDENTIALS.message()));
        return new org.springframework.security.core.userdetails.User(name, user.getPassword(),
                new ArrayList<>());
    }
}

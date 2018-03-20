package com.noser.java.shopit.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {this.passwordEncoder = passwordEncoder;}

    public User create(String username, CharSequence password, String... roles) {

        return new User(UUID.randomUUID(),
                        username,
                        passwordEncoder.encode(password),
                        roles);
    }
}

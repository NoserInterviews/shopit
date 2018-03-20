package com.noser.java.shopit.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Repository
public class UserRepository implements UserDetailsService {

    private final UserService userService;
    private final Map<String, User> users = new HashMap<>();

    @Autowired
    public UserRepository(UserService userService) {

        this.userService = userService;
    }

    @PostConstruct
    private void setup() {

        Stream.of(userService.create("user", collect('u', 's', 'e', 'r'), "ROLE_USER"),
                  userService.create("admin", collect('a', 'd', 'm', 'i', 'n'), "ROLE_ADMIN"))
              .forEach(user -> users.put(user.getUsername(), user));
    }

    private CharSequence collect(char... chars) {

        return CharBuffer.wrap(chars);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    public Optional<User> findByUsername(String username) {

        requireNonNull(username);
        return Optional.ofNullable(users.get(username));
    }
}

package com.noser.java.shopit.domain.user;

import com.noser.java.shopit.util.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class User implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password; // encrypted
    private final String[] roles;
    private final Lazy<List<GrantedAuthority>> authorities;

    User(UUID id, String username, String password, String... roles) {

        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.roles = requireNonNull(roles);
        authorities = Lazy.of(() -> Stream.of(roles)
                                          .map(SimpleGrantedAuthority::new)
                                          .collect(toList()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities.get();
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return username;
    }

    public String[] getRoles() {

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}

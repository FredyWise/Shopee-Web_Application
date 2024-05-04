package com.fooddelivery.finalprojectfredy.Service.Authentication;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserRoleAuthentication extends org.springframework.security.core.userdetails.User implements UserDetails {

    private final User user;

    public UserRoleAuthentication(User user) {
        super(user.getUsername(), user.getPassword(),  Collections.emptyList());
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }
}

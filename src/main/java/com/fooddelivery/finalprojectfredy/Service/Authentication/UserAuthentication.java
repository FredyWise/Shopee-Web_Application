package com.fooddelivery.finalprojectfredy.Service.Authentication;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserAuthentication implements UserDetailsService{
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)throws RuntimeException  {
        User user = userService.getUserByUsernameOrEmail(username);
        if (user != null) {
            return new UserRoleAuthentication(user);
        }else{
            throw new RuntimeException("Invalid username or password.");
        }
    }

}


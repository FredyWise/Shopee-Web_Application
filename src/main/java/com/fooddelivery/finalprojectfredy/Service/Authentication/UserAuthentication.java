package com.fooddelivery.finalprojectfredy.Service.Authentication;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IUserService;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserAuthentication implements UserDetailsService{
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException  {
        try {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nCurrent user: " + username);
            User user = userService.getUserByUsernameOrEmail(username);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nCurrent user: " + user);
            if (user!= null) {
                return new UserRoleAuthentication(user);
            } else {
                throw new RuntimeException("Invalid username or password.");
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error while fetching user from database.", e);
        }
    }

}


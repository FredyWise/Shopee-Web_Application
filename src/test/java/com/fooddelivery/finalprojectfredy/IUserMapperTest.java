package com.fooddelivery.finalprojectfredy;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Data.JDBCMappers.IUserMapper;
import com.fooddelivery.finalprojectfredy.Service.Main.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
public class IUserMapperTest {

    @Mock
    private IUserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserByEmail() throws ExecutionException, InterruptedException {
        String email = "test@example.com";
        User expectedUser = new User(); // Set the expected user details

        when(userMapper.getUserByEmail(eq(email))).thenReturn(expectedUser);

        User result = userService.getUserByUsernameOrEmail(email);

        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserByUsername() throws ExecutionException, InterruptedException {
        String username = "testUser";
        User expectedUser = new User(); // Set the expected user details

        when(userMapper.getUserByUsername(eq(username))).thenReturn(expectedUser);

        User result = userService.getUserByUsernameOrEmail(username);

        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserById() throws ExecutionException, InterruptedException {
        String userId = "1";
        User expectedUser = new User(); // Set the expected user details

        when(userMapper.getUserById(eq(userId))).thenReturn(expectedUser);

        User result = userService.getUserById(userId);

        assertEquals(expectedUser, result);
    }

    @Test
    void testInsertUser() throws InterruptedException, ExecutionException {
        User userToInsert = new User(); // Set the user details to insert

        userService.insertUser(userToInsert);

        verify(userMapper, times(1)).insertUser(eq(userToInsert));
    }

    @Test
    void testUpdateUser() {
        User userToUpdate = new User(); // Set the updated user details

//        userService.updateUserProfile(userToUpdate);

        verify(userMapper, times(1)).updateUser(eq(userToUpdate));
    }

    @Test
    void testDeleteUser() {
        String userIdToDelete = "1";

        userService.deleteUser(userIdToDelete);

        verify(userMapper, times(1)).deleteUser(eq(userIdToDelete));
    }
}

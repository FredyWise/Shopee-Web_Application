package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import com.fooddelivery.finalprojectfredy.Data.Entity.Order;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IUserService {
    User getUserById(String userId) throws ExecutionException, InterruptedException;
    User getUserByUsernameOrEmail(String usernameOrEmail) throws ExecutionException, InterruptedException;
    void insertUser(User user) throws InterruptedException, ExecutionException;
    void updateUserPassword(User user);
    void updateUserProfile(User user, HttpSession session);
    void deleteUser(String userId);

    List<Cart> getCartItemsByUserId(String userId) throws ExecutionException, InterruptedException;
    List<Cart> getUserItemsByItemName(String userId, String itemName) throws ExecutionException, InterruptedException;
    void addCartItem(String itemId, String userId) throws ExecutionException, InterruptedException;
    void deleteCartItem(String cartId);

    List<Order> getOrderHistory(String userId) throws ExecutionException, InterruptedException;
    void addOrder(List<Cart> carts) throws InterruptedException, ExecutionException;
}

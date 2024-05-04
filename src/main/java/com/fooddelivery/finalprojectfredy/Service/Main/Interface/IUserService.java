package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import com.fooddelivery.finalprojectfredy.Data.Entity.Order;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IUserService {
    User getUserById(int userId);
    User getUserByUsernameOrEmail(String usernameOrEmail);
    void insertUser(User user);
    void updateUserPassword(User user);
    void updateUserProfile(User user, HttpSession session);
    void deleteUser(int userId);

    List<Cart> getCartItemsByUserId(int userId);
    List<Cart> getUserItemsByItemName(int userId, String itemName);
    void addCartItem(int itemId, int userId);
    void deleteCartItem(int cartId);
    List<Order> getOrderHistory(int userId);

    void addOrder(List<Cart> carts);
}

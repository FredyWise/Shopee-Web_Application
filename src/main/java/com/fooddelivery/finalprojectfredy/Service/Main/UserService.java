package com.fooddelivery.finalprojectfredy.Service.Main;

import com.fooddelivery.finalprojectfredy.Config.SecurityConfig;
import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import com.fooddelivery.finalprojectfredy.Data.Entity.Order;
import com.fooddelivery.finalprojectfredy.Data.Entity.OrderItem;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Data.Enum.Role;
import com.fooddelivery.finalprojectfredy.Data.Enum.Status;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreCartRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreOrderItemRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreOrderRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreUserRepository;
// import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreItemRepository;
import com.fooddelivery.finalprojectfredy.Data.JDBCMappers.*;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IUserService;
import com.fooddelivery.finalprojectfredy.utils.Calculation;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.fooddelivery.finalprojectfredy.Service.ImageService.saveImage;

@Service
public class UserService implements IUserService {

    @Autowired
    private FirestoreUserRepository userMapper;
    @Autowired
    private FirestoreCartRepository cartMapper;
    @Autowired
    private FirestoreOrderRepository orderMapper;
    @Autowired
    private FirestoreOrderItemRepository orderItemMapper;

    //USER
    @Override
    public User getUserById(String userId) throws ExecutionException, InterruptedException {
        return this.userMapper.getUserById(userId);
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) throws ExecutionException, InterruptedException {
        User user = userMapper.getUserByUsername(usernameOrEmail);
        System.out.println("Current user: " + user);
        if (user == null) {
            user = userMapper.getUserByEmail(usernameOrEmail);
            System.out.println("Current user: " + user);
        }
        return user;
    }

    @Override
    public void insertUser(User user) throws InterruptedException, ExecutionException {
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRole(Role.User);
        System.out.println(user);
        userMapper.insertUser(user);
    }

    @Override
    public void updateUserPassword(User user) {
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        System.out.println(user);
        userMapper.updateUser(user);
    }

    @SneakyThrows
    @Override
    public void updateUserProfile(User user, HttpSession session) {
        if (!user.getTempFile().isEmpty()) {
            String imageName = saveImage(user.getTempFile());
            user.setImage(imageName);
        }
        User user1 = (User) session.getAttribute("user");
        user.setUserId(user1.getUserId());
        user.setPassword(user1.getPassword());
        user.setRole(user1.getRole());
        System.out.println(user);
        session.setAttribute("user", user);
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        System.out.println("delete: "+userId);
        userMapper.deleteUser(userId);
    }


    //CART
    @Override
    public List<Cart> getCartItemsByUserId(String userId) throws ExecutionException, InterruptedException {
        List<Cart> carts = cartMapper.getCartItemsByUserId(userId);
        System.out.println(carts);
        return carts;
    }

    @Override
    public List<Cart> getUserItemsByItemName(String userId, String itemName) throws ExecutionException, InterruptedException {
        List<Cart> carts = cartMapper.getUserCartItemsByItemName(userId,itemName);
        System.out.println(carts);
        return carts;
    }


    @Override
    public void addCartItem(String itemId, String userId) throws ExecutionException, InterruptedException {
        boolean cartFound = false;
        List<Cart> carts = cartMapper.getCartItemsByUserId(userId);
        System.out.println(carts);
        for (Cart cart : carts) {
            if (cart.getItemId() == itemId) {
                cart.setQuantity(cart.getQuantity() + 1);
                cartMapper.updateCartItem(cart);
                cartFound = true;
            }
        }
        if (!cartFound) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setItemId(itemId);
            cart.setQuantity(1);
            cartMapper.insertCartItem(cart);
        }
    }

    @Override
    public void deleteCartItem(String cartId) {
        cartMapper.deleteCartItem(cartId);
    }

    //CHECKOUT
    @Override
    public List<Order> getOrderHistory(String userId) throws ExecutionException, InterruptedException {
        List<Order> orders = orderMapper.getOrdersByUserId(userId);
        System.out.println(orders);
        return orders;
    }


    @Override
    public void addOrder(List<Cart> carts) throws InterruptedException, ExecutionException {
        LocalDate today = LocalDate.now();
        Order order = new Order();
        order.setOrderDate(today);
        order.setUserId(carts.get(0).getUserId());
        order.setStatus(Status.Pending);
        order.setTotalPrice(Double.parseDouble(Calculation.getTotalPriceOnCart(carts)));
        orderMapper.insertOrder(order);
        String orderId = orderMapper.getOrderByAll(order);
        for (Cart cart : carts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setItemId(cart.getItemId());
            System.out.println(orderItem);
            orderItemMapper.insertOrderItem(orderItem);
            cartMapper.deleteCartItem(cart.getCartId());
        }
    }
}

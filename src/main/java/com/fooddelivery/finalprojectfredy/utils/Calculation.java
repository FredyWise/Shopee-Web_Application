package com.fooddelivery.finalprojectfredy.utils;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class Calculation {
    public static int getTotalItemOnCart(List<Cart> carts){
        return carts.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }

    public static String getTotalPriceOnCart(List<Cart> carts) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double totalPrice = carts.stream()
                .mapToDouble(cart -> cart.getItem().getPrice() * cart.getQuantity())
                .sum();
        return decimalFormat.format(totalPrice);
    }
}

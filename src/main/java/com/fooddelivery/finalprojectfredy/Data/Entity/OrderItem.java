package com.fooddelivery.finalprojectfredy.Data.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private String orderItemId;
    private String orderId;
    private String itemId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "itemId",referencedColumnName = "item_id")
    Item item;
}


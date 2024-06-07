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
public class Cart {
    private String cartId;
    private String userId;
    private String itemId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "itemId", referencedColumnName = "item_id")
    private Item item;

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }
}


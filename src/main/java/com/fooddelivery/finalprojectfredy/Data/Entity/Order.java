package com.fooddelivery.finalprojectfredy.Data.Entity;

import com.fooddelivery.finalprojectfredy.Data.Enum.Status;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private LocalDate orderDate;
    private double totalPrice;
    private Status status;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;
}

package com.fooddelivery.finalprojectfredy.Data.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String categoryId;
    private String businessId;
    private String image;

    private MultipartFile tempFile;

    @ManyToOne
    @JoinColumn(name = "categoryId",referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "business_id")
    private Business business;
}

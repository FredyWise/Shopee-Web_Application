package com.fooddelivery.finalprojectfredy.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    private int businessId;
    private String fakeId;
    private String name;
    private int ownerId;
    private String phoneNumber;
    private double rating;
    private String image;

    private MultipartFile tempFile;

    @OneToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User owner;

    @OneToMany(mappedBy = "business")
    private BusinessAddress businessAddress;

    @OneToMany(mappedBy = "business")
    private List<Item> items;
}



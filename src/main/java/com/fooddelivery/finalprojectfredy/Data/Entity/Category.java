package com.fooddelivery.finalprojectfredy.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int categoryId;
    private String fakeId;
    private String name;
}


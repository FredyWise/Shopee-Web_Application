package com.fooddelivery.finalprojectfredy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String title;
    private String description;
    private String img;
}


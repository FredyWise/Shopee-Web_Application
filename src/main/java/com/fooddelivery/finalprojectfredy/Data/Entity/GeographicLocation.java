package com.fooddelivery.finalprojectfredy.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GeographicLocation {
    private String geographicId;
    private String city;
    private String state;
    private String country;

    @Override
    public String toString() {
        return city + ", " + state + ", " + country;
    }
}

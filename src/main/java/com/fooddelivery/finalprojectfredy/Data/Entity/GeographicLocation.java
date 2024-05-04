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
    private int geographicId;
    private String fakeId;
    private String city;
    private String state;
    private String country;

    @Override
    public String toString() {
        return city + ", " + state + ", " + country;
    }
}

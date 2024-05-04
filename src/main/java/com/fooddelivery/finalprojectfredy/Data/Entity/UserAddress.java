package com.fooddelivery.finalprojectfredy.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    private int userAddressId;
    private String fakeId;
    private int userId;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private int geographicId;
}

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
    private String userAddressId;
    private String fakeId;
    private String userId;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private String geographicId;
}

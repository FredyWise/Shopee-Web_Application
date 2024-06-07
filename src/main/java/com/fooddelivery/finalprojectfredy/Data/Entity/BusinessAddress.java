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
public class BusinessAddress {
    private String businessAddressId;
    private String addressLine;
    private String geographicId;
    private String businessId;

    @ManyToOne
    @JoinColumn(name = "geographicId", referencedColumnName = "geographic_id")
    private GeographicLocation geographicLocation;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "business_id")
    private Business business;

    @Override
    public String toString() {
        return addressLine + ", " + geographicLocation.toString();
    }
}

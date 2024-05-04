package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import com.fooddelivery.finalprojectfredy.Data.Entity.GeographicLocation;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;

import java.util.List;

public interface IBusinessService {
    Business getBusinessById(int businessId);
    Business getBusinessByOwnerId(int ownerId);
    List<Business> getBusinessByName(String name);
    Business searchBusinessItemByName(int BusinessId, String itemName);
    List<Business> getAllBusinesses();
    List<GeographicLocation> getAllGeographicLocation();//change later
    void insertBusiness(Business business, User tempUser);
    void updateBusiness(Business business);
    void deleteBusiness(int businessId);
}

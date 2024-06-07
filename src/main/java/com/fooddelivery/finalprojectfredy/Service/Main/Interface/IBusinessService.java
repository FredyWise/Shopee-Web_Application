package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import com.fooddelivery.finalprojectfredy.Data.Entity.GeographicLocation;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IBusinessService {
    Business getBusinessById(String businessId) throws ExecutionException, InterruptedException;
    Business getBusinessByOwnerId(String ownerId) throws ExecutionException, InterruptedException;
    List<Business> getBusinessByName(String name) throws ExecutionException, InterruptedException;
    Business searchBusinessItemByName(String BusinessId, String itemName) throws ExecutionException, InterruptedException;
    List<Business> getAllBusinesses() throws ExecutionException, InterruptedException;
    void insertBusiness(Business business, User tempUser) throws ExecutionException, InterruptedException;
    void updateBusiness(Business business);
    void deleteBusiness(String businessId);

    List<GeographicLocation> getAllGeographicLocation() throws ExecutionException, InterruptedException;//change later
}

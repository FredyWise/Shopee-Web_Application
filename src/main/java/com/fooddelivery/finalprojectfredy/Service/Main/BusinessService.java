package com.fooddelivery.finalprojectfredy.Service.Main;

import com.fooddelivery.finalprojectfredy.Data.Entity.*;
import com.fooddelivery.finalprojectfredy.Data.Enum.Role;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreBusinessAddressRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreBusinessRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreGeographicLocationRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreItemRepository;
import com.fooddelivery.finalprojectfredy.Data.FirestoreMapper.FirestoreUserRepository;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IBusinessService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.fooddelivery.finalprojectfredy.Service.ImageService.saveImage;

@Service
public class BusinessService implements IBusinessService {
    @Autowired
    private FirestoreBusinessRepository businessMapper;
    @Autowired
    private FirestoreBusinessAddressRepository addressMapper;
    @Autowired
    private FirestoreGeographicLocationRepository geographicLocationMapper;
    @Autowired
    private FirestoreItemRepository itemMapper;
    @Autowired
    private FirestoreUserRepository userMapper;
    //BUSINESS
    @Override
    public Business getBusinessById(String businessId) throws ExecutionException, InterruptedException {
        Business business = businessMapper.getBusinessById(businessId);
        System.out.println(business);
        return business;
    }

    @Override
    public List<Business> getBusinessByName(String name) throws ExecutionException, InterruptedException {
        List<Business> businesses = businessMapper.getBusinessByName(name);
        System.out.println(businesses);
        return businesses;
    }

    @Override
    public Business searchBusinessItemByName(String businessId, String itemName) throws ExecutionException, InterruptedException {
        Business business = businessMapper.getBusinessById(businessId);
        List<Item> items = itemMapper.getBusinessItemsByName(business.getBusinessId(), itemName);
        business.setItems(items);
        System.out.println(business);
        return business;
    }

    @Override
    public Business getBusinessByOwnerId(String ownerId) throws ExecutionException, InterruptedException {
        Business business = businessMapper.getBusinessByOwnerId(ownerId);
        List<Item> items = itemMapper.getItemsByBusinessId(business.getBusinessId());
        business.setItems(items);
        return business;
    }

    @Override
    public List<Business> getAllBusinesses() throws ExecutionException, InterruptedException {
        List<Business> businesses = businessMapper.getAllBusinesses();
        System.out.println(businesses);
        return businesses;
    }

    @Override
    public void insertBusiness(Business business, User user) throws ExecutionException, InterruptedException {
        User userTemp = userMapper.getUserByUsername(user.getUsername());
        userTemp.setRole(Role.Owner);
        business.setOwnerId(userTemp.getUserId());
        System.out.println(user+"\n"+business+"\npiblic"+userTemp+"user"+user);
        businessMapper.insertBusiness(business);
        insertUpdateBusinessInformation(business);
        userMapper.updateUser(userTemp);
    }

    @SneakyThrows
    @Override
    public void updateBusiness(Business business) {
        if (!business.getTempFile().isEmpty()) {
            String imageName = saveImage(business.getTempFile());
            business.setImage(imageName);
        }
        System.out.println("update"+business);
        businessMapper.updateBusiness(business);
        insertUpdateBusinessInformation(business);
    }

    @Override
    public void deleteBusiness(String businessId) {
        businessMapper.deleteBusiness(businessId);
    }



    private void insertUpdateBusinessInformation(Business businessTemp) throws ExecutionException, InterruptedException{
        Business business = businessMapper.getBusinessByOwnerId(businessTemp.getOwnerId());
        BusinessAddress businessAddress = businessTemp.getBusinessAddress();
        GeographicLocation geographicLocation = businessAddress.getGeographicLocation();
        System.out.println(business+"\n0"+businessAddress);

        String geographicId = geographicLocationMapper.getGeographicLocationIdByCityStateCountry(
                geographicLocation.getCity(),geographicLocation.getState(),geographicLocation.getCountry());
                
        businessAddress.setGeographicId(geographicId);
        businessAddress.setBusinessId(business.getBusinessId());
        System.out.println(businessAddress+"\n1"+geographicId);

        BusinessAddress temp = addressMapper.getBusinessAddressByBusinessId(business.getBusinessId());

        if(temp==null) {
            System.out.println(businessAddress+"\n2add"+geographicId);
            addressMapper.insertBusinessAddress(businessAddress);
        } else{
            businessAddress.setBusinessAddressId(temp.getBusinessAddressId());
            System.out.println(businessAddress.getBusinessAddressId()+"\n2up"+geographicId);
            addressMapper.updateBusinessAddress(businessAddress);
        }
    }

    //GEOGRAPHIC LOCATION
    @Override
    public List<GeographicLocation> getAllGeographicLocation() throws ExecutionException, InterruptedException {
        return geographicLocationMapper.getAllGeographicLocations();
    }
}


package com.fooddelivery.finalprojectfredy.Data.Mappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IBusinessMapper {
    @Insert("INSERT INTO business (business_id, name, owner_id, phone_number, rating, image)" +
            "VALUES (#{business.businessId}, #{business.name}, #{business.ownerId}, "+
            "#{business.phoneNumber}, #{business.rating}, #{business.image})")
    void insertBusiness(@Param("business") Business business);

    @Update("UPDATE business SET name = #{business.name}, owner_id = #{business.ownerId}, "+
            "phone_number = #{business.phoneNumber}, "+
            "rating = #{business.rating}, image = #{business.image} " +
            "WHERE business_id = #{business.businessId}")
    void updateBusiness(@Param("business") Business business);

    @Delete("DELETE FROM business WHERE business_id = #{businessId}")
    void deleteBusiness(@Param("businessId") int businessId);

    @Select("SELECT * FROM business")
    @Results(id = "businessResultMap", value = {
            @Result(property = "businessId", column = "business_id"),
            @Result(property = "fakeId", column = "fake_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "ownerId", column = "owner_id"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "image", column = "image"),
            // Association mappings
            @Result(property = "owner", column = "owner_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IUserMapper.getUserById")),
            @Result(property = "businessAddress", column = "business_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IBusinessAddressMapper.getBusinessAddressByBusinessId")),
            @Result(property = "items", column = "business_id", many = @Many(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IItemMapper.getItemsByBusinessId"))
    })
    List<Business> getAllBusinesses();
    @Select("SELECT * FROM business WHERE business_id = #{businessId}")
    @ResultMap("businessResultMap")
    Business getBusinessById(@Param("businessId") int businessId);
    @Select("SELECT * FROM business WHERE name LIKE CONCAT('%', #{businessName}, '%') ")
    @ResultMap("businessResultMap")
    List<Business> getBusinessByName(@Param("businessName") String businessName);

    @Select("SELECT * FROM business WHERE owner_id = #{userId}")
    @ResultMap("businessResultMap")
    Business getBusinessByOwnerId(@Param("userId") int userId);
}



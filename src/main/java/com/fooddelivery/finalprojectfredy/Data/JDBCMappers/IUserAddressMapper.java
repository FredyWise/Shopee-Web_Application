package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.UserAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserAddressMapper {

    @Insert("INSERT INTO user_addresses(user_id, postal_code, address_line1, address_line2, geographic_id) VALUES (#{userId}, #{postalCode}, #{addressLine1}, #{addressLine2}, #{geographicId})")
    void insertUserAddress(UserAddress userAddress);

    @Update("UPDATE user_addresses SET user_id = #{userId}, postal_code = #{postalCode}, address_line1 = #{addressLine1}, address_line2 = #{addressLine2}, geographic_id = #{geographicId} WHERE user_address_id = #{userAddressId}")
    void updateUserAddress(UserAddress userAddress);

    @Delete("DELETE FROM user_addresses WHERE user_address_id = #{userAddressId}")
    void deleteUserAddress(int userAddressId);

    @Select("SELECT * FROM User_Addresses WHERE user_address_id = #{userAddressId}")
    @Results(id = "userAddressResultMap", value = {
            @Result(property = "userAddressId", column = "user_address_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "addressLine1", column = "address_line1"),
            @Result(property = "addressLine2", column = "address_line2"),
            @Result(property = "geographicId", column = "geographic_id")
    })
    UserAddress getUserAddressById(@Param("userAddressId") int userAddressId);

    @Select("SELECT * FROM User_Addresses WHERE user_id = #{userId}")
    @ResultMap("userAddressResultMap")
    List<UserAddress> getUserAddressesByUserId(@Param("userId") int userId);
}


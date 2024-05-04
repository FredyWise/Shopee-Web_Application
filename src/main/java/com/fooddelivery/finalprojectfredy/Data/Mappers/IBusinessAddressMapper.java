package com.fooddelivery.finalprojectfredy.Data.Mappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.BusinessAddress;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IBusinessAddressMapper {

    @Insert("INSERT INTO business_addresses(address_line, geographic_id, business_id) " +
            "VALUES (#{address.addressLine}, #{address.geographicId}, #{address.businessId})")
    void insertBusinessAddress(@Param("address")BusinessAddress businessAddress);

    @Update("UPDATE business_addresses SET " +
            "address_line = #{addressLine}, geographic_id = #{geographicId}, business_id = #{businessId} " +
            "WHERE business_address_id = #{businessAddressId}")
    void updateBusinessAddress(BusinessAddress businessAddress);

    @Delete("DELETE FROM business_addresses WHERE business_address_id = #{businessAddressId}")
    void deleteBusinessAddress(int businessAddressId);

    @Select("SELECT * FROM business_addresses WHERE business_id = #{businessId}")
    @Results(id = "businessAddressResultMap", value = {
            @Result(property = "businessAddressId", column = "business_address_id"),
            @Result(property = "addressLine", column = "address_line"),
            @Result(property = "geographicId", column = "geographic_Id"),
            @Result(property = "businessId", column = "business_Id"),
            // Association mappings
            @Result(property = "geographicLocation", column = "geographic_Id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IGeographicLocationMapper.getGeographicLocationById")),
    })
    BusinessAddress getBusinessAddressByBusinessId(int businessId);

    @Select("SELECT * FROM business_addresses WHERE business_id = #{businessId} AND geographic_Id = #{geographicId}")
    @ResultMap("businessAddressResultMap")
    BusinessAddress getBusinessAddressByAddressLine(int businessId, int geographicId);


}

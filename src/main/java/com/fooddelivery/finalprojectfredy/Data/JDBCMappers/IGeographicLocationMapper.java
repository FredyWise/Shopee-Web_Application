package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.GeographicLocation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IGeographicLocationMapper {

    @Insert("INSERT INTO geographic_locations(geographic_id, city, state, country) VALUES (#{geographicId}, #{city}, #{state}, #{country})")
    void insertGeographicLocation(GeographicLocation location);

    @Update("UPDATE geographic_locations SET city = #{city}, state = #{state}, country = #{country} WHERE geographic_id = #{geographicId}")
    void updateGeographicLocation(GeographicLocation location);

    @Delete("DELETE FROM geographic_locations WHERE geographic_id = #{geographicId}")
    void deleteGeographicLocation(int geographicId);

    @Select("SELECT * FROM geographic_locations WHERE geographic_id = #{geographicId}")
    @Result(property = "geographicId", column = "geographic_id")
    GeographicLocation getGeographicLocationById(int geographicId);

    @Select("SELECT geographic_id FROM geographic_locations WHERE city = #{city} AND state = #{state} AND country = #{country}")
    @Result(property = "geographicId", column = "geographic_id")
    String  getGeographicLocationIdByCityStateCountry(@Param("city") String city, @Param("state") String state, @Param("country") String country);

    @Select("SELECT * FROM geographic_locations")
    @Result(property = "geographicId", column = "geographic_id")
    List<GeographicLocation> getAllGeographicLocations();
}

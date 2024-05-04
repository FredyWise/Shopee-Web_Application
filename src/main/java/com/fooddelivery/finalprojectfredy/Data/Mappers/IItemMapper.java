package com.fooddelivery.finalprojectfredy.Data.Mappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IItemMapper {
    @Insert("INSERT INTO items (name, description, price, quantity, category_id, business_id, image) " +
            "VALUES (#{name}, #{description}, #{price}, #{quantity}, #{categoryId}, #{businessId}, #{image})")
    void insertItem(Item item);

    @Update("UPDATE items SET name = #{item.name}, description = #{item.description}, " +
            "price = #{item.price}, quantity = #{item.quantity}, category_id = #{item.categoryId}, image = #{item.image}" +
            "WHERE item_id = #{item.itemId}")
    void updateItem(@Param("item") Item item);

    @Delete("DELETE FROM items WHERE item_id = #{itemId}")
    void deleteItem(@Param("itemId") int itemId);

    @Select("SELECT * FROM items")
    @Results(id = "itemsResultMap", value = {
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "businessId", column = "business_id"),
            @Result(property = "image", column = "image"),
            // Association mappings
            @Result(property = "category", column = "category_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.ICategoryMapper.getCategoryById")),
            @Result(property = "business", column = "business_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IBusinessMapper.getBusinessById"))
    })
    List<Item> getAllItems();

    @Select("SELECT * FROM items WHERE item_id = #{itemId}")
    @ResultMap("itemsResultMap")
    Item getItemById(@Param("itemId") int itemId);

    @Select("SELECT * FROM items WHERE name LIKE CONCAT('%', #{name}, '%')")
    @ResultMap("itemsResultMap")
    List<Item> getItemsByName(@Param("name") String name);

    @Select("SELECT * FROM items WHERE category_id = #{categoryId}")
    @ResultMap("itemsResultMap")//better to use category id?
    List<Item> getItemsByCategory(@Param("category") int categoryId);

    @Select("SELECT * FROM items WHERE type = #{type}")
    @ResultMap("itemsResultMap")
    List<Item> getItemsByType(@Param("type") String type);

    @Select("SELECT * FROM items WHERE business_id = #{businessId}")
    @Results(id = "businessItemsResultMap", value = {
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "price", column = "price"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "businessId", column = "business_id"),
            @Result(property = "image", column = "image"),
            // Association mappings
            @Result(property = "category", column = "category_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.ICategoryMapper.getCategoryById"))
    })
    List<Item> getItemsByBusinessId(@Param("businessId") int businessId);

    @Select("SELECT * FROM items WHERE business_id = #{businessId} AND name LIKE CONCAT('%', #{name}, '%')")
    @ResultMap("businessItemsResultMap")
    List<Item> getBusinessItemsByName(@Param("businessId") int businessId, @Param("name") String name);


}

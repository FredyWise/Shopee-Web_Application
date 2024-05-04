package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Category;
import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IItemService {
    List<Item> getAllItems();

    Item getItemById(@Param("itemId") int itemId);

    List<Item> getItemsByName(@Param("name") String name);

    List<Item> getItemsByType(@Param("type") String type);

    List<Item> getItemsByBusinessId(@Param("businessId") int businessId);

    List<Item> getItemsByCategoryId(@Param("categoryId") int categoryId);
    List<Category> getAllCategory();

    void insertItem(@Param("item") Item item, HttpSession session);

    void updateItem(@Param("item") Item item);

    void deleteItem(@Param("itemId") int itemId);
}

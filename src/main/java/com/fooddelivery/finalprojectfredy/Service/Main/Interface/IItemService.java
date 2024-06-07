package com.fooddelivery.finalprojectfredy.Service.Main.Interface;

import com.fooddelivery.finalprojectfredy.Data.Entity.Category;
import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IItemService {
    List<Item> getAllItems() throws ExecutionException, InterruptedException;

    Item getItemById(@Param("itemId") String itemId) throws ExecutionException, InterruptedException;

    List<Item> getItemsByName(@Param("name") String name) throws ExecutionException, InterruptedException;

    List<Item> getItemsByType(@Param("type") String type) throws ExecutionException, InterruptedException;

    List<Item> getItemsByBusinessId(@Param("businessId") String businessId) throws ExecutionException, InterruptedException;

    List<Item> getItemsByCategoryId(@Param("categoryId") String categoryId) throws ExecutionException, InterruptedException;
    List<Category> getAllCategory() throws ExecutionException, InterruptedException;

    void insertItem(@Param("item") Item item, HttpSession session);

    void updateItem(@Param("item") Item item);

    void deleteItem(@Param("itemId") String itemId);
}

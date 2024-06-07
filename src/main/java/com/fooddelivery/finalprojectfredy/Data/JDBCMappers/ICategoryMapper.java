package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {

    @Insert("INSERT INTO categories(category_id, name) VALUES (#{categoryId}, #{name})")
    void insertCategory(Category category);

    @Update("UPDATE categories SET name = #{name} WHERE category_id = #{categoryId}")
    void updateCategory(Category category);

    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    void deleteCategory(int categoryId);
    @Select("SELECT * FROM categories")
    @Result(property = "categoryId", column = "category_id")
    List<Category> getAllCategories();

    @Select("SELECT * FROM categories WHERE category_id = #{categoryId}")
    @Result(property = "categoryId", column = "category_id")
    Category getCategoryById(int categoryId);

    @Select("SELECT * FROM categories WHERE name = #{name}")
    @Result(property = "categoryId", column = "category_id")
    Category getCategoryByName(String name);

}

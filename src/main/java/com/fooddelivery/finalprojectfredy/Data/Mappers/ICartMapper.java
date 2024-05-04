package com.fooddelivery.finalprojectfredy.Data.Mappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ICartMapper {
    @Insert("INSERT INTO cart (user_id, item_id, quantity) VALUES (#{userId}, #{itemId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    void insertCartItem(Cart cart);

    @Update("UPDATE cart SET quantity = #{quantity} WHERE cart_id = #{cartId}")
    void updateCartItem(Cart cart);

    @Delete("DELETE FROM cart WHERE cart_id = #{cartId}")
    void deleteCartItem(int cartId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND quantity != 0")
    @Results(id = "cartResultMap", value = {
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "quantity", column = "quantity"),
           @Result(property = "item", column = "item_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IItemMapper.getItemById"))
    })
    List<Cart> getCartItemsByUserId(int userId);

    @Select("SELECT c.*" +
            "FROM Cart c JOIN Items i " +
            "ON c.item_id = i.item_id " +
            "WHERE i.name LIKE CONCAT('%', #{name}, '%') AND user_id = #{userId}  AND c.quantity != 0")
    @Results({
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "item", column = "item_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IItemMapper.getItemById"))
    })
    List<Cart> getUserCartItemsByItemName(int userId, String name);


    @Select("SELECT * FROM cart WHERE cart_id = #{cartId}")
    @ResultMap("cartResultMap")
    Cart getCartItem(int cartId);
}

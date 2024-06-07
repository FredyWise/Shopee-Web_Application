package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IOrderItemMapper {

    @Insert("INSERT INTO order_items(order_id, item_id, quantity) " +
            "VALUES (#{orderId}, #{itemId}, #{quantity})")
    void insertOrderItem(OrderItem orderItem);

    @Update("UPDATE order_items SET order_id = #{orderItem.orderId}, item_id = #{orderItem.itemId}, " +
            "quantity = #{orderItem.quantity} " +
            "WHERE order_item_id = #{orderItem.orderItemId}")
    void updateOrderItem(@Param("orderItem") OrderItem orderItem);

    @Delete("DELETE FROM order_items WHERE order_item_id = #{orderItemId}")
    void deleteOrderItem(@Param("orderItemId") int orderItemId);


    @Select("SELECT * FROM Order_Items WHERE order_item_id = #{orderItemId}")
    @Results(id = "orderItemResultMap", value = {
            @Result(property = "orderItemId", column = "order_item_id"),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "itemId", column = "item_id"),
            @Result(property = "quantity", column = "quantity"),
            // Association mappings
            @Result(property = "item", column = "item_id", one = @One(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IItemMapper.getItemById"))
    })
    OrderItem getOrderItemById(@Param("orderItemId") int orderItemId);

    @Select("SELECT * FROM Order_Items WHERE order_id = #{orderId}")
    @ResultMap("orderItemResultMap")
    List<OrderItem> getOrderItemsByOrderId(@Param("orderId") int orderId);
}

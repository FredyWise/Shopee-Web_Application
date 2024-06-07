package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IOrderMapper {

    @Insert("INSERT INTO orders(user_id, order_date, total_price, status) " +
            "VALUES (#{order.userId}, #{order.orderDate}, #{order.totalPrice}, #{order.status})")
    void insertOrder(@Param("order") Order order);

    @Update("UPDATE orders SET user_id = #{order.userId}, order_date = #{order.orderDate}, " +
            "total_price = #{order.totalPrice}, status = #{order.status} " +
            "WHERE order_id = #{order.orderId}")
    void updateOrder(@Param("order") Order order);

    @Delete("DELETE FROM orders WHERE order_id = #{orderId}")
    void deleteOrder(@Param("orderId") int orderId);

    @Select("SELECT * FROM Orders")
    @Results(id = "orderResultMap", value = {
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "orderDate", column = "order_date"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            // Association mappings
            @Result(property = "orderItems", column = "order_id", many = @Many(select = "com.fooddelivery.finalprojectfredy.Data.Mappers.IOrderItemMapper.getOrderItemsByOrderId"))
    })
    List<Order> getAllOrders();

    @Select("SELECT * FROM Orders WHERE order_id = #{orderId}")
    @ResultMap("orderResultMap")
    Order getOrderById(@Param("orderId") int orderId);

    @Select("SELECT * FROM Orders WHERE user_id = #{userId}")
    @ResultMap("orderResultMap")
    List<Order> getOrdersByUserId(@Param("userId") String userId);
    @Select("SELECT order_id FROM orders WHERE user_id = #{order.userId} AND order_date = #{order.orderDate} AND " +
            "total_price = #{order.totalPrice} AND status = #{order.status} ")
    @Result(property = "orderId", column = "order_id")
    int getOrderByAll(@Param("order") Order order);

}

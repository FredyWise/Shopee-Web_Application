package com.fooddelivery.finalprojectfredy.Data.JDBCMappers;

import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUserMapper {

    @Insert("INSERT INTO users (user_id, username, email, password, phone_number, role, birth_date, gender) " +
            "VALUES (#{user.userId}, #{user.username}, #{user.email}, #{user.password}, #{user.phoneNumber}, " +
            "#{user.role}, #{user.birthDate}, #{user.gender})")
    void insertUser(@Param("user") User user);

    @Update("UPDATE users SET username = #{user.username}, email = #{user.email}, password = #{user.password}, " +
            "phone_number = #{user.phoneNumber}, role = #{user.role}, birth_date = #{user.birthDate}," +
            "gender = #{user.gender}, image = #{user.image} WHERE user_id = #{user.userId}")
    void updateUser(@Param("user") User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void deleteUser(@Param("userId") String userId);

    @Select("SELECT * FROM Users WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fakeId", column = "fake_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role"),
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "image", column = "image"),
//            @Result(property = "userAddress", column = "user_id", one = @One(select = "com.example.UserAddressMapper.getUserAddressesByUserId")),
//            @Result(property = "carts", column = "user_id", many = @Many(select = "com.example.CartMapper.getCartsByUserId")),
//            @Result(property = "orders", column = "user_id", many = @Many(select = "com.example.OrderMapper.getOrdersByUserId"))
    })
    User getUserById(@Param("userId") String userId);
    @Select("SELECT * FROM Users WHERE email = #{email}")
    @Results(id = "userResultMap", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fakeId", column = "fake_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role"),
            @Result(property = "birthDate", column = "birth_date"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "image", column = "image"), })
    User getUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM Users WHERE username = #{username}")
    @ResultMap("userResultMap")
    User getUserByUsername(@Param("username") String username);
}

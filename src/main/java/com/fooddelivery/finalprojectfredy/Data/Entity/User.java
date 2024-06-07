package com.fooddelivery.finalprojectfredy.Data.Entity;

import com.fooddelivery.finalprojectfredy.Data.Enum.Role;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;


@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
    private Date birthDate;
    private String gender;
    private String image;

    private MultipartFile tempFile;

    // @OneToMany(mappedBy = "user")
    // private UserAddress userAddress;

    // @OneToMany(mappedBy = "user")
    // private List<Cart> carts;

    // @OneToMany(mappedBy = "user")
    // private List<Order> orders;
}

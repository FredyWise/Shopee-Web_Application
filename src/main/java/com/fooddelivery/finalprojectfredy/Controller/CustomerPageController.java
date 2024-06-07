package com.fooddelivery.finalprojectfredy.Controller;

import com.fooddelivery.finalprojectfredy.Data.Entity.*;
import com.fooddelivery.finalprojectfredy.Service.ImageService;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IBusinessService;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IItemService;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IUserService;
import com.fooddelivery.finalprojectfredy.utils.Image;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.fooddelivery.finalprojectfredy.utils.Calculation.getTotalItemOnCart;
import static com.fooddelivery.finalprojectfredy.utils.Calculation.getTotalPriceOnCart;

@Controller
@RestController
@RequestMapping("/user")
public class CustomerPageController {
    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IItemService itemService;


    @RequestMapping("/")
    public String homePage(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        List<Image> imageList = ImageService.getImageList();
        User user = (User) session.getAttribute("user");
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("imageList", imageList);
        model.addAttribute("user",user);
        model.addAttribute("items", itemList);
        return "userPage/home";
    }

    @GetMapping("/searchItem")
    public String searchItem(@RequestParam("search") String searchQuery, Model model, HttpSession session) throws ExecutionException, InterruptedException{
        List<Image> imageList = ImageService.getImageList();
        User user = (User) session.getAttribute("user");
        List<Item> itemList = itemService.getItemsByName(searchQuery);
        model.addAttribute("imageList", imageList);
        model.addAttribute("user",user);
        model.addAttribute("items", itemList);
        return "userPage/home";
    }

    @RequestMapping("/shop")
    public String storeList(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("businessList",businessService.getAllBusinesses());
        return "userPage/storeList";
    }

    @GetMapping("/shop/{id}")
    public String getStoreDetails(@PathVariable("id") String businessId, Model model) throws ExecutionException, InterruptedException {
        Business business = businessService.getBusinessById(businessId);
        model.addAttribute("business", business);
        return "userPage/storeItem";
    }

    @GetMapping ("/searchBusiness")
    public String searchBusiness(@RequestParam("search") String searchQuery, Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("businessList", businessService.getBusinessByName(searchQuery));
        return "userPage/storeList";
    }

    @GetMapping ("/searchItems/{id}")
    public String searchItems(@PathVariable("id") String businessId,@RequestParam("search") String searchQuery, Model model, HttpSession session) throws ExecutionException, InterruptedException {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("business", businessService.searchBusinessItemByName(businessId, searchQuery));
        return "userPage/storeItem";
    }

    @RequestMapping("/cart")
    public String cartList(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Cart> carts = userService.getCartItemsByUserId(user.getUserId());
        model.addAttribute("carts",carts);
        model.addAttribute("totalItem",getTotalItemOnCart(carts));
        model.addAttribute("totalAmount",getTotalPriceOnCart(carts));
        return "userPage/cart";
    }

    @GetMapping ("/searchCart")
    public String searchCart(@RequestParam("search") String searchQuery, Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Cart> carts = userService.getUserItemsByItemName(user.getUserId(), searchQuery);
        model.addAttribute("carts", carts);
        model.addAttribute("totalItem",getTotalItemOnCart(carts));
        model.addAttribute("totalAmount",getTotalPriceOnCart(carts));
        return "userPage/cart";
    }

    @GetMapping("/addCart/{id}")
    public String addCartItem(@PathVariable("id") String itemId, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        userService.addCartItem(itemId, user.getUserId());
        return "redirect:/user/cart";
    }

    @GetMapping("/delCart/{id}")
    public String delCartItem(@PathVariable("id") String cartId) {
        userService.deleteCartItem(cartId);
        return "redirect:/user/cart";
    }

    @RequestMapping("/checkout")
    public String checkOut(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Cart> carts = userService.getCartItemsByUserId(user.getUserId());
        model.addAttribute("carts",carts);
        model.addAttribute("totalItem",getTotalItemOnCart(carts));
        model.addAttribute("totalAmount",getTotalPriceOnCart(carts));
        return "userPage/checkout";
    }

    @PostMapping("/checkout")
    public String checkOutSuccess(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Cart> carts = userService.getCartItemsByUserId(user.getUserId());
        userService.addOrder(carts);
        return "redirect:/user/";
    }

    @RequestMapping("/history")
    public String history(Model model, HttpSession session) throws ExecutionException, InterruptedException{
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        List<Order> orders = userService.getOrderHistory(user.getUserId());
        model.addAttribute("orders", orders);
        return "userPage/history";
    }

    @RequestMapping("/contact")
    public String contact(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "userPage/contact";
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "userPage/changeProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfileSuccess(@ModelAttribute("user") User user, HttpSession session, Model model){
        try {
            System.out.println(user);
            userService.updateUserProfile(user, session);
            return "userPage/contact";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user",user);
            return "userPage/changeProfile";
        }
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(){
        return "userPage/changePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePasswordSuccess(String newPassword,HttpSession session,Model model){
        try {
            User user = (User) session.getAttribute("user");
            user.setPassword(newPassword);
            userService.updateUserPassword(user);
            return "userPage/contact";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "userPage/changePassword";
        }
    }

    @GetMapping("/delUser")
    public String delUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.deleteUser(user.getUserId());
        return "redirect:/logout";
    }
}


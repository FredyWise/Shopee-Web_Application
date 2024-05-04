package com.fooddelivery.finalprojectfredy.Controller;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IBusinessService;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private User tempUser;

    @Autowired
    private IUserService userService;
    @Autowired
    private IBusinessService businessService;

    @RequestMapping("/login")
    public String showLoginForm() {
        return "/auth/login";
    }


    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "/auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model,
                               @RequestParam(value = "owner", required = false) String owner) {
        try {
            userService.insertUser(user);
            if(owner!=null){
                tempUser = user;
                return "redirect:/auth/register/owner";
            }else {
                return "redirect:/auth/login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/auth/register";
        }
    }

    @RequestMapping("/register/owner")
    public String showOwnerRegistrationForm(Model model) {
        model.addAttribute("business", new Business());
        model.addAttribute("geographicLocation",businessService.getAllGeographicLocation());
        return "/auth/registerOwner";
    }

    @PostMapping("/register/owner")
    public String registerOwnerBusiness(@ModelAttribute("business") Business business, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user!=null){
                tempUser = user;
            }
            businessService.insertBusiness(business, tempUser);
            tempUser = null;
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("geographicLocation",businessService.getAllGeographicLocation());
            return "/auth/registerOwner";
        }
    }


    @RequestMapping("/authentication")
    public String check(Authentication authentication, Principal principal, HttpSession session, Model model) {
        try {
            User user = userService.getUserByUsernameOrEmail(principal.getName());
            session.setAttribute("user", user);

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority("Owner"))) {
                return "redirect:/owner/";
            } else {
                return "redirect:/user/";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        }
    }
}


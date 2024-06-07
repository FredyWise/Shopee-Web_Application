package com.fooddelivery.finalprojectfredy.Controller;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IBusinessService;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IItemService;
import jakarta.servlet.http.HttpSession;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/owner")
public class OwnerPageController {
    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IItemService itemService;

    @RequestMapping("/")
    public String itemList(HttpSession session, Model model) throws ExecutionException, InterruptedException {
        User user = (User) session.getAttribute("user");
        model.addAttribute("business", businessService.getBusinessByOwnerId(user.getUserId()));
        return "ownerPage/home";
    }

    @GetMapping ("/search")
    public String searchItems(@RequestParam("search") String searchQuery, Model model, HttpSession session) throws ExecutionException, InterruptedException {
        User user = (User) session.getAttribute("user");
        Business business = businessService.searchBusinessItemByName(businessService.getBusinessByOwnerId(user.getUserId()).getBusinessId(), searchQuery);
        model.addAttribute("business", business);
        return "ownerPage/home";
    }

    @GetMapping("/add/")
    public String addItem(Model model) throws ExecutionException, InterruptedException {
        Item item = new Item();
        model.addAttribute("item", item);
        model.addAttribute("categories", itemService.getAllCategory());
        return "ownerPage/add";
    }

    @PostMapping("/add/")
    public String addSuccess(@ModelAttribute("item") Item item, Model model, HttpSession session) throws ExecutionException, InterruptedException{
        try{
            itemService.insertItem(item, session);
            return "redirect:/owner/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("item", item);
            model.addAttribute("categories", itemService.getAllCategory());
            return "ownerPage/add";
        }
    }

    @GetMapping("/del/{id}")
    public String delItem(@PathVariable("id") String itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/owner/";
    }

    @GetMapping("/update/{id}")
    public String updateItem(@PathVariable("id") String itemId, Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("item", itemService.getItemById(itemId));
        model.addAttribute("categories", itemService.getAllCategory());
        return "ownerPage/update";
    }

    @PostMapping("/update/")
    public String updateSuccess(@ModelAttribute("item") Item item,Model model) throws ExecutionException, InterruptedException{
        try {
            itemService.updateItem(item);
            return "redirect:/owner/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("item", item);
            model.addAttribute("categories", itemService.getAllCategory());
            return "ownerPage/update";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, HttpSession session) throws ExecutionException, InterruptedException {
        User user = (User) session.getAttribute("user");
        model.addAttribute("business", businessService.getBusinessByOwnerId(user.getUserId()));
        model.addAttribute("geographicLocation",businessService.getAllGeographicLocation());
        return "ownerPage/editBusiness";
    }

    @PostMapping("/edit")
    public String saveBusiness(@ModelAttribute("business") Business business, Model model) throws ExecutionException, InterruptedException{
        try {
            businessService.updateBusiness(business);
            return "redirect:/owner/";
        } catch (RuntimeException e) {
            model.addAttribute("geographicLocation",businessService.getAllGeographicLocation());
            model.addAttribute("error", e.getMessage());
            return "ownerPage/editBusiness";
        }
    }
}

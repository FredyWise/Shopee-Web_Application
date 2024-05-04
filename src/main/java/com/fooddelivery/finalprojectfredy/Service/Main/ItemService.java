package com.fooddelivery.finalprojectfredy.Service.Main;

import com.fooddelivery.finalprojectfredy.Data.Entity.Business;
import com.fooddelivery.finalprojectfredy.Data.Entity.Category;
import com.fooddelivery.finalprojectfredy.Data.Entity.Item;
import com.fooddelivery.finalprojectfredy.Data.Entity.User;
import com.fooddelivery.finalprojectfredy.Data.Mappers.ICategoryMapper;
import com.fooddelivery.finalprojectfredy.Data.Mappers.IItemMapper;
import com.fooddelivery.finalprojectfredy.Service.Main.Interface.IItemService;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fooddelivery.finalprojectfredy.Service.ImageService.saveImage;

@Service
public class ItemService implements IItemService {

    @Autowired
    private IItemMapper itemMapper;
    @Autowired
    private ICategoryMapper categoryMapper;
    @Autowired
    private BusinessService businessService;

    //ITEM
    @Override
    public List<Item> getAllItems() {
        List<Item> items = itemMapper.getAllItems();
        System.out.println(items);
        return items;
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = itemMapper.getItemById(itemId);
        System.out.println(item);
        return item;
    }

    @Override
    public List<Item> getItemsByName(String name) {
        List<Item> items = itemMapper.getItemsByName(name);
        System.out.println(items);
        return items;
    }

    @Override
    public List<Item> getItemsByType(String type) {
        List<Item> items = itemMapper.getItemsByType(type);
        System.out.println(items);
        return items;
    }

    @Override
    public List<Item> getItemsByBusinessId(int businessId) {
        List<Item> items = itemMapper.getItemsByBusinessId(businessId);
        System.out.println(items);
        return items;
    }

    @Override
    public List<Item> getItemsByCategoryId(int categoryId) {
        List<Item> items = itemMapper.getItemsByCategory(categoryId);
        System.out.println(items);
        return items;
    }

    @SneakyThrows
    @Override
    public void insertItem(Item item, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Business business = businessService.getBusinessByOwnerId(user.getUserId());
        item.setBusinessId(business.getBusinessId());
        if (!item.getTempFile().isEmpty()) {
            String imageName = saveImage(item.getTempFile());
            item.setImage(imageName);
        }
        System.out.println(item+"in");
        item.setCategoryId(categoryMapper.getCategoryByName(item.getCategory().getName()).getCategoryId());
        System.out.println(item);
        itemMapper.insertItem(item);
    }

    @SneakyThrows
    @Override
    public void updateItem(Item item) {
        if (!item.getTempFile().isEmpty()) {
            String imageName = saveImage(item.getTempFile());
            item.setImage(imageName);
        }
        item.setCategoryId(categoryMapper.getCategoryByName(item.getCategory().getName()).getCategoryId());
        System.out.println(item);
        itemMapper.updateItem(item);
    }

    @Override
    public void deleteItem(int itemId) {
        System.out.println("del"+itemId);
        itemMapper.deleteItem(itemId);
    }


    @Override
    public List<Category> getAllCategory(){
        List<Category> categories= categoryMapper.getAllCategories();
        System.out.println(categories);
        return categories;
    }
}

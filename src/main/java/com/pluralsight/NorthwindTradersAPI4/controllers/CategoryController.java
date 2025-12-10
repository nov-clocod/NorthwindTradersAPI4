package com.pluralsight.NorthwindTradersAPI4.controllers;

import com.pluralsight.NorthwindTradersAPI4.dao.interfaces.ICategoryDAO;
import com.pluralsight.NorthwindTradersAPI4.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private ICategoryDAO categoryDAO;

    @Autowired
    public CategoryController (ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @RequestMapping(path = "/categories/{categoryID}", method = RequestMethod.GET)
    public Category getCategoryByID(@PathVariable int categoryID) {
        return categoryDAO.getCategoryByID(categoryID);
    }

    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDAO.addCategory(category);
    }
}

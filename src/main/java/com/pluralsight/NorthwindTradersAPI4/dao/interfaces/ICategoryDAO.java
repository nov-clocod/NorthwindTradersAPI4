package com.pluralsight.NorthwindTradersAPI4.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI4.models.Category;

import java.util.List;

public interface ICategoryDAO {

    List<Category> getAllCategories();

    Category getCategoryByID(int categoryID);

    Category addCategory(Category category);
}

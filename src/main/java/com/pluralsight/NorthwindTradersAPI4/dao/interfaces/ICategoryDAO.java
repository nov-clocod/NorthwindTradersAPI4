package com.pluralsight.NorthwindTradersAPI4.dao.interfaces;

import com.pluralsight.northwindTradersAPI3.models.Category;

import java.util.List;

public interface ICategoryDAO {

    List<Category> getAllCategories();

    Category getCategoryByID(int categoryID);
}

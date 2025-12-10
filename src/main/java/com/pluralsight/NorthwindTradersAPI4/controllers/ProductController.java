package com.pluralsight.NorthwindTradersAPI4.controllers;

import com.pluralsight.NorthwindTradersAPI4.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI4.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private IProductDAO productDAO;

    @Autowired
    public ProductController (IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @RequestMapping(path = "/products/{productID}", method = RequestMethod.GET)
    public Product getProductByID(@PathVariable int productID) {
        return productDAO.getProductByID(productID);
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productDAO.addProduct(product);
    }
}

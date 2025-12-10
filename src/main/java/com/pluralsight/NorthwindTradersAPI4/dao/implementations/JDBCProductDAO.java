package com.pluralsight.NorthwindTradersAPI4.dao.implementations;

import com.pluralsight.NorthwindTradersAPI4.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI4.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCProductDAO implements IProductDAO {
    private DataSource dataSource;

    @Autowired
    public JDBCProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String getAllQuery = """
                SELECT *
                FROM products
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getAllQuery);
             ResultSet resultSet = selectStatement.executeQuery()) {
            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                int categoryID = resultSet.getInt("CategoryID");
                double productPrice = resultSet.getDouble("UnitPrice");

                products.add(new Product(productID, productName, categoryID, productPrice));
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return products;
    }

    @Override
    public Product getProductByID(int productID) {
        Product product = null;

        String getByIDQuery = """
                SELECT *
                FROM products
                WHERE ProductID = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByIDQuery)) {
            selectStatement.setInt(1, productID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int productIDFromDB = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    int categoryID = resultSet.getInt("CategoryID");
                    double productPrice = resultSet.getDouble("UnitPrice");

                    product = new Product(productIDFromDB, productName, categoryID, productPrice);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return product;
    }

    @Override
    public Product addProduct(Product product) {
        String insertDataQuery = """
                INSERT INTO products (ProductName, CategoryID, UnitPrice)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, product.getProductName());
            insertStatement.setInt(2, product.getCategoryID());
            insertStatement.setDouble(3, product.getUnitPrice());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    product.setProductID(generatedID);
                } else {
                    throw new SQLException("Creating Product failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return product;
    }
}


package com.pluralsight.NorthwindTradersAPI4.dao.implementations;

import com.pluralsight.NorthwindTradersAPI4.dao.interfaces.ICategoryDAO;
import com.pluralsight.NorthwindTradersAPI4.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCCategoryDAO implements ICategoryDAO {
    private DataSource dataSource;

    @Autowired
    public JDBCCategoryDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        String getAllQuery = """
                SELECT *
                FROM categories
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getAllQuery);
             ResultSet resultSet = selectStatement.executeQuery()) {
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");

                categories.add(new Category(categoryID, categoryName));
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return categories;
    }

    @Override
    public Category getCategoryByID(int categoryID) {
        Category category = null;

        String getByIDQuery = """
                SELECT *
                FROM categories
                WHERE CategoryID = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(getByIDQuery)) {
            selectStatement.setInt(1, categoryID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryIDFromDB = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("CategoryName");

                    category = new Category(categoryIDFromDB, categoryName);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return category;
    }

    @Override
    public Category addCategory(Category category) {
        String insertDataQuery = """
                INSERT INTO categories (CategoryName)
                VALUES (?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertDataQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, category.getCategoryName());

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating Product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    category.setCategoryID(generatedID);
                } else {
                    throw new SQLException("Creating Product failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred");
            System.out.println(ex.getErrorCode());
        }
        return category;
    }
}

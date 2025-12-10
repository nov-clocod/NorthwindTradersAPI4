package com.pluralsight.NorthwindTradersAPI4.dao.implementations;

import com.pluralsight.northwindTradersAPI3.dao.interfaces.ICategoryDAO;
import com.pluralsight.northwindTradersAPI3.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}

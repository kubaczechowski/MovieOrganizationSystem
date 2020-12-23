package sample.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.be.Category;
import sample.dal.exception.DALexception;
import sample.dal.interfaces.CategoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB implements CategoryInterface {
    private DBConnector dbConnector = new DBConnector();

    @Override
    public List<Category> getAllCategories() throws DALexception {
        List<Category> allCategories = new ArrayList<>();
        String query = "SELECT * FROM Category;";
        try (Connection con = dbConnector.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name= rs.getString("name");
                Category category = new Category(id, name);
                allCategories.add(category);
            }
        } catch (SQLServerException throwables) {
           throw new DALexception("Couldn't get all categories", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't get all categories", throwables);
        }
        return allCategories;
    }

    @Override
    public void addCategory(Category category) throws DALexception {
        String query = "INSERT INTO Category(name) VALUES(?);";
        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            try(ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if(generatedKey.next())
                {
                    category.setId(generatedKey.getInt(1));
                }
                else{
                    throw new DALexception("Couldn't get generated key");
                }
            }
        } catch (SQLServerException throwables) {
           //throwables.printStackTrace();
            throw new DALexception("Couldn't add new category", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't add new category", throwables);
        }
    }

    @Override
    public void deleteCategory(Category category) throws DALexception {
        String query = "DELETE FROM Category WHERE id=?;";
        try (Connection con = dbConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, category.getId());
            pstat.execute();
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't delete category", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't delete category", throwables);
        }
    }
}

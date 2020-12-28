package sample.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.be.Category;
import sample.dal.exception.DALexception;
import sample.dal.interfaces.CatMovieInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO implements CatMovieInterface {

    private DBConnector databaseConnector;

    public CatMovieDAO() {
        databaseConnector = new DBConnector();
    }

    /**
     * method at first retrives ids of categories from CatMovie table
     * and then gets that categories from Category table based on ids previously
     * retrived
     * @param movieID
     * @return
     */
    @Override
    public List<Category> getCategoriesFromSpecificMovie(int movieID) throws DALexception {
        List<Integer> categoryIds = new ArrayList<>();
        String query1 = "SELECT CategoryId FROM CatMovie WHERE movieId=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query1)) {
            preparedStatement.setInt(1, movieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryId");
                categoryIds.add(categoryID);
            }
        } catch (SQLServerException throwables) {
            // throwables.printStackTrace();
            throw new DALexception("couldnt get categoryID" , throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt get cateoryID", throwables);
        }


        //second part fof the method
        List<Category> categoriesForMovie = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            String query2 = "SELECT * FROM Categories WHERE id=?;";
            for(Integer integer: categoryIds)
            {
                PreparedStatement preparedStatement = con.prepareStatement(query2);
                preparedStatement.setInt(1, integer);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Category category = new Category(id, name);
                    categoriesForMovie.add(category);
                }
            }

        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt get cateogries for movie", throwables);
        } catch (SQLException throwables) {
           // throwables.printStackTrace();
            throw new DALexception("couldnt get cateogries for movie", throwables);
        }
        return categoriesForMovie;
    }

    @Override
    public void addCategoryToMovie(int categoryID, int movieID) throws DALexception {
        String query = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?, ?);";
        try (Connection con = databaseConnector.getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setInt(1,categoryID );
        preparedStatement.setInt(2,movieID );
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt add cateogry to movie", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt add category to movie", throwables);
        }
    }

    @Override
    public void deleteCategoryFromMovie(int categoryID, int movieID) throws DALexception {
        String query = "DELETE FROM CatMovie WHERE(CategoryId=? AND MovieId=?);";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
         pstat.setInt(1, categoryID);
         pstat.setInt(2, movieID);
         pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt delete category from movie", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt delete category from movie", throwables);
        }
    }
}

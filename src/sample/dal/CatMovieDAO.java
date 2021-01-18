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
            String query2 = "SELECT * FROM Category WHERE id=?;";
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
        preparedStatement.executeUpdate();

        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt add cateogry to movie", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("couldnt add category to movie", throwables);
        }
        System.out.println("line was executed");
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

    /**
     * before deleting a movie we need to delete all categories in the CatMovie table
     * other way we get an exception
     * @param movieID
     * @throws DALexception
     */
    @Override
    public void deleteAllCategoriesForMovie(int movieID) throws DALexception {
        String query = "DELETE FROM CatMovie WHERE(MovieId=?);";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, movieID);
            pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throw new DALexception("couldn't delete all cateogies from a movie", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("couldn't delete all cateogies from a movie", throwables);
        }
    }

    @Override
    public boolean categoryIsSetOnAnyMovie(int categoryID) throws DALexception {
        String query = "SELECT COUNT(1) FROM CatMovie WHERE CategoryId=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, categoryID);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if(count==0)
                return false;
            else
                return true;
        } catch (SQLServerException throwables) {
            throw new DALexception("couldnt check if movie has such category", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("couldnt check if movie has such category", throwables);
        }
    }

    @Override
    public boolean checkIfMovieHasSuchCategory(int categoryID, int movieID) throws DALexception {
       // boolean result;
        String query = "SELECT COUNT(1) FROM CatMovie WHERE CategoryId=? AND MovieId=?;";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, categoryID);
            pstat.setInt(2, movieID );
            ResultSet rs = pstat.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if(count==0)
                return false;
            else
                return true;
        } catch (SQLServerException throwables) {
            throw new DALexception("couldnt check if movie has such category", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("couldnt check if movie has such category", throwables);
        }
    }
}

package sample.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.be.Category;
import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.interfaces.MovieInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MovieDB implements MovieInterface {
    private CatMovieDAO catMovieDAO = new CatMovieDAO();
    private DBConnector dbConnector;
    public MovieDB() {
        dbConnector = new DBConnector();
    }


    @Override
    public List<Movie> getAllMovies() throws DALexception {
        //HashMap<Integer, Movie> allMovies = new HashMap<>();
        List<Movie> allMovies = new ArrayList<>();
        String query = "SELECT * FROM Movie;";
        try (Connection con = dbConnector.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                int ratingIMDB = rs.getInt("ratingIMDB");
                Timestamp lastview = rs.getTimestamp("lastview");
                String filelink = rs.getString("filelink");
                List<Category> categories = catMovieDAO.getCategoriesFromSpecificMovie(id);
                Movie movie = new Movie(id, name, rating, ratingIMDB, filelink, lastview,
                        categories);
               // movie.setCategoryList(categories);
                allMovies.add(movie);
                System.out.println("DB:" + movie.getCategoryList());
            }
            return allMovies;
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't get all movies", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't get all movies", throwables);
        }
    }

    @Override
    public void addMovie(Movie movie) throws DALexception {
        String query = " INSERT INTO MOVIE( name, rating, ratingIMDB, filelink, lastview) " +
                "VALUES (?, ?, ? ,?, ?);";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2 , movie.getRating());
            preparedStatement.setInt(3, movie.getRatingIMDB());
            preparedStatement.setString(4, movie.getFilelink());
            preparedStatement.setTimestamp(5, movie.getLastview());
            preparedStatement.executeUpdate();

            //set proper id for that movie
            try(ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                if(generatedKey.next())
                {
                    movie.setId(generatedKey.getInt(1));
                }
                else{
                    throw new DALexception("Couldn't get generated key");
                }
            }
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't add new movie", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't add new movie", throwables);
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws DALexception {
        String query = "DELETE FROM Movie WHERE id=?;";
        try (Connection con = dbConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, movie.getId());
            pstat.execute();
        } catch (SQLServerException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't delete movie", throwables);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            throw new DALexception("Couldn't delete movie", throwables);
        }
    }

    /**
     *
     * @return
     * @throws DALexception
     */
    @Override
    public List<Movie> getMoviesToDelete() throws DALexception {
        //list of movies to return
        List<Movie> moviesToDelete = new ArrayList<>();
        //get time: now - two years
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -365 * 2);
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        long twoYearsAgo = cal.getTimeInMillis();
        //query
        String query = "SELECT * FROM Movie WHERE rating<6 AND lastview<=?;";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setTimestamp(1, time);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                int ratingIMDB = rs.getInt("ratingIMDB");
                Timestamp lastview = rs.getTimestamp("lastview");
                String filelink = rs.getString("filelink");
                List<Category> categories = catMovieDAO.getCategoriesFromSpecificMovie(id);
                Movie movie = new Movie(id, name, rating, ratingIMDB, filelink, lastview,
                        categories);
                // movie.setCategoryList(categories);
                moviesToDelete.add(movie);
            }

        } catch (SQLServerException throwables) {
            // throwables.printStackTrace();
            throw new DALexception("count get movies to delete", throwables);
        } catch (SQLException throwables) {
            // throwables.printStackTrace();
            throw new DALexception("count get movies to delete", throwables);
        }
        return moviesToDelete;
    }

    /**
     * Method is called when the user opens a movie. Then the date of the last
     * time they saw the movie is updated.
     * @param movie
     * @throws DALexception
     */
    @Override
    public void updateLastViewFor(Movie movie) throws DALexception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        // format 2016-11-16 06:43:19.77
        String update = "UPDATE Movie SET lastview=? WHERE id=?;";
        try (Connection con = dbConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(update)) {
        pstat.setTimestamp(1, currentTime);
        pstat.setInt(2, movie.getId());
        pstat.executeUpdate();
        } catch (SQLServerException throwables) {
            throw new DALexception("count update lastview", throwables);
        } catch (SQLException throwables) {
            throw new DALexception("count update lastview", throwables);
        }

    }


}

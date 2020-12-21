package sample.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.be.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieDB implements MovieInterface{

    private DBConnector dbConnector;
    private HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>();

    public MovieDB() {
        dbConnector = new DBConnector();
    }


    @Override
    public List<Movie> getAllMovies() {
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
                Movie movie = new Movie(id, name, rating, ratingIMDB, filelink, lastview);
                //allMovies.put(id, movie);
                allMovies.add(movie);
            }
            return allMovies;
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            System.out.println("bla");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("bla");

        }
        return null;
    }

    @Override
    public void addMovie(Movie movie) {
        String query = " INSERT INTO MOVIE( name, rating, ratingIMDB, filelink, lastview) " +
                "VALUES (?, ?, ? ,?, ?);";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
                    //throw custom exception
                }
            }

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteMovie(Movie movie) {
        String query = "DELETE FROM Movie WHERE id=?;";
        try (Connection con = dbConnector.getConnection();
             PreparedStatement pstat = con.prepareStatement(query)) {
            pstat.setInt(1, movie.getId());
            pstat.execute();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

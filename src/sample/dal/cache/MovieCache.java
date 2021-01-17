package sample.dal.cache;

import sample.be.Movie;
import java.util.List;


public class MovieCache {
    private List<Movie> movies;
    private static MovieCache movieCache;

    public static MovieCache getInstance() {
        if(movieCache==null){
            movieCache = new MovieCache();
        }
        return movieCache;
    }

    public void saveMovies(Movie... movies) {
        for (Movie movie : movies) {
            this.movies.removeIf(m -> m.getId() == movie.getId());
            this.movies.add(movie);
        }
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.movies = allMovies;
    }


    public void removeMovie(Movie... movies) {
        for(Movie movie: movies)
            this.movies.removeIf(m -> m.getId() == movie.getId());
    }

    public void removeListOfMovies(List<Movie> moviesToDel){
        for(Movie movie: moviesToDel){
            this.movies.removeIf(m -> m.getId() == movie.getId());
        }
    }


    public List<Movie> getAllMovies() {
        return movies;
    }

    public void refresh(List<Movie> allMovies) {
        this.movies = allMovies;
    }
}

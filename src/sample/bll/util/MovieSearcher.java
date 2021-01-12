package sample.bll.util;

import sample.be.Category;
import sample.be.Movie;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;

public class MovieSearcher {
    List<Movie> allMovies;

    List<Movie> moviesToReturn = new ArrayList<>();

    private SearchForSimilarTitles searchForSimilarTitles = new SearchForSimilarTitles();


    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public List<Movie> getSearch(List<Movie> allMovies, String query) {
        moviesToReturn.removeAll(moviesToReturn);
        //it should be outlined
        setAllMovies(allMovies);

        //run only if query is isn't a letter or word
        if(isNumeric(query)){
            boolean isNumeric = query.chars().allMatch( Character::isDigit );
            double Dnumber = Double.valueOf(query);
            int Inumber = (int) Dnumber;

            if(isNumeric && (Dnumber ==Inumber )
                    && Inumber>0 && Inumber<11)
                ratingSearcher(query);
        }

       //run if query isn't a singular number
        else
            Searcher(query);

        return moviesToReturn;
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    /**
     * also enable searching for similar words for example if similarity
     * is smaller or equal 2 or three (implement and then experiment)
     * @param query
     */
    private void Searcher(String query) {
        //search for a movie title
        for (Movie movie : allMovies) {
            if(compareToMovieName(query, movie.toString()))
                moviesToReturn.add(movie);
        }

    }

    private boolean compareToMovieName(String query, String movieToString) {
        if(movieToString.toLowerCase().contains(query.toLowerCase()))
            return true;
        else
            return false;
    }

    private void ratingSearcher(String query){
       // boolean isNumeric = query.chars().allMatch( Character::isDigit );
        double Dnumber = Double.valueOf(query);
        int Inumber = (int) Dnumber;
        //if the user inserted integer between 1-10
      //  if(isNumeric && (Dnumber ==Inumber ) && Inumber>0 && Inumber<11){

            // sort movies. i guess its descending order
            //there is no need to sort movies ealier
            allMovies.sort((movie1, movie2) -> Integer.compare(movie2.getRating(), movie1.getRating()));
            for(Movie movie: allMovies)
                System.out.println(movie.toString());

            int indexOfFound = binarySearch(Inumber);
            //add found movie
            moviesToReturn.add(allMovies.get(indexOfFound));
            //add movies in between
            moviesToReturn.addAll(moviesInBetween(indexOfFound));
            System.out.println("movies to return");
            for(Movie movie: moviesToReturn)
                System.out.println(movie.toString());
        }
    //}

    private List<Movie> moviesInBetween(int indexOfFound) {
        List<Movie> otherMovies = new ArrayList<>();
        int indexOfFoundCopy = indexOfFound;

        while((allMovies.get(indexOfFound).getRating() == allMovies.get(indexOfFound-1).getRating())
                && indexOfFound>=0){
            otherMovies.add(allMovies.get(indexOfFound-1));
            indexOfFound--;
        }
        while(indexOfFoundCopy<(allMovies.size()-1) && (allMovies.get(indexOfFoundCopy).getRating()
                == allMovies.get(indexOfFoundCopy+1).getRating())
                 ){
            otherMovies.add(allMovies.get(indexOfFoundCopy+1));
           indexOfFoundCopy++;
        }
        return otherMovies;
    }

    /**
     * method is used to find an index of searched movie
     * @return indexOfFound
     */
    private int binarySearch(int minimalRating)
    {
        int low=0;
        int high = allMovies.size()-1;
        while(low<=high){
            int middle = low + (high-low)/2;
            if(allMovies.get(middle).getRating()==minimalRating)
                return middle;
            if(allMovies.get(middle).getRating()< minimalRating)
                low = middle+1;
            if(allMovies.get(middle).getRating()> minimalRating)
                low = middle-1;
        }
        //if nothing was found. its indication that nothing was found.
        return -1;

    }


}

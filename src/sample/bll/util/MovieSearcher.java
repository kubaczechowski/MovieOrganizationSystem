package sample.bll.util;

import sample.be.Movie;

import java.util.*;

public class MovieSearcher {
    List<Movie> allMovies;
    List<Movie> moviesToReturn = new ArrayList<>();

    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public List<Movie> getSearch(List<Movie> allMovies, String query) {
        setAllMovies(allMovies);

       ratingSearcher(query);

        return moviesToReturn;
    }

    private void ratingSearcher(String query){
        boolean isNumeric = query.chars().allMatch( Character::isDigit );
        double Dnumber = Double.valueOf(query);
        int Inumber = (int) Dnumber;
        //if the user inserted integer between 1-10
        if(isNumeric && (Dnumber ==Inumber ) && Inumber>0 && Inumber<11){
            // sort movies. i guess its descending order
            //there is no need to sort movies ealier
            allMovies.sort((movie1, movie2) -> Integer.compare(movie2.getRating(), movie1.getRating()));
            int indexOfFound = binarySearch(Inumber);
            //add found movie
            moviesToReturn.add(allMovies.get(indexOfFound));
            //add movies in between
            moviesToReturn.addAll(moviesInBetween(indexOfFound));
        }
    }

    private List<Movie> moviesInBetween(int indexOfFound) {
        List<Movie> otherMovies = new ArrayList<>();
        Movie movie= allMovies.get(indexOfFound);
        int indexOfFoundCopy = indexOfFound;

        while(allMovies.get(indexOfFound).getRating() == allMovies.get(indexOfFound-1).getRating()
                && indexOfFound>=0){
            otherMovies.add(allMovies.get(indexOfFound-1));
            indexOfFound--;
        }
        while(allMovies.get(indexOfFoundCopy).getRating() == allMovies.get(indexOfFoundCopy-1).getRating()
                && indexOfFoundCopy<=(allMovies.size()-1)){
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

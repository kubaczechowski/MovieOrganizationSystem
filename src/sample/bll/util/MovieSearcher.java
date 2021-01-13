package sample.bll.util;

import sample.be.Movie;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;

public class MovieSearcher {
    List<Movie> allMovies;

    List<Movie> moviesToReturn = new ArrayList<>();

    //private SearchForSimilarTitles searchForSimilarTitles = new SearchForSimilarTitles();


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
            //boolean isNumeric = query.chars().allMatch( Character::isDigit );
            double Dnumber = Double.valueOf(query);
            int Inumber = (int) Dnumber;

            //case that we are looking for a rating and
            // in the future number at the beginning of name
            if( (Dnumber ==Inumber )
                    && Inumber>0 && Inumber<11)
                ratingSearcher(query);

            //case we are only looking for a number at the beginning of title or category
            else if((Dnumber ==Inumber ) && (Inumber<=0 || Inumber>=11 ))
                searcher(query);

        }

       //run if query isn't a number
        else
            searcher(query);

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
    private void searcher(String query) {
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


    //make it robust and implement case that we do not find
    //such number
    private void ratingSearcher(String query){

        double Dnumber = Double.valueOf(query);
        int Inumber = (int) Dnumber;

            // sort movies. i guess its descending order
            //there is no need to sort movies ealier
            allMovies.sort((movie1, movie2) -> Integer.compare(movie1.getRating(), movie2.getRating()));
            for(Movie movie: allMovies)
                System.out.println(movie.toString());

            int indexOfFound = binarySearch(Inumber);

            //didn't find
            if(indexOfFound==-1){

            }
            //found some number
            else {
                //add found movie
                moviesToReturn.add(allMovies.get(indexOfFound));
                //add movies in between
                //test if the problem is with movies in between
               moviesToReturn.addAll(moviesInBetween(indexOfFound));
                System.out.println("movies to return");
                for (Movie movie : moviesToReturn)
                    System.out.println(movie.toString());
            }
        }
    //}

    private List<Movie> moviesInBetween(int indexOfFound) {
        List<Movie> otherMovies = new ArrayList<>();
        int indexOfFoundCopy = indexOfFound;

        while(indexOfFoundCopy>0 &&(allMovies.get(indexOfFound).getRating() == allMovies.get(indexOfFound-1).getRating() )){
            otherMovies.add(allMovies.get(indexOfFound-1));
            if(indexOfFound>0)
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
    //i think it doesnt work as it should
    private int binarySearch2(int minimalRating)
    {
        int low=0;
        int high = allMovies.size()-1;
        while(low<=high){
            int middle = low + (high-low)/2;
            if(allMovies.get(middle).getRating()==minimalRating)
                return middle;
            if(allMovies.get(middle).getRating()< minimalRating)
                low =  middle+1;
            if(allMovies.get(middle).getRating()> minimalRating)
                low =  middle-1;
        }
        //if nothing was found. its indication that nothing was found.
        return -1;

    }

    private int binarySearch(int minimalRating)
    {
        int low=0;
        int high = allMovies.size()-1;
        int middle; // we nn
        while(low<high){
            middle = low + (high-low)/2;

            if(allMovies.get(middle).getRating() < minimalRating)
                low =  middle + 1;
            else
                high=middle;
        }
        //if nothing was found. its indication that nothing was found.

        if(allMovies.get(low).getRating()==minimalRating)
            return low;
        else
            return -1;

    }


}

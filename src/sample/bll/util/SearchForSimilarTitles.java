package sample.bll.util;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;
import java.util.ArrayList;
import java.util.List;


public class SearchForSimilarTitles {
    private List<Movie> allMovies;
    private List<Category> allCategories;

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public SearchForSimilarTitles(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public SearchForSimilarTitles() {
    }

    /**
     * returns the list of movies that are similar
     * if the list is null it means that in higher layer
     * no prompt will appear to the user with information about
     * similar titles
     */
    public List<String> getSimilarMovies(String newTitle) {
        List<String> namesOfSimilarMovies = new ArrayList<>();

        for(Movie movie:allMovies)
        {
            if(isVerySimilar(newTitle, movie.getName(), 2, true))
                namesOfSimilarMovies.add(movie.getName());
        }

        return namesOfSimilarMovies;
    }

    public List<Category> getSimilarCategories(String query, List<Category> allCategories){
        setAllCategories(allCategories);
        List<Category> similarCategories = new ArrayList<>();

        for(Category category: allCategories){
            if(isVerySimilar(query, category.getName(), 3, true))
                similarCategories.add(category);
        }
        return similarCategories;
    }

    /**
     * if the similarity is smaller or equals one return true
     */
   private boolean isVerySimilar(String newTitle, String existingTitle,
                                 int setSimilarity, boolean includeThatNumber)
    {
        int difference = levenshteinDistance(newTitle, existingTitle);

        if(difference<setSimilarity)
            return true;
        else if(includeThatNumber && setSimilarity==difference)
            return true;
        else
            return false;
    }

    public  int levenshteinDistance( String s1, String s2 ) {
        return dist( s1.toCharArray(), s2.toCharArray() );
    }

    /**
     * algorithm is gently borrowed from stackoverflow
     * in this very program algorithm is used to check the similarity between
     * the name of the movie that is being created and other movies that
     * already exist
     *source of the algorithm:
     * https://stackoverflow.com/questions/13564464/problems-with-levenshtein-algorithm-in-java
     */
    private int dist( char[] s1, char[] s2 ) {

        // memoize only previous line of distance matrix
        int[] prev = new int[ s2.length + 1 ];

        for( int j = 0; j < s2.length + 1; j++ ) {
            prev[ j ] = j;
        }

        for( int i = 1; i < s1.length + 1; i++ ) {

            // calculate current line of distance matrix
            int[] curr = new int[ s2.length + 1 ];
            curr[0] = i;

            for( int j = 1; j < s2.length + 1; j++ ) {
                int d1 = prev[ j ] + 1;
                int d2 = curr[ j - 1 ] + 1;
                int d3 = prev[ j - 1 ];
                if ( s1[ i - 1 ] != s2[ j - 1 ] ) {
                    d3 += 1;
                }
                curr[ j ] = Math.min( Math.min( d1, d2 ), d3 );
            }

            // define current line of distance matrix as previous
            prev = curr;
        }
        return prev[ s2.length ];
    }

}

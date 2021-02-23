package sample.bll.util;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;
import java.util.ArrayList;
import java.util.List;


public class SearchForSimilarTitles {
    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

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


    /**
     * returns the list of movies that are similar
     * if the list is null it means that in higher layer
     * no prompt will appear to the user with information about
     * similar titles
     */
    public String getSimilarMovies(String newTitle) {
        List<String> namesOfSimilarMovies = new ArrayList<>();

        for(Movie movie:allMovies)
        {
            if(isVerySimilar(newTitle.toLowerCase(), movie.getName().toLowerCase(), 2, true))
                namesOfSimilarMovies.add(movie.getName());
        }
        if(namesOfSimilarMovies.isEmpty())
            return null;
        else
            return getListOfSimilarItems(namesOfSimilarMovies);
    }

    private String getListOfSimilarItems(List<String> namesOfSimilarItems){
        String similar = " ";
        for (String item : namesOfSimilarItems) {
            //I dont want to start with a comma not like this: ,item1,item2
            //add comma if already there is an item
            if (similar.length() > 1)
                similar += ", ";

            similar += item + " ";
        }
        return similar;
    }

    public boolean checkIfExists(String text) {
       for(Movie movie: allMovies){
           if(text.toLowerCase().equals(movie.getName().toLowerCase()))
               return true;
       }
       return false;
    }

    public String getSimilarCategories(String query, List<Category> allCategories){
        setAllCategories(allCategories);
        List<String> similarCategories = new ArrayList<>();

        for(Category category: allCategories){
            if(isVerySimilar(query.toLowerCase(), category.getName().toLowerCase(), 3, false))
                similarCategories.add(category.getName());
        }
        if(similarCategories.isEmpty())
            return null;
        else
            return getListOfSimilarItems(similarCategories);
    }

    /**
     * if the similarity is smaller or equals one return true
     */
   private boolean isVerySimilar(String newTitle, String existingTitle,
                                 int setSimilarity, boolean includeThatNumber)
    {
        int difference = levensteinMinDistance(newTitle, existingTitle);

        if(difference<setSimilarity)
            return true;
        else if(includeThatNumber && setSimilarity==difference)
            return true;
        else
            return false;
    }


    public int levensteinMinDistance(String word1, String word2) {
        int[][] dist =  new int[word1.length()+1][word2.length()+1];

        //populate first column and first row because its  just simple incrementing
        // in the first one its deleteing
        //in the second one is inserting
        for(int i=0; i<dist.length; i++){
            dist[i][0] = i;
        }
        for(int i=0; i<dist[0].length; i++){
            dist[0][i] = i;
        }
        //the most crushial part
        for(int col=1; col<dist.length; col++){
            for(int row =1; row< dist[0].length; row++ ){
                //if characters are eqal dont do anything just paste
                if(word1.charAt(col -1) == word2.charAt(row-1))
                    dist[col][row] = dist[col-1][row-1];
                else{
                    //get the smallest number from all the movements
                    //get the smallest from: insert, replace, delete
                    dist[col][row] = Math.min(dist[col-1][row], Math.min(
                            dist[col-1][row-1], dist[col][row-1]) ) +1;
                }
            }
        }
        return dist[word1.length()][word2.length()];
    }

}

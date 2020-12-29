package sample.dal.interfaces;

import sample.be.Category;
import sample.dal.exception.DALexception;

import java.util.List;

public interface CatMovieInterface {
    List<Category> getCategoriesFromSpecificMovie(int movieID) throws DALexception;
    void addCategoryToMovie(int categoryID, int movieID) throws DALexception;
    void deleteCategoryFromMovie(int categoryID, int movieID) throws DALexception;
    boolean checkIfMovieHasSuchCategory(int categoryID, int MovieID) throws DALexception;
}

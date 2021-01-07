package sample.dal.interfaces;

import sample.be.Category;
import sample.dal.exception.DALexception;

import java.util.List;

public interface CategoryInterface {
    List<Category> getAllCategories() throws DALexception;
    void addCategory(Category category) throws DALexception;
    void deleteCategory(Category category) throws DALexception;
    boolean checkIfSuchCategoryExists(String name) throws DALexception;
}

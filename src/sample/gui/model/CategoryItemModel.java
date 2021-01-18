package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Category;
import sample.be.CategoryItem;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;

public class CategoryItemModel {
    private static CategoryItemModel categoryItemModel;
    private ObservableList<Category> obsCategoryItems;
    private BLLFacade logicLayer;

    //we use singleton pattern
    private CategoryItemModel() {
        obsCategoryItems = FXCollections.observableArrayList();
        try {
            logicLayer = new BLLController(false);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public static CategoryItemModel getInstance() {
        if(categoryItemModel ==  null)
            categoryItemModel = new CategoryItemModel();
        return categoryItemModel;
    }

    public void load(){
        obsCategoryItems.clear();
        try {
            obsCategoryItems.addAll(logicLayer.getAllCategories());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void addCategoryItem(int movieID, int categoryID)
    {
        try {
            logicLayer.addCategoryItem(movieID, categoryID);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        try {
            logicLayer.refreshcashList();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void deleteCategoryItem(int movieID, int categoryID)
    {
        try {
            logicLayer.deleteCategoryItem(movieID, categoryID);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        try {
            logicLayer.refreshcashList();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public boolean checkIfMovieHasCategory(int movieID, int categoryID) {
        try {
             return logicLayer.checkIfMovieHasCategory(movieID, categoryID);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        System.out.println("couldnt check if movie has category");
        return true;
    }

    public boolean categoryIsSetOnAnyMovie(int id) {
        try {
            return logicLayer.categoryIsSetOnAnyMovie(id);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return true;
    }
}

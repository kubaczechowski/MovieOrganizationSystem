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

    public CategoryItemModel() {
        obsCategoryItems = FXCollections.observableArrayList();
        logicLayer = new BLLController();
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
    }

    public void deleteCategoryItem(int movieID, int categoryID)
    {
        try {
            logicLayer.deleteCategoryItem(movieID, categoryID);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }
}

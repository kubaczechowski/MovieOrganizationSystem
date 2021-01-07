package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.be.Category;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;
import sample.gui.util.AlertDisplayer;

public class CategoryModel {
    private static CategoryModel categoryModel;
    private ObservableList<Category> obsCategories;
    private BLLFacade logicLayer;
    private AlertDisplayer alertDisplayer = new AlertDisplayer();

    public CategoryModel() {
        obsCategories = FXCollections.observableArrayList();
        logicLayer = new BLLController();
    }

    public static CategoryModel getInstance() {
        if(categoryModel ==  null)
            categoryModel = new CategoryModel();
        return categoryModel;
    }

    public void save(Category category) {
        try {
            logicLayer.saveCategory(category);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        obsCategories.add(category);
    }

    public void delete(Category selectedItem) {
        try {
            logicLayer.deleteCategory(selectedItem);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        obsCategories.remove(selectedItem);
    }

    public ObservableList<Category> getAllCategories()
    {
        return obsCategories;
    }


    public void load() {
        obsCategories.clear();
        try {
            obsCategories.addAll(logicLayer.getAllCategories());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public boolean chechIfExists(String newCategory) {
        try {
            return logicLayer.checkIfCategoryExists(newCategory);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        //if we get here it means that something went wrong
        //show allert
        alertDisplayer.displayAlert("couldnt add new category",
                "something must gone wrong", "please try again",
                Alert.AlertType.ERROR);
        //it will prevent from adding new category
        return true;
    }
}

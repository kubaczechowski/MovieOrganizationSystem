package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Category;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;

public class CategoryModel {
    private static CategoryModel categoryModel;
    private ObservableList<Category> obsCategories;
    private BLLFacade logicLayer;

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
}

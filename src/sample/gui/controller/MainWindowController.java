package sample.gui.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.CategoryItemModel;
import sample.gui.model.CategoryModel;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private MovieModel movieModel;
    private CategoryModel categoryModel;
    private CategoryItemModel categoryItemModel;
    private AlertDisplayer alertDisplayer = new AlertDisplayer();

    public MainWindowController() {
        movieModel = MovieModel.getInstance();
        categoryModel = CategoryModel.getInstance();
        categoryItemModel = CategoryItemModel.getInstance();
    }

    @FXML private TableView<Movie> moviesTable;
    @FXML private TableColumn<Movie, String> columnName;
    @FXML private TableColumn<Movie, Integer> columnRating;
    @FXML private TableColumn<Movie, String> columnLastView;
    @FXML private  TableColumn<Movie, List<Category>> columnCategories;
    //ListView
    @FXML private ListView<Category> categoriesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
        initListView();
    }

    private void initTableView() {
        columnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("rating"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<Movie, String>("lastview"));
        columnCategories.setCellValueFactory(
                new PropertyValueFactory<Movie, List<Category>>("categoryList") );
        //categories
        columnCategories.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, List<Category>>, ObservableValue<List<Category>>>() {
            @Override
            public ObservableValue<List<Category>> call(TableColumn.CellDataFeatures<Movie, List<Category>> movieListCellDataFeatures) {
                return new ReadOnlyObjectWrapper( movieListCellDataFeatures.getValue().getCategoryList());
            }
        });
        //load data and set items
        movieModel.load();
        moviesTable.setItems(movieModel.getAllMovies());
    }

    private void initListView(){
        categoryModel.load();
        categoriesList.setItems(categoryModel.getAllCategories());
    }


    public void addMovieButton(ActionEvent actionEvent) {
        //create loader
        FXMLLoader loader = null;
        Parent root = null;
        String path = "/sample/gui/view/addMovieWindow.fxml";
        // method returns a URL object or null if no resource with this name(path) is found.
        loader = new FXMLLoader(getClass().getResource(path));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create new window
        createStage(root, "New Movie");
    }

    private void createStage(Parent root, String title)
    {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteMovieButton(ActionEvent actionEvent) {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        //display alert if user didn't select movie
        if(selectedMovie==null)
            alertDisplayer.displayAlert("no movie",
                    "please select a movie", "no movie", Alert.AlertType.INFORMATION);

        //show alert to ensure that user wants to delete movie
         boolean result = alertDisplayer.displayConfirmationAlert("Delete Movie", "Do you want to delete movie?",
                "Delete");

         //delete movie if user decided to do so
         if(result==true) {
             //System.out.println("do action");
             //delete from table && db
             movieModel.delete(selectedMovie);
             movieModel.load();
         }
         //close the window if user decided not to delete movie
         else {
             //System.out.println("close the program");
             //overall do nothing??
         }
    }

    public void changeRatingButton(ActionEvent actionEvent) {
    }

    /**
     * method is called when user decides to create a new category
     * @param actionEvent
     */
    public void addCategory(ActionEvent actionEvent) {
        String newCategory = alertDisplayer.ShowTextInputDialog("add category",
               "please add new category", "new category");
        if(newCategory!=null){
            //System.out.println(newCategory);
            Category category = new Category(newCategory);
            //call category model
            categoryModel.save(category);
        }
    }

    public void removeCategory(ActionEvent actionEvent) {
        Category selectedItem = categoriesList.getSelectionModel().getSelectedItem();
        if(selectedItem==null)
            alertDisplayer.displayAlert("No item selected",
                    "Please select an item", "no category selected",
                    Alert.AlertType.INFORMATION);
        //show alert to ensure that user wants to delete movie
        boolean result = alertDisplayer.displayConfirmationAlert("Delete Category",
                "Do you want to delete category?", "Delete");
        if(result==true)
        {
            categoryModel.delete(selectedItem);
            categoryModel.load();
        }
    }

    /**
     * method sets the category on the movie if the movie is selected
     * and the category is selected
     * @param actionEvent
     */
    public void setCategory(ActionEvent actionEvent) {
        //get selected items
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        Category selectedCategory = categoriesList.getSelectionModel().getSelectedItem();
        //show alerts if needed
        showAlertsWhenSettingCategories(selectedMovie, selectedCategory);
        //check if this category is added if yes show alert
        //if true show alert
        //if false continue
        boolean result = categoryItemModel.checkIfMovieHasCategory(selectedMovie.getId(), selectedCategory.getId());
        if(result==true)
            alertDisplayer.displayAlert("Category",
                    "you cannot add one category twice", "such category is added",
                    Alert.AlertType.WARNING);
        else{
            
        //do action
        categoryItemModel.addCategoryItem(selectedMovie.getId(), selectedCategory.getId());
        categoryItemModel.load();
        movieModel.load();
        System.out.println( "set category" + selectedMovie.getCategoryList());
        }

    }
    public void unsetCategory(ActionEvent actionEvent) {

    }
    private void showAlertsWhenSettingCategories(Movie selectedMovie, Category selectedCategory)
    {
        if(selectedMovie==null)
            alertDisplayer.displayAlert("No movie selected",
                    "Please select a movie", "no movie selected",
                    Alert.AlertType.INFORMATION);
        if(selectedCategory==null)
            alertDisplayer.displayAlert("No category selected",
                    "Please select a category", "no category selected",
                    Alert.AlertType.INFORMATION);
    }
}

package sample.gui.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.CategoryItemModel;
import sample.gui.model.CategoryModel;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Timestamp;
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
        //if double clicked on selected movie method is invoked
       moviePlayer();
    }

    private void moviePlayer() {
        moviesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    //get chosen movie
                    Movie movieToPlay = moviesTable.getSelectionModel().getSelectedItem();
                    //open the window with movie player
                    openMoviePlayer(movieToPlay).play();
                    //refresh the lastview
                    movieModel.updateLastview(movieToPlay);
                    movieModel.load();
                }
            }
        });
    }

    private MoviePlayerController openMoviePlayer(Movie movieToPlay)
    {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("/sample/gui/view/moviePlayer.fxml"));
        Parent root=null; //local variables arent automatically initialized
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MoviePlayerController moviePlayerController = loader.getController();
        moviePlayerController.setFilePath(movieToPlay.getFilelink());
        moviePlayerController.sendMovieName(movieToPlay.getName());
        Stage stage = new Stage();
        stage.setTitle("movie player");
        stage.setScene(new Scene(root));
        stage.show();

        return moviePlayerController;
    }


    private String lastviewToShow(Movie movie)
    {
        if(movie.getLastview()==null)
            return "not seen";
        else
            return String.valueOf(movie.getLastview());
    }

    private void initTableView() {
        columnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("rating"));
        //columnLastView.setCellValueFactory(new PropertyValueFactory<Movie, String>("lastview"));
        columnLastView.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movie, String> object) {
                return new ReadOnlyObjectWrapper<>(lastviewToShow(object.getValue()));
            }
        });
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
        else {
            //show alert to ensure that user wants to delete movie
            boolean result = alertDisplayer.displayConfirmationAlert("Delete Movie", "Do you want to delete movie?",
                    "Delete");

            //delete movie if user decided to do so
            if (result == true) {
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
    }

    /**
     * Button used to change personal rating if such is inserted or to
     * add a new one if a movie doesn't have rating yet <-
     * rating must be added while creating a movie so adding it here\
     * doesn't make sense
     * @param actionEvent
     */
    public void changeRatingButton(ActionEvent actionEvent) {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        //check if movie was selected
        if(selectedMovie==null)
            alertDisplayer.displayAlert("No movie",
                    "Please select a movie", "movie wasn't selected",
                    Alert.AlertType.INFORMATION);
        else {
            String newRating = null;
            //if its null
            try {
                newRating = alertDisplayer.ShowTextInputDialog("change rating",
                        "please insert new rating", "changing rating");
            } catch (NumberFormatException numberFormatException) {
                alertDisplayer.displayAlert("Nothing was selected",
                        "Please select an item", "nothing selected",
                        Alert.AlertType.INFORMATION);
            }
            //if number wasnt selected
            if (newRating == null)
                alertDisplayer.displayAlert("Number wasn't selected",
                        "Please insert a number", "number should be in range of 1 to 10",
                        Alert.AlertType.WARNING);
            //in case of number out of bounds or null
             else if (Integer.parseInt(newRating) < 1 || Integer.parseInt(newRating) > 10)
                alertDisplayer.displayAlert("Number isn't correct",
                        "Please insert a correct number", "number should be in range of 1 to 10",
                        Alert.AlertType.WARNING);
             else {
                //in case of number format exception
                try {
                    selectedMovie.setRating(Integer.parseInt(newRating));
                } catch (NumberFormatException numberFormatException) {
                    alertDisplayer.displayAlert("Number format exception",
                            "Please insert a number", "number should be in range of 1 to 10",
                            Alert.AlertType.WARNING);
                }
            }
        }
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
        //get movie user want to modify and cateogry that has to be removed
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        Category selectedCategory = categoriesList.getSelectionModel().getSelectedItem();
        //check if category and movie are selected
        showAlertsWhenSettingCategories(selectedMovie, selectedCategory);
        //check if such category is added
        boolean result = categoryItemModel.checkIfMovieHasCategory(selectedMovie.getId(), selectedCategory.getId());
        if(result==false)
            alertDisplayer.displayAlert("Category",
                    "please select category that is added to the movie",
                    "such category isn't added to the movie",
                    Alert.AlertType.WARNING);
        //do action
            else{
                categoryItemModel.deleteCategoryItem(selectedMovie.getId(), selectedCategory.getId());
                categoryItemModel.load();
                movieModel.load();
        }
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

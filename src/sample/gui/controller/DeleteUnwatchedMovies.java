package sample.gui.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.DeleteUnwatchedModel;
import sample.gui.model.MovieModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteUnwatchedMovies implements Initializable {

    private static DeleteUnwatchedModel deleteUnwatchedModel;
    private static MovieModel movieModel;

    @FXML private TableView<Movie> moviesTable;
    @FXML private TableColumn<Movie, String> columnName;
    @FXML private TableColumn<Movie, Integer> columnRating;
    @FXML private TableColumn<Movie, String> columnLastView;
    @FXML private  TableColumn<Movie, List<Category>> columnCategories;

    public DeleteUnwatchedMovies() {
        deleteUnwatchedModel = DeleteUnwatchedModel.getInstance();
        movieModel = MovieModel.getInstance();
    }

    public void deleteAll(ActionEvent actionEvent) {
        deleteUnwatchedModel.deleteAll();
        deleteUnwatchedModel.load();
        //refresh moviemodel
        movieModel.load();
        closeStage(actionEvent);
    }

    /**
     * method enables to select multiple movies
     * @param actionEvent
     */
    public void deleteSelected(ActionEvent actionEvent) {
    List<Movie> moviesToDelete = moviesTable.getSelectionModel().getSelectedItems();
    deleteUnwatchedModel.delete(moviesToDelete);
    deleteUnwatchedModel.load();
    //refresh moviemodel
    movieModel.load();
    //close stage
        closeStage(actionEvent);
    }

    /**
     * method simply does nothing and only closes that window
     * @param actionEvent
     */
    public void notDelete(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }

    private void closeStage(ActionEvent event){
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
    }

    private void initTableView() {
        columnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("rating"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<Movie, String>("lastview"));
        //cateogories list
        columnCategories.setCellValueFactory(
                new PropertyValueFactory<Movie, List<Category>>("categoryList") );
        //categories
        columnCategories.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, List<Category>>, ObservableValue<List<Category>>>() {
            @Override
            public ObservableValue<List<Category>> call(TableColumn.CellDataFeatures<Movie, List<Category>> movieListCellDataFeatures) {
                return new ReadOnlyObjectWrapper( movieListCellDataFeatures.getValue().getCategoryList());
            }
        });
        //load model and data
        deleteUnwatchedModel.load();
        moviesTable.setItems(deleteUnwatchedModel.getObsMoviesToDelete());
    }
}

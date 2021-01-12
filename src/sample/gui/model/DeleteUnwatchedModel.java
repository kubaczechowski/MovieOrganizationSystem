package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;

import java.util.List;

public class DeleteUnwatchedModel {
    private static DeleteUnwatchedModel deleteUnwatchedModel;
    private ObservableList<Movie> obsMoviesToDelete;
    private BLLFacade logicLayer;

    private DeleteUnwatchedModel() {
        obsMoviesToDelete = FXCollections.observableArrayList();
        try {
            logicLayer = new BLLController();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public static DeleteUnwatchedModel getInstance() {
        if(deleteUnwatchedModel ==  null)
            deleteUnwatchedModel = new DeleteUnwatchedModel();
        return deleteUnwatchedModel;
    }

    public void load()
    {
        obsMoviesToDelete.clear();
        try {
            obsMoviesToDelete.addAll(logicLayer.getMoviesToDelete());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Movie> getObsMoviesToDelete(){return obsMoviesToDelete;}

    /**
     * method is used to delete all movies that
     * have not been seen for more than two years and
     * have poor rating
     */
    public void deleteAll() {
        //before delating movie we need to delete
        //all categoiers associated with that movie
        for(Movie item: obsMoviesToDelete) {
            //delete categoires
            deleteCategories(item);

            //delete movie
            try {
                logicLayer.deleteMovie(item);
            } catch (BLLexception blLexception) {
                blLexception.printStackTrace();
            }
        }
    }

    /**
     * method used to delete only movies selected by the user
     * user can select multiple movies
     * @param moviesToDelete
     */
    public void delete(List<Movie> moviesToDelete) {
        for(Movie item: moviesToDelete) {
            deleteCategories(item);
            try {
                logicLayer.deleteMovie(item);
            } catch (BLLexception blLexception) {
                blLexception.printStackTrace();
            }
        }
    }

    private void deleteCategories(Movie item) {
        try {
            logicLayer.deleteCategoriesItemsAssociated(item.getId());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

}

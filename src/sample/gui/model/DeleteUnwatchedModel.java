package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;

public class DeleteUnwatchedModel {
    private static DeleteUnwatchedModel deleteUnwatchedModel;
    private ObservableList<Movie> obsMoviesToDelete;
    private BLLFacade logicLayer;

    public DeleteUnwatchedModel() {
        obsMoviesToDelete = FXCollections.observableArrayList();
        logicLayer = new BLLController();
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




}

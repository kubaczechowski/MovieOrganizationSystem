package sample.bll;

import sample.be.Movie;
import sample.dal.DALController;
import sample.dal.IDALFacade;

import java.util.List;

public class BLLController implements BLLFacade{
    private IDALFacade dataaccess = new DALController();

    @Override
    public List<Movie> getAllMovies() {
        return  dataaccess.getAllMovies();

    }
}

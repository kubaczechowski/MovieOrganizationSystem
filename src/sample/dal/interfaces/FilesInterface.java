package sample.dal.interfaces;

import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;

import java.nio.file.Path;

public interface FilesInterface {

    void saveFileInProgramFolder(Path destinationPath, Path originPath)throws FileExceptionDAL;

    String setAndSaveImage(String fieldname, Path destinationPath) throws FileExceptionDAL, JCodecExceptionDAL;

    void deleteMovie(Movie movie) throws DALexception, FileExceptionDAL;
}

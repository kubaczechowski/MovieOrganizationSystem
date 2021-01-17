package sample.dal.interfaces;

import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;

import java.nio.file.Path;

public interface FilesInterface {

    void saveFileInProgramFolder(Path destinationPath, Path originPath)throws FileExceptionDAL;

    String setAndSaveImage(String fieldname, Path destinationPath) throws FileExceptionDAL, JCodecExceptionDAL;
}

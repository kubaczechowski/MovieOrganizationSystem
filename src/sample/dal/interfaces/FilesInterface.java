package sample.dal.interfaces;

import javafx.scene.Node;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;

public interface FilesInterface {

    void saveFileInProgramFolder()throws FileExceptionDAL;

    String setAndSaveImage(String fieldname) throws FileExceptionDAL, JCodecExceptionDAL;
}

package sample.dal.File;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Frame;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;
import sample.dal.interfaces.FilesInterface;
import sample.gui.model.MovieModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FilesOperations implements FilesInterface {
    Path pathOrigin; // rememer to make it null after each operation
    Path destinationPath; //rememer to make it null after each operation
private static FilesOperations filesOperations;

    //singleton pattern
    public static FilesOperations getInstance() {
        if(filesOperations ==  null)
            filesOperations = new FilesOperations();

        return filesOperations;
    }


    public void saveFileInProgramFolder() throws FileExceptionDAL {
        try {
            Files.copy(pathOrigin, destinationPath, REPLACE_EXISTING);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new FileExceptionDAL("Couldn't save movie in the program " +
                    "folder", e);
        }
        prepareForNextOperations();
    }
    private void prepareForNextOperations(){
        pathOrigin=null;
        destinationPath= null;
    }

    public String setAndSaveImage(String namefieldText) throws FileExceptionDAL, JCodecExceptionDAL {
        File file1 = new File(destinationPath.toString());
        Picture frame = getFrameFromMovie(file1);
        String filename = getFileName(namefieldText);
        saveImageInProgram(frame, filename);
        return "src/../Images/" + filename+ ".jpg";
    }

    private void saveImageInProgram(Picture frame, String filename) throws FileExceptionDAL {
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(frame);
        try {
            ImageIO.write( bufferedImage, "jpg",
                    new File("src/../Images/"+ filename +".jpg"));
        } catch (IOException e) {
            throw new FileExceptionDAL("Couldn't save image in the program " +
                    "folder", e);
        }
    }

    private  String getFileName(String namefieldText){
        String filename;
        if(namefieldText==null) {
            long time = System.currentTimeMillis();
            filename = String.valueOf(time);
        }
        else
            filename = namefieldText;
        return filename;
    }

    private Picture getFrameFromMovie(File file1) throws FileExceptionDAL, JCodecExceptionDAL {
        Picture frame = null;
        try {
             return frame =  FrameGrab.getFrameFromFile(file1, 1);
        } catch (IOException e) {
           throw new FileExceptionDAL(" coundn't get a picture from a movie",
                   e);
        } catch (JCodecException e) {
            throw new JCodecExceptionDAL("couldn't get a picture from a movie", e);
        }
    }

}

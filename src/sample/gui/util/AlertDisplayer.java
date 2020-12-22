package sample.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.TilePane;

import java.lang.reflect.Method;

/**
 * alert types:
 *      * Alert.AlertType.CONFIRMATION
 *      * Alert.AlertType.INFORMATION
 *      * Alert.AlertType.ERROR
 *      * Alert.AlertType.WARNING
 *      * Alert.AlertType.NONE
 */
public class AlertDisplayer {
    /**
     * Alert suggests that the program is missing some data
     * data needs to be inserted by the user
     *
     */
    public boolean displayConfirmationAlert(String title, String information, String header)
    {
        //it must be final and array element to be set in the lambda expression. check that
        final boolean[] result = new boolean[1];
        TilePane tilePane = new TilePane();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(information);
        alert.showAndWait().ifPresent( buttonType -> {
            if(buttonType == ButtonType.OK)
            {
                result[0] =true;
            }
            if(buttonType==ButtonType.CANCEL)
                result[0] = false;
        });
        return result[0];
    }

    public void displayAlert(String title, String information, String header,
                                        Alert.AlertType alertType)
    {
        TilePane tilePane = new TilePane();
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(information);
        alert.show(); //no other action is needed
    }


}

package sample.bll.util;

import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class Validations {

    public Path getDestinationPath(String namefieldText, Path originPath) {
        if (originPath.toString().contains(".mp4"))
            return  Path.of("src/../Movies/" + namefieldText + ".mp4" );
        else if(originPath.toString().contains(".mpeg4"))
            return Path.of("src/../Movies/" + namefieldText + ".mpeg4" );
        else
            return null; //something went wrong. there is weird case when it happens
    }

    public boolean isValidFileExtension(String absolutePath) {
        if( absolutePath.contains(".mp4") || absolutePath.contains(".mpeg4"))
            return true;
        else
            return false;
    }

    public boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public boolean isFileLinkCorrect(String text) {
        if(!text.equals("file link") && !text.isEmpty())
            return true;
        else
            return false;
    }

    public boolean isNameFieldCorrect(String text) {
        if(text!=null && !text.isEmpty() && !text.equals("name"))
            return true;
        else
            return false;
    }

    public boolean checkBoundsOfRating(int parseInt) {
        if(parseInt>0 && parseInt<11)
            return true;
        else
            return false;
    }
}

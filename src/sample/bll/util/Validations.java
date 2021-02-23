package sample.bll.util;

import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    public Path getDestinationPath(String namefieldText, Path originPath) {
        if (originPath.toString().endsWith(".mp4"))
            return  Path.of("src/../Movies/" + namefieldText + ".mp4" );
        else if(originPath.toString().endsWith(".mpeg4"))
            return Path.of("src/../Movies/" + namefieldText + ".mpeg4" );
        else
            return null; //something went wrong. there is weird case when it happens
    }

    /*
    public boolean isValidFileExtension(String absolutePath) {
        if( absolutePath.contains(".mp4") || absolutePath.contains(".mpeg4"))
            return true;
        else
            return false;
    }

     */

    //testing needed
    public boolean isValidFileExtension(String absolutePath) {
        String patternString = "src/../Movies/+\\s+(\\.mp4 | \\.mpeg4)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(absolutePath);

       return matcher.matches();
    }

    /*
    public boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
     */

    //is it used only fot the rating? if yes only from 1 to 10
    //then check bounds of rating will be reluctant
    public boolean isNumeric(String str) {
        String patternString = "(10 | [0-9])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
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

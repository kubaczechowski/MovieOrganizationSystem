package sample.bll.util;

import sample.be.Movie;

import java.sql.Timestamp;


/**
 * method shows lastview differently depending
 *      * on when it was the last time the movie was opened.
 *      * - less than a quarter show ex. less than a quater ago
 *      * - less than a day show ex. 4 hours ago
 *      * - less than a week for ex 6 days ago
 *      * - then show a concreate date when the movie was opened
 * in the format ex. 2021-01-16 06:43:19.77
 */
public class TimeCalculator {

    public String timeDifference(int currentTimeInMillis,
        int lastviewInMillis, Timestamp timestamp)
    {
        int currentTimeInSec = currentTimeInMillis/1000;
        int lastviewInSec = lastviewInMillis/1000;

        int difference =  currentTimeInSec-lastviewInSec;
        //within quarter 15 *60 sec
        if( currentTimeInSec-lastviewInSec< 15*60)
            return "within quarter";
        //if less in hours
        else if(difference< 60*60)
        {
            int minutes = (int) difference/60;
            if(minutes==1)
                return "1 minute ago";
            else
                return minutes + " minutes ago";
        }
        //less than a day. show in hours ex. 6 hours ago
        else if(currentTimeInSec-lastviewInSec < 24 * 60*60)
        {
            //when downcasting when 1h and 30 min we will show 1 hour
            int hours = (int) difference / (60*60);
                if(hours==1)
                    return "1 hour ago";
                else
                    return hours + " hours ago";
        }
        //less than a week ex. 5 day ago
        else if(difference < 7* 24 * 60*60 )
        {
            //when downcasting we will get full days
            //if it will be 5 days and 5 hours we will get 5 days
            int days = (int) difference/ (24 * 60*60);
            if(days==1)
                return "1 day ago";
            else
                return days + " days ago";
        }
        else
            return String.valueOf(timestamp);
    }

    public String lastViewToShow(Movie movie) {
        if(movie.getLastview()==null)
            return "not seen";

        else {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            // method get time Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
            int currentTimeInMillis = (int) currentTime.getTime(); //downcast long to int
            int lastviewInMillis = (int) movie.getLastview().getTime();

            return timeDifference(currentTimeInMillis,
                    lastviewInMillis, movie.getLastview());
        }
    }
}

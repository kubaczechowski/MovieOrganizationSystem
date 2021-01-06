package sample.bll.util;

import java.sql.Timestamp;

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
            int days = (int) difference/ 24 * 60*60;
            if(days==1)
                return "1 day ago";
            else
                return days + " days ago";
        }
        else
            return String.valueOf(timestamp);
    }
}

package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /*
     * firstDate < secondDate => -1
     * firstDate == secondDate => 0
     * firstDate > secondDate => 1
     */
    public static int compareDates(GregorianCalendar fisrtDate, GregorianCalendar secondDate) {
        if (fisrtDate.get(Calendar.YEAR) > secondDate.get(Calendar.YEAR)) {
            return 1;
        } else if (fisrtDate.get(Calendar.DAY_OF_YEAR) < secondDate.get(Calendar.DAY_OF_YEAR)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean isToday(GregorianCalendar date) {
        GregorianCalendar today = new GregorianCalendar();

        return date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
                && date.get(Calendar.YEAR) == today.get(Calendar.YEAR);
    }

    public static boolean isPast(GregorianCalendar date) {
        GregorianCalendar today = new GregorianCalendar();

        if (date.get(Calendar.YEAR) < today.get(Calendar.YEAR)) {
            return true;
        } else if (date.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && date.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }
    public static GregorianCalendar copyGregorianCalendar(GregorianCalendar calendar) {
        calendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    public static String getDateString(GregorianCalendar date) {
        String year = String.valueOf(date.get(Calendar.YEAR));
        //Months start at 0
        String month = String.valueOf(date.get(Calendar.MONTH) + 1);
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

        return year + "-" + month + "-" + day;
    }

    public static String getDateDMY(GregorianCalendar date) {
        String year = String.valueOf(date.get(Calendar.YEAR));
        //Months start at 0
        String month = String.valueOf(date.get(Calendar.MONTH) + 1);
        if (Integer.parseInt(month) < 10) month = "0" + month;

        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(day) < 10) day = "0" + day;

        return day + '/' + month + '/' + year;
    }

    public static GregorianCalendar stringToGregorianCalendar(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return cal;
    }
}

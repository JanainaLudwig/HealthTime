package utils;

import java.util.Calendar;
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

    public static GregorianCalendar copyGregorianCalendar(GregorianCalendar calendar) {
        calendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        return calendar;
    }
}
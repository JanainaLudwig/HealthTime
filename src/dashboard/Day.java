package dashboard;

import com.jfoenix.controls.JFXButton;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Day {
    private GregorianCalendar date;
    private JFXButton button;

    public Day(JFXButton button, GregorianCalendar date) {
        this.button = button;
        this.date = date;
        this.button.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
    }

    public boolean buttonIsDisabled() {
        //Weekend
        if (date.get(Calendar.DAY_OF_WEEK) == 1 || date.get(Calendar.DAY_OF_WEEK) == 7) {
            return true;
        }

        Calendar today = new GregorianCalendar();
        //Past days
        if (date.compareTo(today) < 0 && date.get(Calendar.MONTH) < today.get(Calendar.MONTH)) {
            return true;
        }

        return false;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }
}

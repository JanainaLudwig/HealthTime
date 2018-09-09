package dashboard;

import com.jfoenix.controls.JFXButton;
import utils.DateUtils;

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

        GregorianCalendar today = new GregorianCalendar();
        //Past days
        if (DateUtils.compareDates(date, today) == -1) {
            return true;
        }

        return false;
    }
/*
    private void buttonClick() {
        System.out.println(this.date.toString());
    }
*/
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

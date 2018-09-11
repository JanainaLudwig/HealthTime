package dashboard;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WeekDay {
    private ArrayList<Appointment> appointments;
    private GregorianCalendar date;

    public WeekDay(GregorianCalendar date) {
        appointments = new ArrayList<>();
        this.date = date;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}

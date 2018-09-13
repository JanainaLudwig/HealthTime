package dashboard;

import DAO.DAOAppointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeekDay {
    private ArrayList<Appointment> appointments;
    private GregorianCalendar date;

    public WeekDay(GregorianCalendar date) {
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

    public String getDateString() {
        String year = String.valueOf(date.get(Calendar.YEAR));
        //Months start at 0
        String month = String.valueOf(date.get(Calendar.MONTH) + 1);
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

        return year + "-" + month + "-" + day;
    }

    public Appointment getAppointment(int time) {
        for (Appointment appointment: appointments) {
            if (appointment.getTime().getTimeCode() == time) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "appointments=" + appointments +
                ", date=" + date +
                '}';
    }
}

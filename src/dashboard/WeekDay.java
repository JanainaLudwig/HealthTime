package dashboard;

import dashboard.view.DashboardWeekController;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WeekDay {
    private ArrayList<Appointment> appointments;
    private GregorianCalendar date;
    private User user;
    private DashboardWeekController controller;

    public WeekDay(GregorianCalendar date, User user, DashboardWeekController controller) {
        this.date = date;
        this.user = user;
        this.controller = controller;
    }

    public DashboardWeekController getController() {
        return controller;
    }

    public void setController(DashboardWeekController controller) {
        this.controller = controller;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

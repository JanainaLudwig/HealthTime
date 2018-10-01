package dashboard;

import dashboard.view.DashboardWeekController;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WeekDay {
    private ArrayList<AvailableAppointment> availableAppointments;
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

    public ArrayList<AvailableAppointment> getAvailableAppointments() {
        return availableAppointments;
    }

    public void setAvailableAppointments(ArrayList<AvailableAppointment> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public AvailableAppointment getAppointment(int time) {
        for (AvailableAppointment availableAppointment : availableAppointments) {
            if (availableAppointment.getTime().getTimeCode() == time) {
                return availableAppointment;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "availableAppointments=" + availableAppointments +
                ", date=" + date +
                '}';
    }
}

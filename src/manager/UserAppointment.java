package manager;

import dashboard.Appointment;
import dashboard.User;

import java.util.GregorianCalendar;

public class UserAppointment extends Appointment {
    private User user;

    public UserAppointment(GregorianCalendar date, int time, int idDoctor, int idSpecialty, int idCity, User user) {
        super(date, time, idDoctor, idSpecialty, idCity);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

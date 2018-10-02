package manager;

import dashboard.Appointment;
import dashboard.Doctor;
import dashboard.User;

import java.util.GregorianCalendar;

public class UserAppointment extends Appointment {
    private User user;

    public UserAppointment(GregorianCalendar date, int time, Doctor doctor, int idSpecialty, int idCity, User user) {
        super(date, time, doctor, idSpecialty, idCity);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

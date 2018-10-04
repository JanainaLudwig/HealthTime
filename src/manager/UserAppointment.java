package manager;

import dashboard.Appointment;
import dashboard.Doctor;
import dashboard.User;

import java.util.GregorianCalendar;

public class UserAppointment extends Appointment {
    private User user;
    private int idAppointment;

    public UserAppointment(GregorianCalendar date, int time, Doctor doctor, int idSpecialty, int idCity, User user, int idAppointment) {
        super(date, time, doctor, idSpecialty, idCity);
        this.user = user;
        this.idAppointment = idAppointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }
}

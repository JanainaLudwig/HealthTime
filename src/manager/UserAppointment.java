package manager;

import DAO.DAOAppointment;
import dashboard.Appointment;
import dashboard.Doctor;
import dashboard.User;
import location.City;
import manager.view.AppointmentManagerController;
import utils.DateUtils;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class UserAppointment extends Appointment {
    private User user;
    private int idAppointment;
    private int idQueueAppointment;

    public UserAppointment(GregorianCalendar date, int time, Doctor doctor, int idSpecialty, int idCity, User user, int idAppointment) {
        super(date, time, doctor, idSpecialty, idCity);
        this.user = user;
        this.idAppointment = idAppointment;
    }

    public UserAppointment(GregorianCalendar date, int time, int idSpecialty, int idCity, User user, int idAppointment, int idQueueAppointment) {
        super(date, time, idSpecialty, idCity);
        this.user = user;
        this.idAppointment = idAppointment;
        this.idQueueAppointment = idQueueAppointment;
    }

    public boolean cancelAppointment() {
        if (DateUtils.isPast(this.date) || DateUtils.isToday(this.date)) {
            return false;
        }

        DAOAppointment daoAppointment = null;

        try {
            daoAppointment = new DAOAppointment();
            daoAppointment.cancelAppointment(this);

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        user.updateUserAppointments(true);

        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdQueueAppointment() { return this.idQueueAppointment; }

    public void setSpecialty(int idSpecialty) { super.idSpecialty = idSpecialty; }

    public int getIdCity() { return idCity; }

    public City getCity() { return super.city; }

    public int getTimeCode() { return super.time.getTimeCode(); }

    public GregorianCalendar getDate() { return super.date; }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }
}

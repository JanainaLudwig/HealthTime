package manager;

import DAO.DAOAppointment;
import dashboard.Appointment;
import dashboard.Doctor;
import dashboard.User;
import utils.DateUtils;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class UserAppointment extends Appointment {
    private User user;
    private int idAppointment;

    public UserAppointment(GregorianCalendar date, int time, Doctor doctor, int idSpecialty, int idCity, User user, int idAppointment) {
        super(date, time, doctor, idSpecialty, idCity);
        this.user = user;
        this.idAppointment = idAppointment;
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

        user.updateUserAppointments();

        return true;
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

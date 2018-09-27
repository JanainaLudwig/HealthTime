package dashboard;

import DAO.DAOAppointment;
import dashboard.appointmentCard.AppointmentCard;

import java.sql.SQLException;

public class Appointment {
    private AppointmentTime time;
    private AppointmentCard card = null;
    private WeekDay day;
    private int idDoctor;
    private int idSpecialty;
    private int idCity;

    public Appointment(WeekDay day, int time, int idDoctor, int idSpecialty, int idCity) {
        this.day = day;
        this.time = new AppointmentTime(time);
        this.idDoctor = idDoctor;
        this.idSpecialty = idSpecialty;
        this.idCity = idCity;
    }

    public AppointmentTime getTime() {
        return time;
    }

    public void setTime(AppointmentTime time) {
        this.time = time;
    }

    public AppointmentCard getCard() {
        if (card == null) {
            card = new AppointmentCard(this);
        }

        return card;
    }

    public void setCard(AppointmentCard card) {
        this.card = card;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdSpecialty() {
        return idSpecialty;
    }

    public void setIdSpecialty(int idSpecialty) {
        this.idSpecialty = idSpecialty;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }
}

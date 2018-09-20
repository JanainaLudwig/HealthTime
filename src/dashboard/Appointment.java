package dashboard;

import DAO.DAOAppointment;
import dashboard.appointmentCard.AppointmentCard;

import java.sql.SQLException;

public class Appointment {
    private AppointmentTime time;
    private AppointmentCard card;
    private WeekDay day;
    private int idDoctor;
    private int idSpecialty;

    public Appointment(WeekDay day, int time, int idDoctor, int idSpecialty) {
        this.day = day;
        this.time = new AppointmentTime(time);
        card = new AppointmentCard(this);
        this.idDoctor = idDoctor;
        this.idSpecialty = idSpecialty;
    }

    public AppointmentTime getTime() {
        return time;
    }

    public void setTime(AppointmentTime time) {
        this.time = time;
    }

    public AppointmentCard getCard() {
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
}

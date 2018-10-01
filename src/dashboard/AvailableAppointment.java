package dashboard;

import DAO.DAOAppointment;
import dashboard.appointmentCard.AppointmentCard;

import java.sql.SQLException;

public class AvailableAppointment extends Appointment{
    private AppointmentCard card = null;
    private WeekDay day;

    public AvailableAppointment(WeekDay day, int time, int idDoctor, int idSpecialty, int idCity) {
        super(day.getDate(), time, idDoctor, idSpecialty, idCity);
        this.day = day;
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
}

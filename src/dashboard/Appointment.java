package dashboard;

import dashboard.appointmentCard.AppointmentCard;

public class Appointment {
    private AppointmentTime time;
    private AppointmentCard card;
    private WeekDay day;
    //TODO: change int to Doctor
    private int id_doctor;

    public Appointment(WeekDay day, int time, int id_doctor) {
        this.day = day;
        this.time = new AppointmentTime(time);
        card = new AppointmentCard();
        this.id_doctor = id_doctor;
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
}

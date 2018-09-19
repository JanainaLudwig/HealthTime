package dashboard;

import dashboard.appointmentCard.AppointmentCard;

public class Appointment {
    private AppointmentTime time;
    private AppointmentCard card;
    private WeekDay day;
    private int id_doctor;
    private int id_specialty;

    public Appointment(WeekDay day, int time, int id_doctor, int id_specialty) {
        this.day = day;
        this.time = new AppointmentTime(time);
        card = new AppointmentCard(this);
        this.id_doctor = id_doctor;
        this.id_specialty = id_specialty;
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

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public int getId_specialty() {
        return id_specialty;
    }

    public void setId_specialty(int id_specialty) {
        this.id_specialty = id_specialty;
    }
}

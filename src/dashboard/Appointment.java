package dashboard;

import dashboard.appointmentCard.AppointmentCard;

public class Appointment {
    private AppointmentTime time;
    private AppointmentCard card;

    public Appointment(AppointmentTime time, AppointmentCard card) {
        this.time = time;
        this.card = card;
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
}

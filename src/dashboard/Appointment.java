package dashboard;

import java.util.GregorianCalendar;

public class Appointment {
    protected AppointmentTime time;
    protected int idDoctor;
    protected int idSpecialty;
    protected int idCity;
    protected GregorianCalendar date;

    public Appointment(GregorianCalendar date, int time, int idDoctor, int idSpecialty, int idCity) {
        this.date = date;
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

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}

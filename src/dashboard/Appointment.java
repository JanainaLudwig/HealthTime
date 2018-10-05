package dashboard;

import java.util.GregorianCalendar;

public class Appointment {
    protected AppointmentTime time;
    protected Doctor doctor;
    protected int idSpecialty;
    protected Specialty specialty;
    protected int idCity;
    protected GregorianCalendar date;

    public Appointment(GregorianCalendar date, int time, Doctor doctor, int idSpecialty, int idCity) {
        this.date = date;
        this.time = new AppointmentTime(time);
        this.doctor = doctor;
        this.idSpecialty = idSpecialty;
        specialty = new Specialty(idSpecialty);
        this.idCity = idCity;
    }

    public AppointmentTime getTime() {
        return time;
    }

    public void setTime(AppointmentTime time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
}

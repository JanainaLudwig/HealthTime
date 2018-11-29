package queue;

public class AppointmentQueue {
    private int idQueue, idConsultant, idSpecialty;

    public AppointmentQueue(int idQueue, int idConsultant, int idSpecialty) {
        this.idQueue = idQueue;
        this.idConsultant = idConsultant;
        this.idSpecialty = idSpecialty;
    }

    public int getIdQueue() {
        return idQueue;
    }

    public int getIdConsultant() {
        return idConsultant;
    }

    public int getIdSpecialty() {
        return idSpecialty;
    }
}

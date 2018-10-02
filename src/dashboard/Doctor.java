package dashboard;

import DAO.DAODoctor;
import DAO.DAOUser;

import java.sql.SQLException;

public class Doctor {
    private int doctorId;
    private String doctorName;

    public Doctor(int doctorId, String doctorName) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
    }

    public Doctor(int doctorId) {
        this.doctorId = doctorId;

        try {
            DAODoctor daoDoctor = new DAODoctor();
            this.doctorName = daoDoctor.getDoctorName(doctorId);

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }
}

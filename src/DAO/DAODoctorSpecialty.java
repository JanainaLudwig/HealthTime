package DAO;

import dashboard.Doctor;
import dashboard.Specialty;
import database.ConnectionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAODoctorSpecialty {
    private Connection connection;
    private int idSpecialty;
    private int idDoctor;

    public DAODoctorSpecialty() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }

    public ArrayList<Specialty> getAllDescription(int userId) throws SQLException {
        String query = "SELECT s.id_specialty, s.description " +
                "FROM specialty AS s JOIN appointment_release AS ar ON s.id_specialty = ar.id_specialty " +
                "WHERE ar.id_patient = '" + userId + "' " +
                "AND id_appointment IS NULL ORDER BY s.description";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Specialty> specialtyList = new ArrayList<>();
        specialtyList.add(new Specialty(1, "Clínica Geral"));

        while (rs.next()) {
            specialtyList.add(new Specialty(rs.getInt("id_specialty"), rs.getString("description")));
        }
        return specialtyList;
    }

    public ArrayList<Doctor> getDoctor(int idSpecialty, int idCity) throws SQLException {
        String query = "SELECT DISTINCT u.id_user, u.name " +
                "FROM users u JOIN doctor_specialty ds ON u.id_user = ds.id_doctor " +
                "JOIN specialty s ON ds.id_specialty = s.id_specialty " +
                "JOIN working_time AS wt ON wt.id_doctor = u.id_user " +
                "WHERE s.id_specialty = '" + idSpecialty + "' " +
                "AND wt.id_city = '" + idCity + "' " +
                "ORDER BY u.name;";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Doctor> doctorList = new ArrayList<>();

        doctorList.add(new Doctor(0, "Todos"));

        while (rs.next()) {
            Doctor doctor = new Doctor(rs.getInt("id_user"), rs.getString("name"));
            doctorList.add(doctor);
        }
        return doctorList;
    }

    public String getSpecialty(int id_specialty) throws SQLException {
        String query = "SELECT description FROM specialty WHERE id_specialty = " + id_specialty;

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return (rs.next()) ? rs.getString("description") : null;
    }
}

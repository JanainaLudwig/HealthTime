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
                "FROM specialty s JOIN appointment_release ar ON s.id_specialty = ar.id_specialty " +
                "WHERE ar.id_patient = '" + userId + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Specialty> specialtyList = new ArrayList<>();

        while (rs.next()) {
            Specialty specialty = new Specialty(rs.getInt("id_specialty"), rs.getString("description"));
            specialtyList.add(specialty);
        }
        return specialtyList;
    }

    public ArrayList<Doctor> getDoctor(String code) throws SQLException {
        String query = "SELECT u.id_user, u.name " +
                "FROM users u JOIN doctor_specialty ds ON u.id_user = ds.id_doctor " +
                "JOIN specialty s ON ds.id_specialty = s.id_specialty " +
                "WHERE s.description = '" + code + "'";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Doctor> doctorList = new ArrayList<>();

        Doctor todos = new Doctor(0, "Todos");
        doctorList.add(todos);
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

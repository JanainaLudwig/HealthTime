package DAO;

import dashboard.Doctor;
import dashboard.Specialty;
import database.ConnectionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

    public ArrayList<Specialty> getAllDescriptionFilter(int userId, LocalDate initialDate, LocalDate finalDate) throws SQLException {
        String query = "SELECT DISTINCT s.id_specialty, s.description " +
                "FROM specialty AS s JOIN appointment AS a ON s.id_specialty = a.id_specialty " +
                "WHERE a.id_consultant = '" + userId + "' AND " +
                "a.appointment_date BETWEEN '" + initialDate +"' AND '" + finalDate +"' " +
                "ORDER BY s.description";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Specialty> specialtyList = new ArrayList<>();

        specialtyList.add(new Specialty(0, "Todas"));

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

    public ArrayList<Doctor> getDoctorFilter(int idSpecialty, int userId, LocalDate initialDate, LocalDate finalDate) throws SQLException {
        String query;
        if (idSpecialty == 0) {
            query = "SELECT DISTINCT u.id_user, u.name " +
                    "FROM users u JOIN doctor d ON u.id_user = d.id_user " +
                    "JOIN appointment AS a ON a.id_doctor = u.id_user " +
                    "WHERE a.id_consultant = '" + userId + "' AND " +
                    "a.appointment_date BETWEEN '" + initialDate +"' AND '" + finalDate +"' " +
                    "ORDER BY u.name;";

        } else {
            query = "SELECT DISTINCT u.id_user, u.name " +
                    "FROM users u JOIN doctor d ON u.id_user = d.id_user " +
                    "JOIN appointment AS a ON a.id_doctor = u.id_user " +
                    "WHERE a.id_specialty = '" + idSpecialty + "' " +
                    "AND a.id_consultant = '" + userId + "' AND " +
                    "a.appointment_date BETWEEN '" + initialDate +"' AND '" + finalDate +"' " +
                    "ORDER BY u.name;";
        }

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

    public String getDoctorName(int idDoctor) throws SQLException {
        String query = "SELECT u.name FROM users AS u JOIN doctor AS d ON u.id_user = d.id_user " +
                "WHERE u.id_user = " + idDoctor + ";";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return (rs.next()) ? rs.getString("name") : null;
    }
}

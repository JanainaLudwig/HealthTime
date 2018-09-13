package DAO;

import dashboard.Appointment;
import dashboard.WeekDay;
import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOAppointment {
    private Connection connection;

    public DAOAppointment() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }


    public ArrayList<Appointment> getAvailableAppointments(int id_specialty, int id_city, WeekDay date) throws SQLException {

        System.out.println(date.getDateString());

        String query = "SELECT * FROM available_appointments(" + id_city + ", '" + date.getDateString() + "', " + id_specialty + ");";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            appointments.add(new Appointment(date, rs.getInt("appointment_time"), rs.getInt("id_doctor")));
        }

        return appointments;
    }
/*
    public int getIdSpecialty(String code) throws SQLException {
        String query = "SELECT id_specialty FROM doctor_specialty WHERE id_doctor = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            this.idSpecialty = rs.getInt("id_specialty");

            return this.idSpecialty;
        } else {
            return -1;
        }
    }

    public String getDescription(int code) throws SQLException {
        String query = "SELECT description FROM specialty WHERE id_specialty='" + code + "';";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return  rs.getString("description");
        } else {
            return null;
        }
    }

    //Talvez em novo DAO
    public int getIdDoctor(String code) throws SQLException {
        String query = "SELECT id_doctor FROM doctor_specialty WHERE id_specialty = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            this.idDoctor = rs.getInt("id_doctor");

            return this.idDoctor;
        } else {
            return -1;
        }
    }
    */
}

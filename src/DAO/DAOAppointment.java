package DAO;

import dashboard.Appointment;
import dashboard.WeekDay;
import database.ConnectionDB;
import utils.DateUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DAOAppointment {
    private Connection connection;

    public DAOAppointment() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }


    public ArrayList<Appointment> getAvailableAppointments(int id_specialty, int id_city, WeekDay weekDay, int id_doctor) throws SQLException {
        String query = "SELECT * FROM available_appointments(" + id_city + ", '" + DateUtils.getDateString(weekDay.getDate()) + "', " + id_specialty + ")";
        if (id_doctor != 0) {
            query += " WHERE id_doctor = " + id_doctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            appointments.add(new Appointment(weekDay, rs.getInt("appointment_time"), rs.getInt("id_doctor"), id_specialty));
        }

        return appointments;
    }

    public boolean hasAny(int id_specialty, int id_city, GregorianCalendar date, int id_doctor) throws SQLException {
        String query = "SELECT * FROM available_appointments(" + id_city + ", '" + DateUtils.getDateString(date) + "', " + id_specialty + ")";
        if (id_doctor != 0) {
            query += " WHERE id_doctor = " + id_doctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return rs.next();
    }
}

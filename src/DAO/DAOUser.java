package DAO;

import dashboard.Doctor;
import dashboard.User;
import manager.UserAppointment;
import database.ConnectionDB;
import utils.DateUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DAOUser {
    private Connection connection;
    private User user;

    public DAOUser(User user) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
        this.user = user;
    }

    public String getName() throws SQLException {
        String query = "SELECT name FROM  users WHERE id_user='" + user.getUserId() + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return  rs.getString("name");
        } else {
            return null;
        }
    }

    public ArrayList<UserAppointment> getAppointments() throws SQLException {
        String query = "SELECT * FROM  appointment WHERE id_consultant='" + user.getUserId() + "' ORDER BY (appointment_date, appointment_time) DESC";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<UserAppointment> appointments = new ArrayList();
        while (rs.next()) {
            GregorianCalendar date = DateUtils.stringToGregorianCalendar(rs.getString("appointment_date"));
            Doctor doctor = new Doctor(rs.getInt("id_doctor"));

            appointments.add(new UserAppointment(date, rs.getInt("appointment_time"), doctor,
                                                    rs.getInt("id_specialty"), rs.getInt("id_city"), this.user, rs.getInt("id_appointment")));
        }

        return appointments;
    }

    public ArrayList<UserAppointment> getNextAppointments() throws SQLException {
        String query = "SELECT * FROM  appointment " +
                "WHERE id_consultant='" + user.getUserId() + "'" +
                " AND appointment_date >= current_date" +
                " ORDER BY (appointment_date, appointment_time) ASC LIMIT 2";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<UserAppointment> appointments = new ArrayList();
        while (rs.next()) {
            GregorianCalendar date = DateUtils.stringToGregorianCalendar(rs.getString("appointment_date"));
            Doctor doctor = new Doctor(rs.getInt("id_doctor"));

            appointments.add(new UserAppointment(date, rs.getInt("appointment_time"), doctor,
                    rs.getInt("id_specialty"), rs.getInt("id_city"), this.user, rs.getInt("id_appointment")));
        }

        return appointments;
    }

    public int getIdCity() throws SQLException {
        String query = "SELECT id_city FROM consultant WHERE id_user='" + user.getUserId() + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return (rs.next()) ? rs.getInt("id_city") : 0;
    }
}

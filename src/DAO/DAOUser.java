package DAO;

import dashboard.Doctor;
import dashboard.User;
import login.Hash;
import manager.UserAppointment;
import database.ConnectionDB;
import utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

    public DAOUser() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
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

    //Password recovery

    public String getMotherName(String code) throws SQLException {
        String query = "SELECT mother_name FROM users WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("mother_name");
        } else {
            return null;
        }
    }

    public String getCpf(String code) throws SQLException {
        String query = "SELECT cpf FROM users WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("cpf");
        } else {
            return null;
        }
    }

    public void setNewPassword(int code, String password) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String query = "UPDATE login SET password = '" + Hash.sha256(password) +"' WHERE id_user = '" + code + "';";
        Statement stm = connection.createStatement();
        stm.executeUpdate(query);
    }

    public int getIdUser(String code) throws SQLException {
        String query = "SELECT id_user FROM users WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getInt("id_user");
        } else {
            return -1;
        }
    }
}

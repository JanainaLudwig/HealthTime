package DAO;

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
    private int idUser;

    public DAOUser(int idUser) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
        this.idUser = idUser;
    }

    public String getName() throws SQLException {
        String query = "SELECT name FROM  users WHERE id_user='" + this.idUser + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return  rs.getString("name");
        } else {
            return null;
        }
    }

    public ArrayList<UserAppointment> getAppointments() throws SQLException {
        String query = "SELECT * FROM  appointment WHERE id_consultant='" + this.idUser + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<UserAppointment> appointments = new ArrayList();
        while (rs.next()) {
            GregorianCalendar date = DateUtils.stringToGregorianCalendar(rs.getString("appointment_date"));
            //TODO: Instanciar construtor do UserAppointment
            //appointments.add(new UserAppointment());
        }

        return appointments;
    }
}

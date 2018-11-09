package DAO;

import dashboard.AvailableAppointment;
import dashboard.Doctor;
import dashboard.User;
import dashboard.WeekDay;
import dashboard.view.DashboardController;
import database.ConnectionDB;
import manager.UserAppointment;
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


    public ArrayList<AvailableAppointment> getAvailableAppointments(int idSpecialty, int idCity, WeekDay weekDay, int idDoctor) throws SQLException {
        int idUser = weekDay.getUser().getUserId();

        String query = "SELECT * FROM available_appointments(" + idUser + ", " + idCity + ", '" + DateUtils.getDateString(weekDay.getDate()) + "', " + idSpecialty + ")";
        if (idDoctor != 0) {
            query += " WHERE id_doctor = " + idDoctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<AvailableAppointment> availableAppointments = new ArrayList<>();
        while (rs.next()) {
            Doctor doctor = new Doctor(rs.getInt("id_doctor"));
            availableAppointments.add(new AvailableAppointment(weekDay, rs.getInt("appointment_time"), doctor, idSpecialty, idCity));
        }

        return availableAppointments;
    }

    public boolean hasAny(int id_specialty, int id_city, GregorianCalendar date, int id_doctor, int idUser) throws SQLException {
        String query = "SELECT * FROM available_appointments(" + idUser + ", " + id_city + ", '" + DateUtils.getDateString(date) + "', " + id_specialty + ")";
        if (id_doctor != 0) {
            query += " WHERE id_doctor = " + id_doctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return rs.next();
    }

    public String minDate() throws SQLException {
        String query = "SELECT MIN(appointment_date) FROM appointment";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        String min = "";
        while (rs.next()) {
            min = rs.getString("min");
        }
        return min;
    }

    public String maxDate() throws SQLException {
        String query = "SELECT MAX(appointment_date) FROM appointment";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        String max = "";
        while (rs.next()) {
            max = rs.getString("max");
        }
        return max;
    }


    public void scheduleAppointment(AvailableAppointment availableAppointment) throws SQLException {
        int idDoctor = availableAppointment.getDoctor().getDoctorId(),
            idConsultant = availableAppointment.getDay().getUser().getUserId(),
            idSpecialty = availableAppointment.getIdSpecialty(),
            appointmentTime = availableAppointment.getTime().getTimeCode(),
            idCity = availableAppointment.getDay().getUser().getCity().getId();

        String appointmentDate = DateUtils.getDateString(availableAppointment.getDay().getDate());

        String query = "INSERT INTO appointment (id_doctor, id_consultant, \n" +
                "                         id_specialty, id_city, appointment_date, \n" +
                "                         appointment_time) VALUES \n" +
                "(" + idDoctor + ", " +
                "" + idConsultant + ", " +
                "" + idSpecialty + ", " +
                ""+ idCity + ", " +
                "'" + appointmentDate + "', " +
                "" + appointmentTime + ");";

        Statement stm = connection.createStatement();
        stm.execute(query);
    }

    public void cancelAppointment(UserAppointment userAppointment) throws SQLException {
        String query = "DELETE FROM appointment WHERE id_appointment = " + userAppointment.getIdAppointment() + ";";

        Statement stm = connection.createStatement();
        stm.execute(query);
    }
}

package DAO;

import dashboard.AvailableAppointment;
import dashboard.Doctor;
import dashboard.User;
import dashboard.WeekDay;
import dashboard.view.DashboardController;
import database.ConnectionDB;
import login.Hash;
import manager.UserAppointment;
import queue.modal.AppointmentQueue;
import utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Queue;

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

    public void scheduleAppointment(AvailableAppointment availableAppointment) throws SQLException {
        int idDoctor = availableAppointment.getDoctor().getDoctorId(),
            idConsultant = availableAppointment.getDay().getUser().getUserId(),
            idSpecialty = availableAppointment.getIdSpecialty(),
            appointmentTime = availableAppointment.getTime().getTimeCode(),
            idCity = availableAppointment.getCity().getId();

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

    public void addAppointmentQueue(int idUser, int idSpecialty, int idCity, LocalDate date, int time) throws SQLException {
        String query = "INSERT INTO appointment_queue (id_consultant, id_specialty, id_city, appointment_date, appointment_time) VALUES" +
                "(" + idUser + ", " +
                "" + idSpecialty + ", " +
                ""+ idCity + ", " +
                "'" + date + "', " +
                "" + time + ");";

        Statement stm = connection.createStatement();
        stm.execute(query);
    }

    public AppointmentQueue findSpecificSpecialty(int idCity, int idDoctor, GregorianCalendar date, int time) throws SQLException {
        String query = "SELECT MIN(aq.id_appointment_queue) AS id, aq.id_specialty, aq.id_consultant FROM appointment_queue aq " +
                "WHERE aq.appointment_date = '" + DateUtils.getDateString(date) + "' " +
                "AND aq.id_city = '" + idCity + "' " +
                "AND aq.appointment_time = '" + time + "' " +
                "AND aq.id_specialty IN (" +
                    "SELECT s.id_specialty " +
                    "FROM specialty s JOIN doctor_specialty ds ON s.id_specialty = ds.id_specialty " +
                    "WHERE ds.id_doctor = '" + idDoctor + "' AND ds.id_specialty != 1" +
                ") " +
                "GROUP BY aq.id_appointment_queue, aq.id_specialty " +
                "ORDER BY aq.id_appointment_queue ASC;";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            AppointmentQueue newConsultant = new AppointmentQueue(rs.getInt("id"), rs.getInt("id_consultant"), rs.getInt("id_specialty"));
            return newConsultant;
        } else {
            return null;
        }
    }

    public AppointmentQueue findClinic(int idCity, int idDoctor, GregorianCalendar date, int time) throws SQLException {
        String query = "SELECT MIN(aq.id_appointment_queue) AS id, aq.id_specialty, aq.id_consultant FROM appointment_queue aq " +
                "WHERE aq.appointment_date = '" + DateUtils.getDateString(date) + "' " +
                "AND aq.id_city = '" + idCity + "' " +
                "AND aq.appointment_time = '" + time + "' " +
                "AND aq.id_specialty IN (" +
                "SELECT s.id_specialty " +
                "FROM specialty s JOIN doctor_specialty ds ON s.id_specialty = ds.id_specialty " +
                "WHERE ds.id_doctor = '" + idDoctor + "' " +
                ") " +
                "GROUP BY aq.id_appointment_queue, aq.id_specialty " +
                "ORDER BY aq.id_appointment_queue ASC;";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            AppointmentQueue newConsultant = new AppointmentQueue(rs.getInt("id"), rs.getInt("id_consultant"), rs.getInt("id_specialty"));
            return newConsultant;
        } else {
            return null;
        }
    }

    public boolean hasAppointmentPeriod(int id_specialty, int id_city, int period, GregorianCalendar date, int id_doctor, int idUser) throws SQLException {
        String query = "SELECT * FROM available_appointments_queue(" + idUser + ", " + id_city + ", " + period + ", '" + DateUtils.getDateString(date) + "', " + id_specialty + ")";
        if (id_doctor != 0) {
            query += " WHERE id_doctor = " + id_doctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return rs.next();
    }

    public void cancelAppointmentQueue(int id) throws SQLException {
        String query = "DELETE FROM appointment_queue WHERE id_appointment_queue = " + id + ";";

        Statement stm = connection.createStatement();
        stm.execute(query);
    }

    public void updateAppointment(int idAppointment ,int idConsultant, int idSpecialty) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String query = "UPDATE appointment SET id_consultant = '" + idConsultant +"', id_specialty = '" + idSpecialty +"' WHERE id_appointment = '" + idAppointment + "'";
        Statement stm = connection.createStatement();
        stm.executeUpdate(query);
    }

    public boolean deleteOldQueue(GregorianCalendar date) throws SQLException {
        String query = "SELECT delete_old_queue('" + DateUtils.getDateString(date) + "')";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        return rs.next();
    }
}

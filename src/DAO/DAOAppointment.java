package DAO;

import dashboard.Appointment;
import dashboard.WeekDay;
import dashboard.view.DashboardController;
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


    public ArrayList<Appointment> getAvailableAppointments(int idSpecialty, int idCity, WeekDay weekDay, int idDoctor) throws SQLException {
        int idUser = weekDay.getUser().getUserId();
        String query = "SELECT * FROM available_appointments(" + idCity + ", '" + DateUtils.getDateString(weekDay.getDate()) + "', " + idSpecialty + ")";
        if (idDoctor != 0) {
            query += " WHERE id_doctor = " + idDoctor + ";";
        } else {
            query += ";";
        }

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        ArrayList<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {

            appointments.add(new Appointment(weekDay, rs.getInt("appointment_time"), rs.getInt("id_doctor"), idSpecialty));
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

    public void scheduleAppointment(Appointment appointment) throws SQLException {
        int idDoctor = appointment.getIdDoctor(),
            idConsultant = appointment.getDay().getUser().getUserId(),
            idSpecialty = appointment.getIdSpecialty(),
            appointmentTime = appointment.getTime().getTimeCode(),
            idCity = DashboardController.selectedCity;

        String appointmentDate = DateUtils.getDateString(appointment.getDay().getDate());

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
}

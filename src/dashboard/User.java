package dashboard;

import DAO.DAOUser;
import manager.UserAppointment;

import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private ArrayList<UserAppointment> userAppointments;

    public User(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;

        DAOUser dao = new DAOUser(this);
        this.userName = dao.getName();
        this.userAppointments = dao.getAppointments();
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<UserAppointment> getUserAppointments() {
        return userAppointments;
    }

    public void setUserAppointments(ArrayList<UserAppointment> userAppointments) {
        this.userAppointments = userAppointments;
    }

    public void updateUserAppointments() {
        try {
            DAOUser dao = new DAOUser(this);
            this.userAppointments = dao.getAppointments();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

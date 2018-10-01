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
        DAOUser dao = new DAOUser(userId);
        this.userName = dao.getName();
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
}

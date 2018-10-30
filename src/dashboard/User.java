package dashboard;

import DAO.DAOUser;
import manager.UserAppointment;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private int idCity;
    private ArrayList<UserAppointment> userAppointments;

    public User(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;

        DAOUser dao = new DAOUser(this);
        this.userName = dao.getName();
        this.userAppointments = null;
        this.idCity = dao.getIdCity();
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
        System.out.println("get");
        if (userAppointments == null) {
            System.out.println("NULL");
            this.updateUserAppointments();
        }
        return userAppointments;
    }

    public void setUserAppointments(ArrayList<UserAppointment> userAppointments) {
        this.userAppointments = userAppointments;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
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

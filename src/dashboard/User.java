package dashboard;

import DAO.DAOUser;
import location.City;
import manager.UserAppointment;
import utils.LocationUtils;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private ArrayList<UserAppointment> userAppointments;
    private City city;
    public boolean checkbox = true;

    public User(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;

        DAOUser dao = new DAOUser(this);
        this.userName = dao.getName();
        this.userAppointments = null;
        this.city = LocationUtils.getCity(String.valueOf(dao.getIdCity()));
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public void setCheckboxFalse() { this.checkbox = false; }

    public void setCheckboxTrue() { this.checkbox = true; }

    public ArrayList<UserAppointment> getUserAppointments() {
        if (userAppointments == null) {
            this.updateUserAppointments();
        }
        return userAppointments;
    }

    public void setUserAppointments(ArrayList<UserAppointment> userAppointments) {
        this.userAppointments = userAppointments;
    }

    public void updateUserAppointments() {
        try {
            DAOUser dao = new DAOUser(this);
            if (checkbox) {
                this.userAppointments = dao.getQueueAppointments();
                this.userAppointments.addAll(dao.getAppointments());
            } else {
                this.userAppointments = dao.getAppointments();
            }

        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

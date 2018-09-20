package dashboard;

import DAO.DAOUser;

import java.sql.SQLException;

public class User {
    private int userId;
    private String userName;

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

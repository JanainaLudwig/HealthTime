package login;

import java.sql.SQLException;

public interface IUserLogin {
    boolean login() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;
}

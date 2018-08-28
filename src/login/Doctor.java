
package login;


import DAO.DAOLogin;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;

public class Doctor extends User {

    
    public Doctor(String userCode, String password, char userType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(userCode, password, userType);
    }

    @Override
    public boolean login() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        DAOLogin dao = new DAOLogin(this.getUserType());

        int id = dao.getIdUser(this.userCode);
        if (id > 0) {
            this.userId = id;
        } else {
            return false;
        }

        String password = dao.getPassword();
        if (password == null) {
            return false;
        }

        if (password.equals(this.getHashedPassword())) {
            return true;
        } else {
            return false;
        }
    }
}

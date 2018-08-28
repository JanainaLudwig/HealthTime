
package login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class User {
    protected String userCode;
    protected int userId;
    private String password;
    private char userType;
    /*
     * 'D' for doctor
     * 'C' for consultant
     */

    public User(String userCode, String password, char userType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.userCode = userCode;
        this.password = password;
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return Hash.sha256(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public boolean login() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        Boolean loginValidation = false;
        switch (this.userType) {
            case 'D':
                Doctor doctor = new Doctor(this.userCode, this.password, this.userType);
                loginValidation = doctor.login();
                break;
            case 'C':
                Consultant consultant = new Consultant(this.userCode, this.password, this.userType);
                loginValidation = consultant.login();
                break;
        }
        return loginValidation;
    }
}

package login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class UserLogin {
    protected String userCode;
    protected int userId;
    private String password;
    private String hashedPassword;

    public UserLogin(String userCode, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.userCode = userCode;
        this.password = password;
        this.hashedPassword = Hash.sha256(password);
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() { return hashedPassword; }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }
}

package DAO;

import database.ConnectionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAODoctorLogin {
    private Connection connection;
    private int idUser;

    public DAODoctorLogin() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }

    public int getIdUser(String code) throws SQLException {
        String query = "SELECT id_user FROM doctor WHERE crm_number = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            this.idUser = rs.getInt("id_user");

            return this.idUser;
        } else {
            return -1;
        }
    }

    public String getPassword() throws SQLException {
        String query = "SELECT password FROM login WHERE id_user = " + this.idUser + ";";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("password");
        } else {
            return null;
        }
    }
}

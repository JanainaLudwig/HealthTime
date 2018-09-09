package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUser {
    private Connection connection;
    private int idUser;

    public DAOUser(int idUser) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
        this.idUser = idUser;
    }

    public String getName() throws SQLException {
        String query = "SELECT name FROM  users WHERE id_user='" + this.idUser + "'";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return  rs.getString("name");
        } else {
            return null;
        }
    }
}

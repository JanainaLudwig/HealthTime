package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOLogin {
    private Connection connection;
    private int idUser;
    private String table;
    private String codeColumn;

    public DAOLogin(char type) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
        switch (type) {
            case 'D':
                this.table = "doctor";
                this.codeColumn = "crm_number";
                break;
            case 'C':
                this.table = "consultant";
                this.codeColumn = "sus_number";
                break;
        }
    }

    public int getIdUser(String code) throws SQLException {
        String query = "SELECT id_user FROM " + table + " WHERE " + codeColumn + "='" + code + "'";

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
        String query = "SELECT password FROM login WHERE id_user=" + this.idUser;

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("password");
        } else {
            return null;
        }
    }
}

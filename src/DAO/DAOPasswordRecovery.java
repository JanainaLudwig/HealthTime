package DAO;

import dashboard.Doctor;
import dashboard.Specialty;
import database.ConnectionDB;
import login.Hash;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOPasswordRecovery {
    private Connection connection;

    public DAOPasswordRecovery() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }

    public String getMotherName(String code) throws SQLException {
        String query = "SELECT mother_name FROM password_recovery WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("mother_name");
        } else {
            return null;
        }
    }

    public String getCpf(String code) throws SQLException {
        String query = "SELECT cpf FROM password_recovery WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("cpf");
        } else {
            return null;
        }
    }

    public void setNewPassword(int code, String password) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String query = "UPDATE login SET password = '" + Hash.sha256(password) +"' WHERE id_user = '" + code + "';";
        Statement stm = connection.createStatement();
        stm.executeUpdate(query);
    }

    public int getIdUser(String code) throws SQLException {
        String query = "SELECT id_user FROM password_recovery WHERE cpf = '" + code + "';";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getInt("id_user");
        } else {
            return -1;
        }
    }
}
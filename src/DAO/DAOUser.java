package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOUser {
    private Connection connection;
    private int idUser;

    public DAOUser(int idUser) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
        this.idUser = idUser;
    }
}

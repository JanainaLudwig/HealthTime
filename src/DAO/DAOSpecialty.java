package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOSpecialty {
    private Connection connection;


    public DAOSpecialty() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        connection = ConnectionDB.getConnection();
    }

    public String getDescription(int idSpecialty) throws SQLException {
        String query = "SELECT description FROM specialty WHERE id_specialty = " + idSpecialty + ";";

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return rs.getString("description");
        }

        return null;
    }
}

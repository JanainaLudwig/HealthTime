package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOStation {
    private Connection connection;


    public DAOStation() {
        try {
            connection = ConnectionDB.getConnection();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getStations(int idCity)  {
        String query = "SELECT station FROM cities_stations WHERE id_city ="  + idCity + ";";

        Statement stm = null;
        try {
            stm = connection.createStatement();

            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                return rs.getString("station");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

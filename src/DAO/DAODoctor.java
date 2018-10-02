package DAO;

import database.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAODoctor {
        private Connection connection;

        public DAODoctor() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
            connection = ConnectionDB.getConnection();
        }


        public String getDoctorName(int idDoctor) throws SQLException {
            String query = "SELECT u.name FROM users AS u JOIN doctor AS d USING (id_user) WHERE u.id_user = " + idDoctor + ";";

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);

            return (rs.next()) ? rs.getString("name") : null;
        }
}

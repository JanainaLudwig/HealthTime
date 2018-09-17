
package dashboard.view;

import DAO.DAODoctorSpecialty;
import com.jfoenix.controls.JFXComboBox;
import dashboard.Doctor;
import dashboard.Specialty;
import dashboard.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    protected final String BLUE = "#168EE9";
    protected final String GRAY = "#394e5e";
    protected int userId;
    protected User user;
    protected static final String[] MONTH_NAME = {
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
    };

    @FXML
    private Text userName;
    @FXML
    private JFXComboBox specialtyComboBox;
    @FXML
    private JFXComboBox doctorComboBox;

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) userName.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    public void specialtyCombo() throws ClassNotFoundException, NullPointerException, FileNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        ArrayList<Specialty> specialtyList = new ArrayList<>();
        specialtyList = dao.getAllDescription();

        for (int i = 0; i < specialtyList.size(); i++) {
            this.specialtyComboBox.getItems().add(specialtyList.get(i).getDescription());
        }
        this.specialtyComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void doctorCombo() throws ClassNotFoundException, NullPointerException, FileNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DAODoctorSpecialty dao = new DAODoctorSpecialty();
        ArrayList<Doctor> doctorList = new ArrayList<>();

        doctorList = dao.getDoctor((String) specialtyComboBox.getValue());
        this.doctorComboBox.getItems().clear();
        this.doctorComboBox.getItems().add("Todos");

        for (int i = 0; i < doctorList.size(); i++) {
            this.doctorComboBox.getItems().add(doctorList.get(i).getDoctorName());
        }

        this.doctorComboBox.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         * Get the username
         * with two first names
         */
        String name;
        String[] nameSplitted = user.getUserName().split(" ");

        if (nameSplitted.length > 1) {
            name = nameSplitted[0] + " " + nameSplitted[1];
        } else {
            name = nameSplitted[0];
        }

        userName.setText(name);

        /*
         * Specialty ComboBox
         */
        try {
            specialtyCombo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
         * Doctor ComboBox
         */
        try {
            doctorCombo();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch ( NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public DashboardController(int userId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        this.userId = userId;
        this.user = new User(userId);
    }

}

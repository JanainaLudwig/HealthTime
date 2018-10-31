package login.view;

import DAO.DAOPasswordRecovery;
import dashboard.view.DashboardMonthController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import utils.ControllerUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    private int idUser;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label errorLabel;


    @FXML
    private void changePassword(ActionEvent event) throws NullPointerException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
        String INCOMPLETE = "Preencha todos os campos.";
        String INVALID = "As senhas não conferem.";

        errorLabel.setText("");

        String newPass = newPassword.getText(),
                confirmPass = confirmPassword.getText();

        if (newPass.equals("") || confirmPass.equals("")) {
            errorLabel.setText(INCOMPLETE);
            return;
        }

        boolean changeValidate;

        if (newPass.equals(confirmPass)) {
            changeValidate = true;
        } else {
            errorLabel.setText(INVALID);
            return;
        }

        if(changeValidate) {
            DAOPasswordRecovery dao = new DAOPasswordRecovery();
            dao.setNewPassword(idUser, newPass);
            closeRecovery();
        } else {
            return;
        }
    }

    @FXML
    private void closeRecovery() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/view/Login.fxml"));
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public ChangePasswordController(int idUser) {
        this.idUser = idUser;
    }
}

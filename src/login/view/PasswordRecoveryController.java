package login.view;

import DAO.DAOPasswordRecovery;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.ControllerUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PasswordRecoveryController implements Initializable {
    private int id;
    protected int attempts = 0;
//    protected int delay = 30000;
    protected int delay = 2000;
    @FXML
    private Button back;
    @FXML
    private Button confirm;
    @FXML
    private TextField cpf;
    @FXML
    private TextField motherName;
    @FXML
    private Label errorLabel;


    @FXML
    private void recovery(ActionEvent event) throws IllegalAccessException, IOException, InstantiationException, SQLException, NoSuchAlgorithmException, ClassNotFoundException, InterruptedException {
        String INCOMPLETE = "Preencha todos os campos.";
        String INVALID_CPF = "CPF não foi encontrado.";
        String INVALID_MOTHERNAME = "Nome da mãe incorreto.";
        String DISABLE = "Aguarde " + delay/1000 + " segundos";

        errorLabel.setText("");

        if (attempts >= 2) {
            errorLabel.setText(DISABLE);
            confirm.setDisable(true);
            back.setDisable(true);
            cpf.setDisable(true);
            motherName.setDisable(true);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        attempts = 0;
                        confirm.setDisable(false);
                        back.setDisable(false);
                        cpf.setDisable(false);
                        motherName.setDisable(false);
                        errorLabel.setText("");
                        cpf.setText("");
                        motherName.setText("");
                    });
                }
            }, delay);

//            if(delay < 600000) {
//                delay += 30000;
//            }
            if(delay < 10000) {
                delay += 2000;
            }
            return;
        }

        String cpfCode = cpf.getText(),
                name = motherName.getText();

        if (cpfCode.equals("") || name.equals("")) {
            errorLabel.setText(INCOMPLETE);
            attempts++;
            return;
        }

        DAOPasswordRecovery dao = new DAOPasswordRecovery();

        String bdCpf = dao.getCpf(cpf.getText());

        if(bdCpf == null){
            errorLabel.setText(INVALID_CPF);
            attempts++;
            return;
        }

        String bdMotherName = dao.getMotherName(cpf.getText());

        if (name.equals(bdMotherName)) {
            id = dao.getIdUser(cpfCode);
        } else {
            errorLabel.setText(INVALID_MOTHERNAME);
            attempts++;
            return;
        }

        ChangePasswordController controller = new ChangePasswordController(id);
        ControllerUtils.changeScene(controller, event, "ChangePassword.fxml");
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
}

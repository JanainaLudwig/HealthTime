package login.view;

import DAO.DAOUser;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    protected int duration = 2000;
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
        String CPF_INCOMPLETE = "Informe seu CPF.";
        String MOTHERNAME_INCOMPLETE = "Informe o nome da mãe.";
        String INVALID_CPF = "CPF não foi encontrado.";
        String INVALID_MOTHERNAME = "Nome da mãe incorreto.";

        errorLabel.setText("");
        duration = delay;

        if (attempts >= 2) {
            disableComponents();

            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, arg0 -> {
                updateTimer(duration);
                duration -= 1000;
            }), new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        enableComponents();
                        timeline.stop();
                        timer.cancel();
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

        if (cpfCode.equals("")) {
            errorLabel.setText(CPF_INCOMPLETE);
            return;
        }

        if (name.equals("")) {
            errorLabel.setText(MOTHERNAME_INCOMPLETE);
            return;
        }

        DAOUser dao = new DAOUser();

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

    private void updateTimer(int duration) {
        errorLabel.setText("Aguarde " + duration/1000 + " segundos");
    }

    private void disableComponents() {
        confirm.setDisable(true);
        back.setDisable(true);
        cpf.setDisable(true);
        motherName.setDisable(true);
    }

    private void enableComponents() {
        attempts = 0;
        confirm.setDisable(false);
        back.setDisable(false);
        cpf.setDisable(false);
        motherName.setDisable(false);
        errorLabel.setText("");
        cpf.setText("");
        motherName.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}

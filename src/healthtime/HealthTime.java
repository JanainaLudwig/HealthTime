/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtime;

import dashboard.view.DashboardMonthController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.ControllerUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class HealthTime extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Starts at login
        //Parent root = FXMLLoader.load(getClass().getResource("/login/view/Login.fxml"));


        //Starts at month view (for dev purposes)
        DashboardMonthController controller = new DashboardMonthController(1);

        FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource("../../dashboard/view/DashboardMonth.fxml"));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        // End of month controller

        stage.setTitle("HealthTime");
        stage.getIcons().add(new Image("/resources/images/logo.png"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        launch(args);
    }
    
}

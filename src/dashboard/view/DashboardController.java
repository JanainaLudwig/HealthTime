
package dashboard.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private int userId;
    /*@FXML
    private Text username;*/



    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public DashboardController(int userId) {
        this.userId = userId;
    }
}

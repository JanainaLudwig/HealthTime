package location.modal;

import dashboard.view.DashboardController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import location.City;
import utils.Controller;
import utils.LocationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChangeLocation implements Initializable {
    @FXML
    private ListView cityList;
    @FXML
    private Pane pane;
    @FXML
    private TextField searchInput;
    @FXML
    private Text cityNotSelected;

    private Stage modalStage;
    private DashboardController controller;
    private City currentCity;

    public ChangeLocation(DashboardController controller, Scene scene) throws IOException {
        this.controller = controller;
        this.currentCity = LocationUtils.getCurrentCity();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangeLocation.fxml"));
        fxmlLoader.setController(this);
        Parent modalRoot = fxmlLoader.load();
        Scene modalScene = new Scene(modalRoot);
        modalScene.setFill(Color.TRANSPARENT);

        modalStage = new Stage();
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setScene(modalScene);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(scene.getWindow());
        modalStage.setResizable(false);
        modalStage.centerOnScreen();
        modalStage.show();
    }

    @FXML
    public void searchCities() {
        if (searchInput.getText().length() < 3) {
            setCitiesList(null);
            return;
        }

        setCitiesList(searchInput.getText());
    }

    @FXML
    private void itemSelected() {
        cityNotSelected.setOpacity(0);
    }

    @FXML
    public void confirmChange() {
        City city;

        //Check if
        if (cityList.getSelectionModel().getSelectedItems().size() == 0) {
            cityNotSelected.setOpacity(1);
            return;
        }

        city = (City) cityList.getSelectionModel().getSelectedItem();

        controller.setCity(city);
        closeModal();
    }

    public void setCitiesList(String start) {
        ArrayList<City> cities = new ArrayList();

        int quantity = 4;

        /**
         * TODO:
         * menssagem: erro de rede
         */

        if (currentCity != null) {
            cities.add(currentCity);
        } else {
            quantity++;
        }

        if (start != null) {
            cities.addAll(LocationUtils.getCitiesStartingWith(start, quantity));
        }

        cityList.setItems(FXCollections.observableArrayList(cities));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // setCitiesList(""); // List starts without cities
        setCitiesList(null);
    }

    @FXML
    private void closeModal() {
        controller.closeModal();

        modalStage.close();
    }
}

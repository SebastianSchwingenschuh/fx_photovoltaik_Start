package at.htl.photovoltaic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OffersController {

    @FXML
    private Label welcomeText;

    @FXML
    private ListView<?> lvOffers;

    @FXML
    private TextField tfFilterName;

    @FXML
    private Slider slFilterPrice;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfNoPanels;

    @FXML
    private TextField tfPowerPanel;

    @FXML
    private TextField tfTotal;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private ChoiceBox<?> cbTopOffer;

    @FXML
    private TextArea taTopReport;

    @FXML
    void onCreateOffer(ActionEvent event) {

    }

    @FXML
    void onDeleteOffer(ActionEvent event) {

    }

}

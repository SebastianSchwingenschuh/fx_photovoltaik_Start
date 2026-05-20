package at.htl.photovoltaic.controller;

import at.htl.photovoltaic.model.Offer;
import at.htl.photovoltaic.repository.OfferRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class OffersController {
    private OfferRepository repository;
    private ObservableList<Offer> masterList;
    private FilteredList<Offer> filteredList;


    @FXML
    private Label welcomeText;

    @FXML
    private ListView<Offer> lvOffers;

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
    void initialize() {
        repository = OfferRepository.getInstance();

        List<Offer> allOffers = repository.getAllOffers();
        masterList = FXCollections.observableArrayList(allOffers);

        filteredList = new FilteredList<>(masterList, offer -> true);
        lvOffers.setItems(filteredList);

        updateSliderBounds();

        tfFilterName.textProperty().addListener(((observable, oldValue, newValue) -> {
            applyFilters();
        }));

        slFilterPrice.valueProperty().addListener(((observable, oldValue, newValue) -> {
            applyFilters();
        }));

        lvOffers.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null){
                showDetails(newValue);
                btnDelete.setDisable(false);
            } else {
                clearDetails();
                btnDelete.setDisable(true);
            }
        }));



    }

    private void alert(String title, String msg){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearDetails() {
        tfId.clear();
        tfName.clear();
        tfNoPanels.clear();
        tfPowerPanel.clear();
        tfTotal.clear();
    }

    private void showDetails(Offer offer) {
        tfId.setText(String.valueOf(offer.getId()));
        tfName.setText(String.valueOf(offer.getName()));
        tfNoPanels.setText(String.valueOf(offer.getNumberOfPanels()));
        tfPowerPanel.setText(String.valueOf(offer.getPowerPerPanel()));
        tfTotal.setText(String.valueOf(offer.getTotalPrice()));
    }

    private void updateSliderBounds() {
        if(masterList.isEmpty()) {
            slFilterPrice.setMin(0);
            slFilterPrice.setMax(100);
        } else {
            slFilterPrice.setMax(repository.getMaximumPrice());
            slFilterPrice.setMin(repository.getMinimumPrice());
        }
    }

    private void applyFilters() {
        String nameFilter =tfFilterName.getText();
        double maxPrice = slFilterPrice.getValue();

        filteredList.setPredicate(offer -> {
            boolean matchNames = (nameFilter == null || nameFilter.isEmpty()) ||
                    offer.getName().toLowerCase().contains(nameFilter.toLowerCase());

            boolean matchesPrice = offer.getTotalPrice() <= maxPrice;

            return matchNames && matchesPrice;
        });
    }

    @FXML
    void onCreateOffer(ActionEvent event) {
        try {
            String name = tfName.getText();
            int numberOfPanels = Integer.parseInt(tfNoPanels.getText());
            double powerPerPanel = Double.parseDouble(tfPowerPanel.getText());
            double totalPrice = Double.parseDouble(tfTotal.getText());

            Offer newOffer = new Offer(name, numberOfPanels, powerPerPanel, totalPrice);
            repository.addOffer(newOffer);

            masterList.setAll(repository.getAllOffers());
            updateSliderBounds();
            clearDetails();
        } catch (NumberFormatException e) {
            alert("Fehler", "Bitte geben Sie gültige Zahlen ein.");
        }
    }

    @FXML
    void onDeleteOffer(ActionEvent event) {
        Offer selected = lvOffers.getSelectionModel().getSelectedItem();
        if(selected != null){
            repository.deleteOffer(selected.getId());
            masterList.remove(selected);
            updateSliderBounds();
            clearDetails();
            btnDelete.setDisable(true);
        }
    }

}

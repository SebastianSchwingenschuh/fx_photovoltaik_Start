package at.htl.photovoltaic.controller;

import at.htl.photovoltaic.model.Offer;
import at.htl.photovoltaic.model.SortCriteria;
import at.htl.photovoltaic.repository.OfferRepository;
import at.htl.photovoltaic.service.OfferService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class OffersController {
    private OfferRepository repository;
    private OfferService offerService;
    private ObservableList<Offer> masterList;
    private FilteredList<Offer> filteredList;

    @FXML
    public Button btnUpdate;

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
    private ChoiceBox<SortCriteria> cbTopOffer;

    @FXML
    private TextArea taTopReport;

    @FXML
    void initialize() {
        repository = OfferRepository.getInstance();
        offerService = new OfferService();

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
            if (newValue != null) {
                showDetails(newValue);
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
            } else {
                clearDetails();
                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);
            }
        }));

        cbTopOffer.getItems().addAll(SortCriteria.values());
        cbTopOffer.setValue(SortCriteria.TOTAL_PRICE);

        cbTopOffer.setOnAction(event -> {
            updateTop3Report();
        });

        updateTop3Report();
    }

    private void updateTop3Report() {
        SortCriteria selectedCriteria = cbTopOffer.getValue();
        if(selectedCriteria == null){
            return;
        }

        List<Offer> top3 = offerService.getTop3Offers(selectedCriteria);

        StringBuilder report = new StringBuilder();

        if(top3.isEmpty()){
            report.append("Keine Angebote verfügbar");
        } else {
            for (int i = 0; i < top3.size(); i++) {
                Offer offer = top3.get(i);
                report.append(offer.toString());
            }
        }
        taTopReport.setText(report.toString());
    }

    private void alert(String title, String msg) {
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
        if (masterList.isEmpty()) {
            slFilterPrice.setMin(0);
            slFilterPrice.setMax(100);
        } else {
            slFilterPrice.setMax(repository.getMaximumPrice());
            slFilterPrice.setMin(repository.getMinimumPrice());
            slFilterPrice.setValue(repository.getMaximumPrice());   //sonst muss man selber immer raufsetzen
        }
    }

    private void applyFilters() {
        String nameFilter = tfFilterName.getText();
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
        if (selected != null) {
            repository.deleteOffer(selected.getId());
            masterList.remove(selected);
            updateSliderBounds();
            clearDetails();
            btnDelete.setDisable(true);
        }
    }

    public void onUpdateOffer(ActionEvent actionEvent) {
        Offer selected = lvOffers.getSelectionModel().getSelectedItem();

        if(selected != null){
            String name = tfName.getText();
            int numberOfPanels = Integer.parseInt(tfNoPanels.getText());
            double powerPerPanel = Double.parseDouble(tfPowerPanel.getText());
            double totalPrice = Double.parseDouble(tfTotal.getText());

            selected.setName(name);
            selected.setNumberOfPanels(numberOfPanels);
            selected.setPowerPerPanel(powerPerPanel);
            selected.setTotalPrice(totalPrice);

            repository.updateOffer(selected);
            masterList.setAll(repository.getAllOffers());
            updateSliderBounds();
            clearDetails();
            btnUpdate.setDisable(true);
        }
    }
}

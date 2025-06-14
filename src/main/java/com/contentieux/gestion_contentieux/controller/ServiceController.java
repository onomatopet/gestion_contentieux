package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.CentreDAO;
import com.contentieux.gestion_contentieux.dao.ServiceDAO;
import com.contentieux.gestion_contentieux.model.Centre;
import com.contentieux.gestion_contentieux.model.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceController {

    @FXML private TableView<Service> serviceTableView;
    @FXML private TableColumn<Service, String> codeColumn;
    @FXML private TableColumn<Service, String> nomColumn;
    @FXML private TableColumn<Service, String> centreColumn; // Colonne pour le nom du centre
    @FXML private TextField codeTextField;
    @FXML private TextField nomTextField;
    @FXML private ComboBox<Centre> centreComboBox;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final CentreDAO centreDAO = new CentreDAO();
    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();
    private Map<Integer, Centre> centreMap; // Pour retrouver un centre par son ID facilement

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau aux propriétés du modèle
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeService"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomService"));

        // Pour la colonne du centre, c'est une liaison plus complexe
        centreColumn.setCellValueFactory(cellData -> {
            Service service = cellData.getValue();
            Centre centre = centreMap.get(service.getCentreId());
            return new SimpleStringProperty(centre != null ? centre.getNomCentre() : "N/A");
        });

        loadCentres(); // Charger les centres en premier
        loadServices();

        serviceTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> showServiceDetails(newVal));
    }

    private void loadCentres() {
        List<Centre> centres = centreDAO.getAll();
        centreComboBox.setItems(FXCollections.observableArrayList(centres));
        // Créer une map pour un accès rapide centreId -> Centre
        centreMap = centres.stream().collect(Collectors.toMap(Centre::getId, centre -> centre));
    }

    private void loadServices() {
        serviceList.clear();
        serviceList.addAll(serviceDAO.getAll());
        serviceTableView.setItems(serviceList);
    }

    private void showServiceDetails(Service service) {
        if (service != null) {
            codeTextField.setText(service.getCodeService());
            nomTextField.setText(service.getNomService());
            centreComboBox.setValue(centreMap.get(service.getCentreId()));
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleAddButton() {
        if (isInputValid()) {
            Centre selectedCentre = centreComboBox.getSelectionModel().getSelectedItem();
            Service newService = new Service(
                    codeTextField.getText(),
                    nomTextField.getText(),
                    selectedCentre.getId()
            );
            serviceDAO.add(newService);

            loadServices();
            clearFields();
        }
    }

    @FXML
    private void handleUpdateButton() {
        Service selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService != null && isInputValid()) {
            Centre selectedCentre = centreComboBox.getSelectionModel().getSelectedItem();
            selectedService.setCodeService(codeTextField.getText());
            selectedService.setNomService(nomTextField.getText());
            selectedService.setCentreId(selectedCentre.getId());
            serviceDAO.update(selectedService);

            loadServices();
            clearFields();
        }
    }

    @FXML
    private void handleDeleteButton() {
        Service selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            serviceDAO.delete(selectedService.getId());
            loadServices();
            clearFields();
        }
    }

    @FXML
    private void handleClearButton() {
        clearFields();
    }

    private void clearFields() {
        serviceTableView.getSelectionModel().clearSelection();
        codeTextField.clear();
        nomTextField.clear();
        centreComboBox.getSelectionModel().clearSelection();
    }

    private boolean isInputValid() {
        return !codeTextField.getText().trim().isEmpty() &&
                !nomTextField.getText().trim().isEmpty() &&
                centreComboBox.getSelectionModel().getSelectedItem() != null;
    }
}
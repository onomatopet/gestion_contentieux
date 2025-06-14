package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.CentreDAO;
import com.contentieux.gestion_contentieux.model.Centre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CentreController {

    // Injection des composants FXML
    @FXML private TableView<Centre> centreTableView;
    @FXML private TableColumn<Centre, String> codeColumn;
    @FXML private TableColumn<Centre, String> nomColumn;
    @FXML private TextField codeTextField;
    @FXML private TextField nomTextField;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    private final CentreDAO centreDAO = new CentreDAO();
    private final ObservableList<Centre> centreList = FXCollections.observableArrayList();

    /**
     * Méthode appelée automatiquement après le chargement du FXML.
     */
    @FXML
    public void initialize() {
        // Lier les colonnes du tableau aux propriétés du modèle Centre
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeCentre"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomCentre"));

        // Charger les données initiales
        loadCentres();

        // Ajouter un listener pour remplir les champs quand on sélectionne une ligne
        centreTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCentreDetails(newValue));
    }

    private void loadCentres() {
        centreList.clear();
        List<Centre> centresFromDB = centreDAO.getAll();
        centreList.addAll(centresFromDB);
        centreTableView.setItems(centreList);
    }

    private void showCentreDetails(Centre centre) {
        if (centre != null) {
            codeTextField.setText(centre.getCodeCentre());
            nomTextField.setText(centre.getNomCentre());
        } else {
            // Si aucune sélection, on vide les champs
            clearFields();
        }
    }

    private void clearFields() {
        codeTextField.clear();
        nomTextField.clear();
        centreTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleAddButton() {
        if (isInputValid()) {
            Centre newCentre = new Centre(codeTextField.getText(), nomTextField.getText());
            centreDAO.add(newCentre);
            loadCentres(); // Recharger la liste pour voir le nouvel ajout
            clearFields();
        }
    }

    @FXML
    private void handleUpdateButton() {
        Centre selectedCentre = centreTableView.getSelectionModel().getSelectedItem();
        if (selectedCentre != null && isInputValid()) {
            selectedCentre.setCodeCentre(codeTextField.getText());
            selectedCentre.setNomCentre(nomTextField.getText());
            centreDAO.update(selectedCentre);
            loadCentres(); // Recharger pour voir la modification
            clearFields();
        }
    }

    @FXML
    private void handleDeleteButton() {
        Centre selectedCentre = centreTableView.getSelectionModel().getSelectedItem();
        if (selectedCentre != null) {
            centreDAO.delete(selectedCentre.getId());
            loadCentres(); // Recharger
            clearFields();
        }
    }

    private boolean isInputValid() {
        // On pourrait ajouter des contrôles plus poussés ici (ex: format du code)
        return !codeTextField.getText().trim().isEmpty() && !nomTextField.getText().trim().isEmpty();
    }
}
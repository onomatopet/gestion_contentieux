package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.ContraventionDAO;
import com.contentieux.gestion_contentieux.model.Contravention;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContraventionController {

    @FXML private TableView<Contravention> contraventionTableView;
    @FXML private TableColumn<Contravention, String> codeColumn;
    @FXML private TableColumn<Contravention, String> libelleColumn;
    @FXML private TableColumn<Contravention, String> descriptionColumn;

    @FXML private TextField codeField;
    @FXML private TextField libelleField;
    @FXML private TextArea descriptionArea;

    private final ContraventionDAO dao = new ContraventionDAO();
    private final ObservableList<Contravention> contraventionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau aux propriétés du modèle
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadData();

        // Remplir les champs quand on sélectionne une ligne
        contraventionTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> showDetails(newVal));
    }

    private void loadData() {
        contraventionList.setAll(dao.getAll());
        contraventionTableView.setItems(contraventionList);
    }

    private void showDetails(Contravention c) {
        if (c != null) {
            codeField.setText(c.getCode());
            libelleField.setText(c.getLibelle());
            descriptionArea.setText(c.getDescription());
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            Contravention c = new Contravention(0, codeField.getText(), libelleField.getText(), descriptionArea.getText());
            dao.add(c);
            loadData();
            clearFields();
        }
    }

    @FXML
    private void handleUpdate() {
        Contravention c = contraventionTableView.getSelectionModel().getSelectedItem();
        if (c != null && isInputValid()) {
            c.setCode(codeField.getText());
            c.setLibelle(libelleField.getText());
            c.setDescription(descriptionArea.getText());
            dao.update(c);
            loadData();
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        Contravention c = contraventionTableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            // Ajouter une confirmation ici serait une bonne pratique
            dao.delete(c.getId());
            loadData();
            clearFields();
        }
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void clearFields() {
        contraventionTableView.getSelectionModel().clearSelection();
        codeField.clear();
        libelleField.clear();
        descriptionArea.clear();
    }

    private boolean isInputValid() {
        // Valider que le code et le libellé ne sont pas vides
        return !codeField.getText().trim().isEmpty() && !libelleField.getText().trim().isEmpty();
    }
}
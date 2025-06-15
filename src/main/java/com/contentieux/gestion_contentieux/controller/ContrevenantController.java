package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.ContrevenantDAO;
import com.contentieux.gestion_contentieux.model.Contrevenant;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContrevenantController {

    @FXML private TableView<Contrevenant> contrevenantTableView;
    @FXML private TableColumn<Contrevenant, String> codeColumn;
    @FXML private TableColumn<Contrevenant, String> nomColumn;
    @FXML private TableColumn<Contrevenant, String> adresseColumn;
    @FXML private TableColumn<Contrevenant, String> telephoneColumn;

    @FXML private TextField codeField;
    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;

    private final ContrevenantDAO dao = new ContrevenantDAO();

    @FXML
    public void initialize() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        loadData();

        contrevenantTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> showDetails(newVal));
    }

    private void loadData() {
        contrevenantTableView.setItems(FXCollections.observableArrayList(dao.getAll()));
    }

    private void showDetails(Contrevenant c) {
        if (c != null) {
            codeField.setText(c.getCode());
            nomField.setText(c.getNomComplet());
            adresseField.setText(c.getAdresse());
            telephoneField.setText(c.getTelephone());
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleAdd() {
        Contrevenant c = new Contrevenant(0, codeField.getText(), nomField.getText(), adresseField.getText(), telephoneField.getText());
        dao.add(c);
        loadData();
        clearFields();
    }

    @FXML
    private void handleUpdate() {
        Contrevenant c = contrevenantTableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            c.setCode(codeField.getText());
            c.setNomComplet(nomField.getText());
            c.setAdresse(adresseField.getText());
            c.setTelephone(telephoneField.getText());
            dao.update(c);
            loadData();
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        Contrevenant c = contrevenantTableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            dao.delete(c.getId());
            loadData();
            clearFields();
        }
    }

    private void clearFields() {
        codeField.clear();
        nomField.clear();
        adresseField.clear();
        telephoneField.clear();
    }
}
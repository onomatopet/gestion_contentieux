package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.*;
import com.contentieux.gestion_contentieux.model.*;
import com.contentieux.gestion_contentieux.service.RepartitionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EncaissementSaisieController {

    @FXML private Label titleLabel;
    @FXML private TextField montantField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> modeReglementComboBox;
    @FXML private VBox chequeFieldsVBox;
    @FXML private TextField banqueField;
    @FXML private TextField numeroChequeField;

    private AffaireRow affaireConcernee;
    private final EncaissementDAO encaissementDAO = new EncaissementDAO();
    private final RepartitionService repartitionService = new RepartitionService();
    private final RepartitionResultatsDAO repartitionResultatsDAO = new RepartitionResultatsDAO();
    private final AffaireDAO affaireDAO = new AffaireDAO();

    @FXML
    public void initialize() {
        modeReglementComboBox.setItems(FXCollections.observableArrayList("Espèces", "Chèque"));
        datePicker.setValue(LocalDate.now());

        // Afficher/Cacher les champs du chèque en fonction de la sélection
        modeReglementComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            chequeFieldsVBox.setVisible("Chèque".equals(newVal));
        });
    }

    // Méthode pour recevoir l'affaire depuis le contrôleur parent
    public void setAffaire(AffaireRow affaire) {
        this.affaireConcernee = affaire;
        titleLabel.setText("Encaissement pour l'Affaire : " + affaire.getNumero());
    }

    @FXML
    private void handleSave() {
        if (!isInputValid()) {
            // Afficher une alerte
            return;
        }

        // 1. Créer l'objet Encaissement
        double montant = Double.parseDouble(montantField.getText());
        Encaissement enc = new Encaissement(
                generateNumeroEncaissement(),
                generateNumeroMandat(),
                datePicker.getValue(),
                montant,
                modeReglementComboBox.getValue(),
                banqueField.getText(),
                numeroChequeField.getText(),
                affaireConcernee.getAffaireId()
        );

        // 2. Sauvegarder et récupérer l'ID
        int encaissementId = encaissementDAO.add(enc);
        if (encaissementId == -1) { /* Gérer l'erreur */ return; }
        enc.setId(encaissementId);

        // 3. Calculer les parts
        RepartitionResultats resultats = repartitionService.calculerParts(enc);

        // 4. Sauvegarder les résultats
        repartitionResultatsDAO.add(resultats);

        // 5. Mettre à jour le statut de l'affaire
        double nouveauTotalEncaisse = affaireConcernee.getMontantEncaisse() + montant;
        String nouveauStatut = (nouveauTotalEncaisse >= affaireConcernee.getMontantAmende()) ? "Soldée" : "Partiellement Payée";
        affaireDAO.updateStatus(affaireConcernee.getAffaireId(), nouveauStatut);

        // 6. Fermer la fenêtre
        handleCancel();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    private String generateNumeroEncaissement() {
        String periode = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        int sequence = encaissementDAO.countEncaissementsForPeriode(periode) + 1;
        return periode + "R" + String.format("%05d", sequence);
    }

    private String generateNumeroMandat() {
        String periode = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        int sequence = encaissementDAO.countMandatsForPeriode(periode) + 1;
        return periode + "M" + String.format("%04d", sequence);
    }

    private boolean isInputValid() {
        // Ajouter la logique de validation ici
        return true;
    }
}
package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.*;
import com.contentieux.gestion_contentieux.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AffaireSaisieController {

    // DAOs
    private final AgentDAO agentDAO = new AgentDAO();
    private final ContrevenantDAO contrevenantDAO = new ContrevenantDAO();
    private final ContraventionDAO contraventionDAO = new ContraventionDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final AffaireDAO affaireDAO = new AffaireDAO(); // Ajouté
    private final AffaireActeurDAO affaireActeurDAO = new AffaireActeurDAO(); // Ajouté

    // ... (Composants FXML et listes ne changent pas) ...
    @FXML private TextField numeroAffaireField;
    @FXML private DatePicker dateCreationPicker;
    @FXML private ComboBox<Contrevenant> contrevenantComboBox;
    @FXML private ComboBox<Contravention> contraventionComboBox;
    @FXML private ComboBox<Service> serviceConstatComboBox;
    @FXML private TextField montantAmendeField;
    @FXML private ComboBox<Agent> saisissantComboBox;
    @FXML private ListView<Agent> saisissantListView;
    @FXML private ComboBox<Agent> chefComboBox;
    @FXML private ListView<Agent> chefListView;

    private final ObservableList<Agent> agentsSaisis = FXCollections.observableArrayList();
    private final ObservableList<Agent> chefsSaisis = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Initialiser les champs
        dateCreationPicker.setValue(LocalDate.now());
        // On génèrera le numéro d'affaire au moment de la sauvegarde
        numeroAffaireField.setText("Sera généré automatiquement");

        // Charger les données dans les ComboBox
        loadComboBoxData();

        // Lier les listes de données aux ListView
        saisissantListView.setItems(agentsSaisis);
        chefListView.setItems(chefsSaisis);
    }

    private void loadComboBoxData() {
        contrevenantComboBox.setItems(FXCollections.observableArrayList(contrevenantDAO.getAll()));
        contraventionComboBox.setItems(FXCollections.observableArrayList(contraventionDAO.getAll()));
        serviceConstatComboBox.setItems(FXCollections.observableArrayList(serviceDAO.getAll()));

        ObservableList<Agent> allAgents = FXCollections.observableArrayList(agentDAO.getAll());
        saisissantComboBox.setItems(allAgents);
        chefComboBox.setItems(allAgents);
    }

    @FXML
    private void handleAddSaisissant() {
        Agent selected = saisissantComboBox.getSelectionModel().getSelectedItem();
        if (selected != null && !agentsSaisis.contains(selected)) {
            agentsSaisis.add(selected);
        }
    }

    @FXML
    private void handleAddChef() {
        Agent selected = chefComboBox.getSelectionModel().getSelectedItem();
        if (selected != null && !chefsSaisis.contains(selected)) {
            chefsSaisis.add(selected);
        }
    }

    @FXML
    private void handleSaveButton() {
        if (!isInputValid()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs obligatoires et sélectionner au moins un saisissant.");
            return;
        } // 1. Générer le numéro d'affaire
        String numeroAffaire = generateNumeroAffaire();

        // 2. Créer l'objet Affaire
        Affaire nouvelleAffaire = new Affaire(
                numeroAffaire,
                dateCreationPicker.getValue(),
                Double.parseDouble(montantAmendeField.getText()),
                contrevenantComboBox.getValue().getId(),
                contraventionComboBox.getValue().getId(),
                serviceConstatComboBox.getValue().getId()
        );

        // 3. Sauvegarder l'affaire en BDD et récupérer son ID
        int affaireId = affaireDAO.add(nouvelleAffaire);

        if (affaireId != -1) {
            // 4. Sauvegarder les acteurs
            affaireActeurDAO.addActeurs(affaireId, new ArrayList<>(agentsSaisis), "Saisissant");
            affaireActeurDAO.addActeurs(affaireId, new ArrayList<>(chefsSaisis), "Chef");

            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'affaire " + numeroAffaire + " a été enregistrée avec succès.");
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur Base de Données", "L'affaire n'a pas pu être enregistrée.");
        }
    }



    private String generateNumeroAffaire() {
        LocalDate date = dateCreationPicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM");
        String periode = date.format(formatter); // ex: "2506"

        int dernierNumero = affaireDAO.getLastNumeroForPeriode(periode);
        int nouveauNumero = dernierNumero + 1;

        // Formate sur 5 chiffres avec des zéros devant (ex: 1 -> 00001)
        return periode + String.format("%05d", nouveauNumero);
    }

    private void clearForm() {
        dateCreationPicker.setValue(LocalDate.now());
        contrevenantComboBox.getSelectionModel().clearSelection();
        contraventionComboBox.getSelectionModel().clearSelection();
        serviceConstatComboBox.getSelectionModel().clearSelection();
        montantAmendeField.clear();
        agentsSaisis.clear();
        chefsSaisis.clear();
    }

    private boolean isInputValid() {
        try {
            Double.parseDouble(montantAmendeField.getText());
        } catch (NumberFormatException e) {
            return false; // Le montant n'est pas un nombre valide
        }

        return dateCreationPicker.getValue() != null &&
                contrevenantComboBox.getValue() != null &&
                contraventionComboBox.getValue() != null &&
                serviceConstatComboBox.getValue() != null &&
                !agentsSaisis.isEmpty(); // Au moins un saisissant est requis
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton() {
        // Logique pour fermer cet onglet ou vider l'écran
        System.out.println("Annulation...");
    }
}
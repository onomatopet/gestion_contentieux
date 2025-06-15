package com.contentieux.gestion_contentieux.controller;

import com.contentieux.gestion_contentieux.dao.AffaireDAO;
import com.contentieux.gestion_contentieux.model.AffaireRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// Importations pour les icônes
import javafx.fxml.FXMLLoader;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ToolBar;

import java.io.IOException;
import java.time.LocalDate;

public class AffaireSuiviController implements IToolBarController {

    @FXML private TextField searchNumeroField;
    @FXML private TextField searchContrevenantField;
    @FXML private TableView<AffaireRow> affaireTableView;
    @FXML private TableColumn<AffaireRow, String> numeroColumn;
    @FXML private TableColumn<AffaireRow, LocalDate> dateColumn;
    @FXML private TableColumn<AffaireRow, String> contrevenantColumn;
    @FXML private TableColumn<AffaireRow, String> contraventionColumn;
    @FXML private TableColumn<AffaireRow, Double> montantAmendeColumn;
    @FXML private TableColumn<AffaireRow, Double> montantEncaisseColumn;
    @FXML private TableColumn<AffaireRow, String> statutColumn;

    private final AffaireDAO affaireDAO = new AffaireDAO();
    private final ObservableList<AffaireRow> affaireList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de AffaireRow
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        contrevenantColumn.setCellValueFactory(new PropertyValueFactory<>("contrevenant"));
        contraventionColumn.setCellValueFactory(new PropertyValueFactory<>("contravention"));
        montantAmendeColumn.setCellValueFactory(new PropertyValueFactory<>("montantAmende"));
        montantEncaisseColumn.setCellValueFactory(new PropertyValueFactory<>("montantEncaisse"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        affaireTableView.setItems(affaireList);

        // Charger toutes les affaires au démarrage
        loadAffaires();
    }

    /**
     * Cette méthode est requise par l'interface IToolBarController.
     * Elle est appelée par MainController après le chargement de cette vue.
     * @param toolBar La barre d'outils principale.
     */
    @Override
    public void setToolBar(ToolBar toolBar) {
        System.out.println(">>> Méthode setToolBar DANS AffaireSuiviController a été appelée !");

        Button addEncaissementButton = new Button("Ajouter un Encaissement");
        addEncaissementButton.setGraphic(new FontIcon(MaterialDesign.MDI_PLUS_BOX));
        addEncaissementButton.setContentDisplay(ContentDisplay.LEFT);
        addEncaissementButton.setOnAction(event -> handleAddEncaissement());

        Button viewDetailsButton = new Button("Voir Détails");
        viewDetailsButton.setGraphic(new FontIcon(MaterialDesign.MDI_EYE));
        viewDetailsButton.setContentDisplay(ContentDisplay.LEFT);
        viewDetailsButton.setOnAction(event -> handleViewDetails());

        toolBar.getItems().addAll(addEncaissementButton, new Separator(), viewDetailsButton);
        System.out.println("Boutons ajoutés à la ToolBar.");
    }

    private void loadAffaires() {
        String numeroFilter = searchNumeroField.getText();
        String contrevenantFilter = searchContrevenantField.getText();

        affaireList.clear();
        affaireList.addAll(affaireDAO.findAffaires(numeroFilter, contrevenantFilter));
    }

    @FXML
    private void handleSearch() {
        loadAffaires();
    }

    @FXML
    private void handleReset() {
        searchNumeroField.clear();
        searchContrevenantField.clear();
        loadAffaires();
    }

    @FXML
    private void handleAddEncaissement() {
        AffaireRow selectedAffaire = affaireTableView.getSelectionModel().getSelectedItem();
        if (selectedAffaire == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner une affaire pour ajouter un encaissement.");
            return;
        }

        if ("Soldée".equals(selectedAffaire.getStatut())) {
            showAlert("Affaire soldée", "Cette affaire est déjà soldée, aucun encaissement supplémentaire n'est possible.");
            return;
        }

        try {
            // Charger le FXML de la fenêtre de saisie
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/contentieux/gestion_contentieux/EncaissementSaisieView.fxml"));
            Scene scene = new Scene(loader.load());

            // Créer une nouvelle fenêtre (Stage) pour la boîte de dialogue
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Saisir un Encaissement");
            dialogStage.initModality(Modality.WINDOW_MODAL); // Bloque la fenêtre parente
            dialogStage.initOwner(affaireTableView.getScene().getWindow()); // Définit la fenêtre parente
            dialogStage.setScene(scene);

            // Passer l'affaire sélectionnée au contrôleur de la nouvelle fenêtre
            EncaissementSaisieController controller = loader.getController();
            controller.setAffaire(selectedAffaire);

            // Afficher la fenêtre et attendre qu'elle soit fermée
            dialogStage.showAndWait();

            // Après la fermeture, rafraîchir le tableau principal pour voir les changements
            loadAffaires();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewDetails() {
        // Logique pour voir les détails complets de l'affaire et la liste de ses encaissements.
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
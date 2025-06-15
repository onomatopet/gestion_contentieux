package com.contentieux.gestion_contentieux.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML private BorderPane mainBorderPane;
    @FXML private ToolBar mainToolBar; // Référence à notre nouvelle ToolBar

    @FXML
    public void initialize() {
        // Optionnel : charger une vue par défaut au démarrage
        // loadView("CentreView.fxml");
    }

    @FXML
    private void handleMenuItemAgents() {
        loadView("AgentView.fxml"); // Nom du futur fichier FXML
    }

    @FXML
    private void handleMenuItemContrevenants() {
        loadView("ContrevenantView.fxml"); // Nom du futur fichier FXML
    }

    @FXML
    private void handleMenuItemContraventions() {
        loadView("ContraventionView.fxml"); // Nom du futur fichier FXML
    }

    // --- Les méthodes pour les menus ne changent pas ---
    @FXML private void handleMenuItemCentres() { loadView("CentreView.fxml"); }
    @FXML private void handleMenuItemServices() { loadView("ServiceView.fxml"); }
    @FXML private void handleMenuItemNouvelleAffaire() { loadView("AffaireSaisieView.fxml"); }
    @FXML private void handleMenuItemSuiviAffaires() { loadView("AffaireSuiviView.fxml"); }
    @FXML private void handleMenuItemQuitter() {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode utilitaire pour charger les vues.
     * C'est ici que la magie opère pour la ToolBar.
     */
    private void loadView(String fxmlFileName) {
        mainToolBar.getItems().clear();

        try {
            String resourcePath = "/com/contentieux/gestion_contentieux/" + fxmlFileName;

            // 1. On déclare et initialise l'URL de la ressource
            URL resourceUrl = getClass().getResource(resourcePath);

            // 2. On vérifie qu'elle n'est pas nulle
            if (resourceUrl == null) {
                showAlert("Erreur de ressource", "Fichier FXML non trouvé : " + resourcePath);
                return;
            }

            // 3. On utilise l'URL validée pour charger le FXML
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof IToolBarController) {
                ((IToolBarController) controller).setToolBar(mainToolBar);
            }

            mainBorderPane.setCenter(view);

        } catch (IOException e) {
            showAlert("Erreur de chargement FXML", "Erreur lors du chargement de " + fxmlFileName);
            e.printStackTrace();
        }
    }

    // La méthode showAlert ne change pas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
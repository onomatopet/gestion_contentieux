package com.contentieux.gestion_contentieux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        // Optionnel: charger une vue par défaut au démarrage
        // loadView("CentreView.fxml");
    }

    @FXML
    private void handleMenuItemCentres(ActionEvent event) {
        loadView("CentreView.fxml");
    }

    @FXML
    private void handleMenuItemServices(ActionEvent event) {
        loadView("ServiceView.fxml");
    }

    @FXML
    private void handleMenuItemNouvelleAffaire(ActionEvent event) {
        // À implémenter plus tard
        System.out.println("Chargement de la vue 'Nouvelle Affaire'...");
        // loadView("AffaireView.fxml");
    }

    @FXML
    private void handleMenuItemQuitter(ActionEvent event) {
        // Obtenir la fenêtre actuelle et la fermer
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode utilitaire pour charger une vue FXML et l'afficher au centre.
     * @param fxmlFileName Le nom du fichier FXML à charger.
     */
    private void loadView(String fxmlFileName) {
        try {
            // Le chemin doit être relatif au dossier 'resources'
            String resourcePath = "/com/contentieux/gestion_contentieux/" + fxmlFileName;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher une alerte à l'utilisateur en cas d'erreur de chargement
        }
    }
}
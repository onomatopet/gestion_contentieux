package com.contentieux.gestion_contentieux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        // Optionnel: charger une vue par défaut au démarrage
        // loadView("CentreView.fxml");
    }

    @FXML // Ajouter l'annotation
    private void handleMenuItemCentres() { // Retirer le paramètre ActionEvent
        loadView("CentreView.fxml");
    }

    @FXML // Ajouter l'annotation
    private void handleMenuItemServices() { // Retirer le paramètre ActionEvent
        loadView("ServiceView.fxml");
    }

    @FXML // Ajouter l'annotation
    private void handleMenuItemNouvelleAffaire() { // Retirer le paramètre ActionEvent
        loadView("AffaireSaisieView.fxml");
    }

    @FXML // Ajouter l'annotation
    private void handleMenuItemQuitter() { // Retirer le paramètre ActionEvent
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode utilitaire pour charger une vue FXML et l'afficher au centre.
     * @param fxmlFileName Le nom du fichier FXML à charger.
     */
    private void loadView(String fxmlFileName) {
        try {
            String resourcePath = "/com/contentieux/gestion_contentieux/" + fxmlFileName;

            // Étape de débogage : vérifier si la ressource est trouvée
            URL resourceUrl = getClass().getResource(resourcePath);
            if (resourceUrl == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de ressource",
                        "Le fichier FXML n'a pas été trouvé à l'emplacement : " + resourcePath);
                return; // On arrête tout ici
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent view = loader.load();
            mainBorderPane.setCenter(view);

        } catch (IOException e) {
            // Cette erreur se produira si le FXML est trouvé mais contient une erreur de syntaxe
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement FXML",
                    "Une erreur est survenue lors du chargement de la vue : " + fxmlFileName + "\n\nErreur détaillée : " + e.getMessage());
            e.printStackTrace(); // On remet le printStackTrace ici pour voir l'erreur complète dans la console
        }
    }

    // Ajouter cette méthode dans MainController.java
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package com.contentieux.gestion_contentieux;

import com.contentieux.gestion_contentieux.util.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Établir la connexion à la base de données au démarrage
        DatabaseConnection.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ServiceView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Gestion des Services");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // Fermer la connexion à la base de données à la fermeture de l'application
        DatabaseConnection.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
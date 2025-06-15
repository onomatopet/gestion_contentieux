module com.contentieux.gestion_contentieux {
    // Déclare que notre module a besoin des bibliothèques JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Déclare que notre module a besoin de la bibliothèque pour la BDD
    requires java.sql;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;

    // Ouvre l'accès à nos paquetages pour que JavaFX puisse y accéder
    opens com.contentieux.gestion_contentieux to javafx.fxml;
    exports com.contentieux.gestion_contentieux;

    // On doit aussi ouvrir l'accès au paquetage contenant le contrôleur
    opens com.contentieux.gestion_contentieux.controller to javafx.fxml;
    exports com.contentieux.gestion_contentieux.controller;

    // Et au paquetage contenant le modèle, pour les TableView
    opens com.contentieux.gestion_contentieux.model to javafx.fxml;
    exports com.contentieux.gestion_contentieux.model;
}
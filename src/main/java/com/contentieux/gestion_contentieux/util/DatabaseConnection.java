package com.contentieux.gestion_contentieux.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // L'URL de connexion à notre base de données SQLite.
    // Le fichier doit se trouver dans un dossier "database" à la racine du projet.
    private static final String URL = "jdbc:sqlite:database/gestion_contentieux.db";

    // L'instance unique de notre connexion.
    private static Connection instance = null;

    /**
     * Le constructeur est privé pour empêcher la création d'instances
     * depuis l'extérieur. C'est la base du pattern Singleton.
     */
    private DatabaseConnection() {
        // Constructeur privé
    }

    /**
     * La méthode publique pour obtenir l'unique instance de la connexion.
     * Si la connexion n'existe pas, elle est créée.
     *
     * @return L'objet Connection à la base de données.
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                // Tente de créer la connexion si elle n'a jamais été initialisée.
                instance = DriverManager.getConnection(URL);
                System.out.println("Connexion à la base de données SQLite établie avec succès.");
            } catch (SQLException e) {
                // En cas d'erreur (ex: fichier non trouvé), affiche l'erreur.
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
                // Dans une vraie application, on pourrait afficher une alerte à l'utilisateur ici.
            }
        }
        return instance;
    }

    /**
     * Méthode pour fermer la connexion.
     * À appeler lorsque l'application se ferme.
     */
    public static void close() {
        if (instance != null) {
            try {
                instance.close();
                instance = null;
                System.out.println("Connexion à la base de données fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
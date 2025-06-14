package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Agent;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AffaireActeurDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    /**
     * Ajoute une liste d'acteurs à une affaire avec un rôle spécifique.
     * @param affaireId L'ID de l'affaire concernée.
     * @param acteurs La liste des agents.
     * @param role Le rôle de ces agents ('Chef' ou 'Saisissant').
     */
    public void addActeurs(int affaireId, List<Agent> acteurs, String role) {
        String sql = "INSERT INTO affaire_acteurs(affaire_id, agent_id, role_sur_affaire) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Utiliser le traitement par lots (batch) pour l'efficacité
            for (Agent acteur : acteurs) {
                pstmt.setInt(1, affaireId);
                pstmt.setInt(2, acteur.getId());
                pstmt.setString(3, role);
                pstmt.addBatch(); // Ajouter la requête au lot
            }
            pstmt.executeBatch(); // Exécuter toutes les requêtes d'un coup
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout des acteurs à l'affaire : " + e.getMessage());
        }
    }
}
package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Centre;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CentreDAO {

    // Obtenir la connexion depuis notre singleton
    private final Connection connection = DatabaseConnection.getInstance();

    /**
     * Récupère tous les centres de la base de données.
     * @return Une liste d'objets Centre.
     */
    public List<Centre> getAll() {
        List<Centre> centres = new ArrayList<>();
        String sql = "SELECT * FROM centres ORDER BY nom_centre"; // On trie par nom pour l'affichage

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Centre centre = new Centre(
                        rs.getInt("id"),
                        rs.getString("code_centre"),
                        rs.getString("nom_centre")
                );
                centres.add(centre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des centres : " + e.getMessage());
        }
        return centres;
    }

    /**
     * Ajoute un nouveau centre dans la base de données.
     * @param centre L'objet Centre à ajouter (sans id).
     */
    public void add(Centre centre) {
        String sql = "INSERT INTO centres(code_centre, nom_centre) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, centre.getCodeCentre());
            pstmt.setString(2, centre.getNomCentre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du centre : " + e.getMessage());
        }
    }

    /**
     * Met à jour un centre existant.
     * @param centre L'objet Centre à mettre à jour (avec son id).
     */
    public void update(Centre centre) {
        String sql = "UPDATE centres SET code_centre = ?, nom_centre = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, centre.getCodeCentre());
            pstmt.setString(2, centre.getNomCentre());
            pstmt.setInt(3, centre.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du centre : " + e.getMessage());
        }
    }

    /**
     * Supprime un centre par son id.
     * @param id L'id du centre à supprimer.
     */
    public void delete(int id) {
        // Attention : En pratique, il faudrait vérifier si des services sont liés
        // à ce centre avant de permettre la suppression (gestion des contraintes).
        String sql = "DELETE FROM centres WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du centre : " + e.getMessage());
        }
    }
}
package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Affaire;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class AffaireDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    /**
     * Ajoute une nouvelle affaire et retourne son ID auto-généré.
     * @param affaire L'objet Affaire à ajouter.
     * @return L'ID de l'affaire nouvellement créée, ou -1 en cas d'erreur.
     */
    public int add(Affaire affaire) {
        String sql = "INSERT INTO affaires(numero_affaire, date_creation, montant_amende_total, " +
                "contrevenant_id, contravention_id, service_constat_id) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, affaire.getNumeroAffaire());
            pstmt.setDate(2, Date.valueOf(affaire.getDateCreation()));
            pstmt.setDouble(3, affaire.getMontantAmendeTotal());
            pstmt.setInt(4, affaire.getContrevenantId());
            pstmt.setInt(5, affaire.getContraventionId());
            pstmt.setInt(6, affaire.getServiceConstatId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retourne l'ID généré
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'affaire : " + e.getMessage());
        }
        return -1; // Retourne -1 si l'ajout a échoué
    }

    /**
     * Récupère le dernier numéro d'affaire pour une période donnée (ex: "2506").
     * @param periode Les 4 premiers chiffres du numéro d'affaire (YYMM).
     * @return Le dernier numéro de séquence (ex: 99 pour 250600099), ou 0 si aucun.
     */
    public int getLastNumeroForPeriode(String periode) {
        String sql = "SELECT numero_affaire FROM affaires WHERE numero_affaire LIKE ? ORDER BY numero_affaire DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, periode + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String lastNumero = rs.getString("numero_affaire");
                // Extrait les 5 derniers chiffres et les convertit en entier
                return Integer.parseInt(lastNumero.substring(4));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du dernier numéro d'affaire: " + e.getMessage());
        }
        return 0; // Aucun numéro trouvé pour cette période
    }
}
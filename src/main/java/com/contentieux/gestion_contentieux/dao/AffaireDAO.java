package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Affaire;
import com.contentieux.gestion_contentieux.model.AffaireRow;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
     * Récupère une liste d'affaires avec des informations étendues pour l'affichage.
     * Inclut des filtres de recherche.
     * @param numeroFilter Filtre sur le numéro d'affaire (peut être vide).
     * @param contrevenantFilter Filtre sur le nom du contrevenant (peut être vide).
     * @return Une liste de Maps, chaque Map représentant une ligne du tableau.
     */
    public List<AffaireRow> findAffaires(String numeroFilter, String contrevenantFilter) {
        List<AffaireRow> affaireRows = new ArrayList<>();

        // Requête complexe avec des jointures et un calcul de somme
        String sql = "SELECT " +
                "    a.id AS affaire_id, " +
                "    a.numero_affaire, " +
                "    a.date_creation, " +
                "    co.nom_complet AS contrevenant_nom, " +
                "    cn.libelle AS contravention_libelle, " +
                "    a.montant_amende_total, " +
                "    IFNULL((SELECT SUM(e.montant_encaisse) FROM encaissements e WHERE e.affaire_id = a.id), 0) AS montant_total_encaisse, " +
                "    a.statut " +
                "FROM affaires a " +
                "JOIN contrevenants co ON a.contrevenant_id = co.id " +
                "JOIN contraventions cn ON a.contravention_id = cn.id " +
                "WHERE a.numero_affaire LIKE ? AND co.nom_complet LIKE ? " +
                "ORDER BY a.date_creation DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + numeroFilter + "%");
            pstmt.setString(2, "%" + contrevenantFilter + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                affaireRows.add(new AffaireRow(
                        rs.getInt("affaire_id"),
                        rs.getString("numero_affaire"),
                        rs.getDate("date_creation").toLocalDate(),
                        rs.getString("contrevenant_nom"),
                        rs.getString("contravention_libelle"),
                        rs.getDouble("montant_amende_total"),
                        rs.getDouble("montant_total_encaisse"),
                        rs.getString("statut")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des affaires : " + e.getMessage());
        }
        return affaireRows;
    }

    /**
     * Met à jour le statut d'une affaire.
     * @param affaireId L'ID de l'affaire à mettre à jour.
     * @param newStatus Le nouveau statut ('Partiellement Payée' ou 'Soldée').
     */
    public void updateStatus(int affaireId, String newStatus) {
        String sql = "UPDATE affaires SET statut = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, affaireId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut de l'affaire : " + e.getMessage());
        }
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
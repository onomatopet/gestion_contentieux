package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Encaissement;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class EncaissementDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public int add(Encaissement encaissement) {
        String sql = "INSERT INTO encaissements(numero_encaissement, numero_mandat, date_encaissement, " +
                "montant_encaisse, mode_reglement, banque_paiement, numero_cheque, affaire_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, encaissement.getNumeroEncaissement());
            pstmt.setString(2, encaissement.getNumeroMandat());
            pstmt.setDate(3, Date.valueOf(encaissement.getDateEncaissement()));
            pstmt.setDouble(4, encaissement.getMontantEncaisse());
            pstmt.setString(5, encaissement.getModeReglement());
            pstmt.setString(6, encaissement.getBanquePaiement());
            pstmt.setString(7, encaissement.getNumeroCheque());
            pstmt.setInt(8, encaissement.getAffaireId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retourne l'ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int countEncaissementsForPeriode(String periode) {
        String sql = "SELECT COUNT(*) FROM encaissements WHERE numero_encaissement LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, periode + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countMandatsForPeriode(String periode) {
        String sql = "SELECT COUNT(DISTINCT numero_mandat) FROM encaissements WHERE numero_mandat LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, periode + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
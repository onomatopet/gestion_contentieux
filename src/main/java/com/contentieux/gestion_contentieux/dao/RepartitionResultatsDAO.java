package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.RepartitionResultats;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepartitionResultatsDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public void add(RepartitionResultats res) {
        String sql = "INSERT INTO repartition_resultats(" +
                "encaissement_id, produit_disponible, part_indicateur, produit_net, part_flcf, part_tresor, " +
                "produit_net_droits, part_chefs, part_saisissants, part_mutuelle, part_masse_commune, part_interessement) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, res.getEncaissementId());
            pstmt.setDouble(2, res.getProduitDisponible());
            pstmt.setDouble(3, res.getPartIndicateur());
            pstmt.setDouble(4, res.getProduitNet());
            pstmt.setDouble(5, res.getPartFlcf());
            pstmt.setDouble(6, res.getPartTresor());
            pstmt.setDouble(7, res.getProduitNetDroits());
            pstmt.setDouble(8, res.getPartChefs());
            pstmt.setDouble(9, res.getPartSaisissants());
            pstmt.setDouble(10, res.getPartMutuelle());
            pstmt.setDouble(11, res.getPartMasseCommune());
            pstmt.setDouble(12, res.getPartInteressement());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
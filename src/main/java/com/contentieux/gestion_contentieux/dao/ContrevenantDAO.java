package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Contrevenant;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContrevenantDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public List<Contrevenant> getAll() {
        List<Contrevenant> contrevenants = new ArrayList<>();
        String sql = "SELECT * FROM contrevenants ORDER BY nom_complet";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contrevenants.add(new Contrevenant(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("nom_complet"),
                        rs.getString("adresse"),
                        rs.getString("telephone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrevenants;
    }

    public void add(Contrevenant c) {
        String sql = "INSERT INTO contrevenants(code, nom_complet, adresse, telephone) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, c.getCode());
            pstmt.setString(2, c.getNomComplet());
            pstmt.setString(3, c.getAdresse());
            pstmt.setString(4, c.getTelephone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Contrevenant c) {
        String sql = "UPDATE contrevenants SET code = ?, nom_complet = ?, adresse = ?, telephone = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, c.getCode());
            pstmt.setString(2, c.getNomComplet());
            pstmt.setString(3, c.getAdresse());
            pstmt.setString(4, c.getTelephone());
            pstmt.setInt(5, c.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM contrevenants WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
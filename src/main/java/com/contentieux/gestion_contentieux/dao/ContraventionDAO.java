package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Contravention;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContraventionDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public List<Contravention> getAll() {
        List<Contravention> contraventions = new ArrayList<>();
        String sql = "SELECT * FROM contraventions ORDER BY libelle";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contraventions.add(new Contravention(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("libelle"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contraventions;
    }

    public void add(Contravention c) {
        String sql = "INSERT INTO contraventions(code, libelle, description) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, c.getCode());
            pstmt.setString(2, c.getLibelle());
            pstmt.setString(3, c.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Contravention c) {
        String sql = "UPDATE contraventions SET code = ?, libelle = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, c.getCode());
            pstmt.setString(2, c.getLibelle());
            pstmt.setString(3, c.getDescription());
            pstmt.setInt(4, c.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM contraventions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
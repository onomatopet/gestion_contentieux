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
                        rs.getString("libelle")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contraventions;
    }
    // ... autres méthodes CRUD si besoin
}
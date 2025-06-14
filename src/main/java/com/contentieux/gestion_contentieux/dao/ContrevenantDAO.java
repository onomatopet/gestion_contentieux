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
                        rs.getString("nom_complet")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrevenants;
    }
    // ... autres méthodes CRUD si besoin
}
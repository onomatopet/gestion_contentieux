package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Agent;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public List<Agent> getAll() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agents WHERE actif = 1 ORDER BY nom, prenom";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                agents.add(new Agent(
                        rs.getInt("id"),
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("grade"),
                        rs.getInt("service_id"),
                        rs.getBoolean("actif")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }
    // On ajoutera les méthodes add, update, delete plus tard si on fait un écran de gestion dédié
}
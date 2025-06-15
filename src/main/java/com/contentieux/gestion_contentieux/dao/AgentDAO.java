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
        String sql = "SELECT * FROM agents ORDER BY nom, prenom";
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

    public void add(Agent agent) {
        String sql = "INSERT INTO agents(matricule, nom, prenom, grade, service_id, actif) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, agent.getMatricule());
            pstmt.setString(2, agent.getNom());
            pstmt.setString(3, agent.getPrenom());
            pstmt.setString(4, agent.getGrade());
            pstmt.setInt(5, agent.getServiceId());
            pstmt.setBoolean(6, agent.isActif());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Agent agent) {
        String sql = "UPDATE agents SET matricule = ?, nom = ?, prenom = ?, grade = ?, service_id = ?, actif = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, agent.getMatricule());
            pstmt.setString(2, agent.getNom());
            pstmt.setString(3, agent.getPrenom());
            pstmt.setString(4, agent.getGrade());
            pstmt.setInt(5, agent.getServiceId());
            pstmt.setBoolean(6, agent.isActif());
            pstmt.setInt(7, agent.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM agents WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
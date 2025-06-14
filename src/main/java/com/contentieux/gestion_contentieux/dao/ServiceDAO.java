package com.contentieux.gestion_contentieux.dao;

import com.contentieux.gestion_contentieux.model.Service;
import com.contentieux.gestion_contentieux.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private final Connection connection = DatabaseConnection.getInstance();

    public List<Service> getAll() {
        List<Service> services = new ArrayList<>();
        // Jointure pour récupérer le nom du centre en plus, ce qui est souvent utile
        String sql = "SELECT s.*, c.nom_centre FROM services s JOIN centres c ON s.centre_id = c.id ORDER BY s.nom_service";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Service service = new Service(
                        rs.getInt("id"),
                        rs.getString("code_service"),
                        rs.getString("nom_service"),
                        rs.getInt("centre_id")
                );
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur complète
        }
        return services;
    }

    public void add(Service service) {
        String sql = "INSERT INTO services(code_service, nom_service, centre_id) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, service.getCodeService());
            pstmt.setString(2, service.getNomService());
            pstmt.setInt(3, service.getCentreId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Service service) {
        String sql = "UPDATE services SET code_service = ?, nom_service = ?, centre_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, service.getCodeService());
            pstmt.setString(2, service.getNomService());
            pstmt.setInt(3, service.getCentreId());
            pstmt.setInt(4, service.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM services WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
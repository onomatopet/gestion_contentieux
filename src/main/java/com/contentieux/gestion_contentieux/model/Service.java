package com.contentieux.gestion_contentieux.model;

public class Service {
    private int id;
    private String codeService;
    private String nomService;
    private int centreId; // On garde l'ID pour la BDD

    // On peut aussi garder l'objet Centre pour un accès facile
    // mais il ne sera pas stocké directement en BDD.
    // On peut le charger au besoin. Pour l'instant, restons simple.

    public Service() {
    }

    public Service(String codeService, String nomService, int centreId) {
        this.codeService = codeService;
        this.nomService = nomService;
        this.centreId = centreId;
    }

    public Service(int id, String codeService, String nomService, int centreId) {
        this.id = id;
        this.codeService = codeService;
        this.nomService = nomService;
        this.centreId = centreId;
    }

    // --- Getters et Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodeService() { return codeService; }
    public void setCodeService(String codeService) { this.codeService = codeService; }
    public String getNomService() { return nomService; }
    public void setNomService(String nomService) { this.nomService = nomService; }
    public int getCentreId() { return centreId; }
    public void setCentreId(int centreId) { this.centreId = centreId; }

    @Override
    public String toString() {
        return nomService;
    }
}
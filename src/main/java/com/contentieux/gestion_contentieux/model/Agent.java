package com.contentieux.gestion_contentieux.model;

public class Agent {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String grade;
    private int serviceId;
    private boolean actif;

    // Constructeurs, Getters, Setters...
    public Agent() {}

    public Agent(int id, String matricule, String nom, String prenom, String grade, int serviceId, boolean actif) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.serviceId = serviceId;
        this.actif = actif;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + matricule + ")";
    }
}
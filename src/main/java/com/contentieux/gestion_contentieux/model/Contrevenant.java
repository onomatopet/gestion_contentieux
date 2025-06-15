package com.contentieux.gestion_contentieux.model;

public class Contrevenant {
    private int id;
    private String code;
    private String nomComplet;
    private String adresse;
    private String telephone;

    // Constructeur vide, important pour JavaFX
    public Contrevenant() {
    }

    // Constructeur complet qui manquait !
    // Utilisé pour créer des objets à partir de la base de données.
    public Contrevenant(int id, String code, String nomComplet, String adresse, String telephone) {
        this.id = id;
        this.code = code;
        this.nomComplet = nomComplet;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Getters et Setters pour tous les champs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return nomComplet + " (" + code + ")";
    }
}
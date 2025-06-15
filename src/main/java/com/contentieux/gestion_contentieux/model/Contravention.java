package com.contentieux.gestion_contentieux.model;

public class Contravention {
    private int id;
    private String code;
    private String libelle;
    private String description;

    public Contravention() {}

    public Contravention(int id, String code, String libelle, String description) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return libelle; // Pour l'affichage dans les ComboBox
    }
}
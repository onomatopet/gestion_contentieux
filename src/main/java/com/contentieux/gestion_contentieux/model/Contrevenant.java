package com.contentieux.gestion_contentieux.model;

public class Contrevenant {
    private int id;
    private String code;
    private String nomComplet;
    // ... autres champs

    public Contrevenant() {}

    public Contrevenant(int id, String code, String nomComplet) {
        this.id = id;
        this.code = code;
        this.nomComplet = nomComplet;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }

    @Override
    public String toString() {
        return nomComplet + " (" + code + ")";
    }
}
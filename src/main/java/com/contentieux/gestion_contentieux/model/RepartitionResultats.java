package com.contentieux.gestion_contentieux.model;

public class RepartitionResultats {
    private int id;
    private int encaissementId;
    private double produitDisponible;
    private double partIndicateur;
    private double produitNet;
    private double partFlcf;
    private double partTresor;
    private double produitNetDroits;
    private double partChefs;
    private double partSaisissants;
    private double partMutuelle;
    private double partMasseCommune;
    private double partInteressement;

    // Constructeur vide, Getters et Setters pour tous les champs...
    public RepartitionResultats() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEncaissementId() { return encaissementId; }
    public void setEncaissementId(int encaissementId) { this.encaissementId = encaissementId; }
    public double getProduitDisponible() { return produitDisponible; }
    public void setProduitDisponible(double produitDisponible) { this.produitDisponible = produitDisponible; }
    public double getPartIndicateur() { return partIndicateur; }
    public void setPartIndicateur(double partIndicateur) { this.partIndicateur = partIndicateur; }
    public double getProduitNet() { return produitNet; }
    public void setProduitNet(double produitNet) { this.produitNet = produitNet; }
    public double getPartFlcf() { return partFlcf; }
    public void setPartFlcf(double partFlcf) { this.partFlcf = partFlcf; }
    public double getPartTresor() { return partTresor; }
    public void setPartTresor(double partTresor) { this.partTresor = partTresor; }
    public double getProduitNetDroits() { return produitNetDroits; }
    public void setProduitNetDroits(double produitNetDroits) { this.produitNetDroits = produitNetDroits; }
    public double getPartChefs() { return partChefs; }
    public void setPartChefs(double partChefs) { this.partChefs = partChefs; }
    public double getPartSaisissants() { return partSaisissants; }
    public void setPartSaisissants(double partSaisissants) { this.partSaisissants = partSaisissants; }
    public double getPartMutuelle() { return partMutuelle; }
    public void setPartMutuelle(double partMutuelle) { this.partMutuelle = partMutuelle; }
    public double getPartMasseCommune() { return partMasseCommune; }
    public void setPartMasseCommune(double partMasseCommune) { this.partMasseCommune = partMasseCommune; }
    public double getPartInteressement() { return partInteressement; }
    public void setPartInteressement(double partInteressement) { this.partInteressement = partInteressement; }
}
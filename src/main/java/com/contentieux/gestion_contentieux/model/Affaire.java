package com.contentieux.gestion_contentieux.model;

import java.time.LocalDate;

public class Affaire {
    private int id;
    private String numeroAffaire;
    private LocalDate dateCreation;
    private double montantAmendeTotal;
    private String statut;
    private int contrevenantId;
    private int contraventionId;
    private int serviceConstatId;

    public Affaire() {}

    // Constructeur utilisé pour créer une nouvelle affaire avant de l'insérer
    public Affaire(String numeroAffaire, LocalDate dateCreation, double montantAmendeTotal,
                   int contrevenantId, int contraventionId, int serviceConstatId) {
        this.numeroAffaire = numeroAffaire;
        this.dateCreation = dateCreation;
        this.montantAmendeTotal = montantAmendeTotal;
        this.contrevenantId = contrevenantId;
        this.contraventionId = contraventionId;
        this.serviceConstatId = serviceConstatId;
    }

    // Getters et Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroAffaire() { return numeroAffaire; }
    public void setNumeroAffaire(String numeroAffaire) { this.numeroAffaire = numeroAffaire; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public double getMontantAmendeTotal() { return montantAmendeTotal; }
    public void setMontantAmendeTotal(double montantAmendeTotal) { this.montantAmendeTotal = montantAmendeTotal; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public int getContrevenantId() { return contrevenantId; }
    public void setContrevenantId(int contrevenantId) { this.contrevenantId = contrevenantId; }
    public int getContraventionId() { return contraventionId; }
    public void setContraventionId(int contraventionId) { this.contraventionId = contraventionId; }
    public int getServiceConstatId() { return serviceConstatId; }
    public void setServiceConstatId(int serviceConstatId) { this.serviceConstatId = serviceConstatId; }
}
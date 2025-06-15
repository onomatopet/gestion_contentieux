package com.contentieux.gestion_contentieux.model;

import java.time.LocalDate;

public class Encaissement {
    private int id;
    private String numeroEncaissement;
    private String numeroMandat;
    private LocalDate dateEncaissement;
    private double montantEncaisse;
    private String modeReglement;
    private String banquePaiement;
    private String numeroCheque;
    private int affaireId;

    public Encaissement() {}

    // Constructeur pour un nouvel encaissement
    public Encaissement(String numeroEncaissement, String numeroMandat, LocalDate dateEncaissement, double montantEncaisse,
                        String modeReglement, String banquePaiement, String numeroCheque, int affaireId) {
        this.numeroEncaissement = numeroEncaissement;
        this.numeroMandat = numeroMandat;
        this.dateEncaissement = dateEncaissement;
        this.montantEncaisse = montantEncaisse;
        this.modeReglement = modeReglement;
        this.banquePaiement = banquePaiement;
        this.numeroCheque = numeroCheque;
        this.affaireId = affaireId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroEncaissement() { return numeroEncaissement; }
    public void setNumeroEncaissement(String numeroEncaissement) { this.numeroEncaissement = numeroEncaissement; }
    public String getNumeroMandat() { return numeroMandat; }
    public void setNumeroMandat(String numeroMandat) { this.numeroMandat = numeroMandat; }
    public LocalDate getDateEncaissement() { return dateEncaissement; }
    public void setDateEncaissement(LocalDate dateEncaissement) { this.dateEncaissement = dateEncaissement; }
    public double getMontantEncaisse() { return montantEncaisse; }
    public void setMontantEncaisse(double montantEncaisse) { this.montantEncaisse = montantEncaisse; }
    public String getModeReglement() { return modeReglement; }
    public void setModeReglement(String modeReglement) { this.modeReglement = modeReglement; }
    public String getBanquePaiement() { return banquePaiement; }
    public void setBanquePaiement(String banquePaiement) { this.banquePaiement = banquePaiement; }
    public String getNumeroCheque() { return numeroCheque; }
    public void setNumeroCheque(String numeroCheque) { this.numeroCheque = numeroCheque; }
    public int getAffaireId() { return affaireId; }
    public void setAffaireId(int affaireId) { this.affaireId = affaireId; }
}
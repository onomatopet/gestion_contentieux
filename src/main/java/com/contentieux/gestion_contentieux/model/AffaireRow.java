package com.contentieux.gestion_contentieux.model;

import java.time.LocalDate;

/**
 * Classe représentant une ligne dans le tableau de suivi des affaires.
 * Elle contient des données dénormalisées pour un affichage facile.
 */
public class AffaireRow {
    private int affaireId;
    private String numero;
    private LocalDate date;
    private String contrevenant;
    private String contravention;
    private double montantAmende;
    private double montantEncaisse;
    private String statut;

    public AffaireRow(int affaireId, String numero, LocalDate date, String contrevenant, String contravention, double montantAmende, double montantEncaisse, String statut) {
        this.affaireId = affaireId;
        this.numero = numero;
        this.date = date;
        this.contrevenant = contrevenant;
        this.contravention = contravention;
        this.montantAmende = montantAmende;
        this.montantEncaisse = montantEncaisse;
        this.statut = statut;
    }

    // Getters
    public int getAffaireId() { return affaireId; }
    public String getNumero() { return numero; }
    public LocalDate getDate() { return date; }
    public String getContrevenant() { return contrevenant; }
    public String getContravention() { return contravention; }
    public double getMontantAmende() { return montantAmende; }
    public double getMontantEncaisse() { return montantEncaisse; }
    public String getStatut() { return statut; }
}
package com.contentieux.gestion_contentieux.model;

public class Centre {
    private int id;
    private String codeCentre;
    private String nomCentre;

    // Constructeur par défaut (important pour certaines librairies)
    public Centre() {
    }

    // Constructeur pour créer un nouvel objet Centre (l'id sera généré par la BDD)
    public Centre(String codeCentre, String nomCentre) {
        this.codeCentre = codeCentre;
        this.nomCentre = nomCentre;
    }

    // Constructeur complet (pour lire depuis la BDD)
    public Centre(int id, String codeCentre, String nomCentre) {
        this.id = id;
        this.codeCentre = codeCentre;
        this.nomCentre = nomCentre;
    }

    // --- Getters et Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCentre() {
        return codeCentre;
    }

    public void setCodeCentre(String codeCentre) {
        this.codeCentre = codeCentre;
    }

    public String getNomCentre() {
        return nomCentre;
    }

    public void setNomCentre(String nomCentre) {
        this.nomCentre = nomCentre;
    }

    // Méthode utile pour l'affichage dans les ComboBox
    @Override
    public String toString() {
        return nomCentre; // On affiche le nom du centre
    }
}
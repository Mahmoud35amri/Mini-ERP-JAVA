package com.minierp.model;

import java.util.Date;
import java.util.Objects;

public class LigneCommande {

    private int id;
    private Commande commande;
    private Produit produit;
    private String designation;
    private int quantite;
    private int quantiteLivree;
    private double prixUnitaireHT;
    private double tauxRemise; // en pourcentage
    private double montantRemise;
    private double tauxTVA; // en pourcentage
    private double montantHT;
    private double montantTVA;
    private double montantTTC;
    private String observations;
    private Date dateCreation;

    public LigneCommande() {
        this.dateCreation = new Date();
    }

    public LigneCommande( Produit produit, int quantite, double prixUnitaireHT, double tauxTVA) {
        this();
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaireHT = prixUnitaireHT;
        this.tauxTVA = tauxTVA;
        recalculer();
    }

    // getters / setters (omitted here for brevity — add as needed)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; recalculer(); }
    public int getQuantiteLivree() { return quantiteLivree; }
    public void setQuantiteLivree(int quantiteLivree) { this.quantiteLivree = quantiteLivree; }
    public double getPrixUnitaireHT() { return prixUnitaireHT; }
    public void setPrixUnitaireHT(double prixUnitaireHT) { this.prixUnitaireHT = prixUnitaireHT; recalculer(); }
    public double getTauxRemise() { return tauxRemise; }
    public void setTauxRemise(double tauxRemise) { this.tauxRemise = tauxRemise; recalculer(); }
    public double getMontantRemise() { return montantRemise; }
    public double getTauxTVA() { return tauxTVA; }
    public void setTauxTVA(double tauxTVA) { this.tauxTVA = tauxTVA; recalculer(); }
    public double getMontantHT() { return montantHT; }
    public double getMontantTVA() { return montantTVA; }
    public double getMontantTTC() { return montantTTC; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    //public Date getDateCreation() { return dateCreation; }
    //public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    // Calculs
    public double calculerMontantHT() {
        double total = prixUnitaireHT * quantite;
        montantRemise = total * (tauxRemise / 100.0);
        montantHT = total - montantRemise;
        return montantHT;
    }

    public double calculerMontantTVA() {
        montantTVA = montantHT * (tauxTVA / 100.0);
        return montantTVA;
    }

    public double calculerMontantTTC() {
        montantTTC = montantHT + montantTVA;
        return montantTTC;
    }

    public void appliquerRemise(double taux) {
        this.tauxRemise = taux;
        recalculer();
    }

    public boolean estCompletementLivree() {
        return quantiteLivree >= quantite;
    }

    public int getQuantiteRestante() {
        return Math.max(0, quantite - quantiteLivree);
    }

    public void recalculer() {
        calculerMontantHT();
        calculerMontantTVA();
        calculerMontantTTC();
    }
    public boolean valider() {
        if (produit == null) return false;
        if (quantite <= 0) return false;
        if (prixUnitaireHT < 0) return false;
        return true;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "id=" + id +
                ", produit=" + (produit != null ? produit.getReference() : "null") +
                ", qte=" + quantite +
                ", prixHT=" + prixUnitaireHT +
                ", montantHT=" + montantHT +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommande)) return false;
        LigneCommande that = (LigneCommande) o;
        return id == that.id || (produit != null && produit.equals(that.produit));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produit);
    }
}

package com.minierp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Stock {

    private int id;
    private Produit produit;
    private int quantiteInitiale;
    private int quantiteActuelle;
    private int quantiteReservee;
    private int quantiteDisponible;
    private double valeurStock;
    private String emplacement;
    private Date dateInventaire;
    private List<MouvementStock> mouvements;

    public Stock() {
        this.mouvements = new ArrayList<>();
        this.dateInventaire = new Date();
    }

    public Stock(Produit produit, int quantiteInitiale) {
        this();
        this.produit = produit;
        this.quantiteInitiale = quantiteInitiale;
        this.quantiteActuelle = quantiteInitiale;
        calculerQuantiteDisponible();
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public int getQuantiteInitiale() { return quantiteInitiale; }
    public void setQuantiteInitiale(int quantiteInitiale) { this.quantiteInitiale = quantiteInitiale; }
    public int getQuantiteActuelle() { return quantiteActuelle; }
    public void setQuantiteActuelle(int quantiteActuelle) { this.quantiteActuelle = quantiteActuelle; calculerQuantiteDisponible(); }
    public int getQuantiteReservee() { return quantiteReservee; }
    public void setQuantiteReservee(int quantiteReservee) { this.quantiteReservee = quantiteReservee; calculerQuantiteDisponible(); }
    public int getQuantiteDisponible() { return quantiteDisponible; }
    public double getValeurStock() { return valeurStock; }
    public void setValeurStock(double valeurStock) { this.valeurStock = valeurStock; }
    public String getEmplacement() { return emplacement; }
    public void setEmplacement(String emplacement) { this.emplacement = emplacement; }
    public Date getDateInventaire() { return dateInventaire; }
    public List<MouvementStock> getMouvements() { return mouvements; }

    public int calculerQuantiteDisponible() {
        this.quantiteDisponible = Math.max(0, quantiteActuelle - quantiteReservee);
        return this.quantiteDisponible;
    }

    public double calculerValeurStock() {
        if (produit == null) return 0.0;
        this.valeurStock = produit.getPrixAchat() * quantiteActuelle;
        return this.valeurStock;
    }

    public void ajouter(int quantite, String motif, Utilisateur utilisateur) {
        int avant = this.quantiteActuelle;
        this.quantiteActuelle += quantite;
        calculerQuantiteDisponible();
        MouvementStock m = new MouvementStock(produit, com.minierp.model.StatutsEnums.TypeMouvement.ENTREE, quantite, avant, utilisateur, motif, "AJOUT");
        m.setQuantiteApres(this.quantiteActuelle);
        mouvements.add(m);
        if (produit != null) produit.ajusterStock(quantite); 
    }

    public void retirer(int quantite, String motif, Utilisateur utilisateur) {
        int avant = this.quantiteActuelle;
        int toRemove = Math.min(quantite, this.quantiteActuelle);
        this.quantiteActuelle -= toRemove;
        calculerQuantiteDisponible();
        MouvementStock m = new MouvementStock(produit, com.minierp.model.StatutsEnums.TypeMouvement.SORTIE, -toRemove, avant, utilisateur, motif, "RETRAIT");
        m.setQuantiteApres(this.quantiteActuelle);
        mouvements.add(m);
        if (produit != null) produit.ajusterStock(-toRemove);
    }

    public void reserver(int quantite) {
        int canReserve = Math.min(quantite, calculerQuantiteDisponible());
        this.quantiteReservee += canReserve;
        calculerQuantiteDisponible();
       
    }

    public void libererReservation(int quantite) {
        int toFree = Math.min(quantite, this.quantiteReservee);
        this.quantiteReservee -= toFree;
        calculerQuantiteDisponible();
    }

    public void ajuster(int quantite, String motif, Utilisateur utilisateur) {
        int avant = this.quantiteActuelle;
        this.quantiteActuelle = Math.max(0, quantite);
        calculerQuantiteDisponible();
        MouvementStock m = new MouvementStock(produit, com.minierp.model.StatutsEnums.TypeMouvement.AJUSTEMENT, this.quantiteActuelle - avant, avant, utilisateur, motif, "AJUST");
        m.setQuantiteApres(this.quantiteActuelle);
        mouvements.add(m);
    }

    public void inventorier() {
        this.dateInventaire = new Date();
        this.quantiteInitiale = this.quantiteActuelle;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "produit=" + (produit != null ? produit.getReference() : "null") +
                ", qteActuelle=" + quantiteActuelle +
                ", disponible=" + quantiteDisponible +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock s = (Stock) o;
        return id == s.id || Objects.equals(produit, s.produit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produit);
    }
}

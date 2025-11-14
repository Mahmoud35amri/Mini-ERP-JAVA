package com.minierp.model;

import java.util.Date;
import java.util.Objects;
import com.minierp.model.StatutsEnums.TypeMouvement;

public class MouvementStock {

    private int id;
    private Produit produit;
    private StatutsEnums.TypeMouvement typeMouvement;
    private int quantite;
    private int quantiteAvant;
    private int quantiteApres;
    private String motif;
    private String reference;
    private Utilisateur utilisateur;
    private Date dateCreation;

    public MouvementStock() {
        this.dateCreation = new Date();
    }

    public MouvementStock(Produit produit, TypeMouvement type, int quantite, int avant, Utilisateur utilisateur, String motif, String reference) {
        this();
        this.produit = produit;
        this.typeMouvement = type;
        this.quantite = quantite;
        this.quantiteAvant = avant;
        this.quantiteApres = avant + quantite;
        this.utilisateur = utilisateur;
        this.motif = motif;
        this.reference = reference;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public TypeMouvement getTypeMouvement() { return typeMouvement; }
    public void setTypeMouvement(TypeMouvement typeMouvement) { this.typeMouvement = typeMouvement; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public int getQuantiteAvant() { return quantiteAvant; }
    public void setQuantiteAvant(int quantiteAvant) { this.quantiteAvant = quantiteAvant; }
    public int getQuantiteApres() { return quantiteApres; }
    public void setQuantiteApres(int quantiteApres) { this.quantiteApres = quantiteApres; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) {this.dateCreation = dateCreation;
}


    @Override
    public String toString() {
        return "MouvementStock{" +
                "produit=" + (produit != null ? produit.getReference() : "null") +
                ", type=" + typeMouvement +
                ", qte=" + quantite +
                ", avant=" + quantiteAvant +
                ", apres=" + quantiteApres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MouvementStock)) return false;
        MouvementStock m = (MouvementStock) o;
        return id == m.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

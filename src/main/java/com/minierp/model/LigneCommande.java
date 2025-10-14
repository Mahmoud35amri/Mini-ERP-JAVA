/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;

public class LigneCommande {
    private final String id;
    private  String commandeId;
    private String produitId;
    private int quantite;
    private double prixUnitaire;
    private double sousTotal;

    public LigneCommande(String commandeId, String produitId, int quantite, double prixUnitaire) {
        this.id = IdGenerator.generateOrderLineId();
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.sousTotal = quantite * prixUnitaire;
    }

    public String getId() { return id; }
    public String getCommandeId() { return commandeId; }
    public String getProduitId() { return produitId; }
    public int getQuantite() { return quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public double getSousTotal() { return sousTotal; }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
        this.sousTotal = quantite * this.prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        this.sousTotal = this.quantite * prixUnitaire;
    }
    public void setProduitId(String produitId) {
        this.produitId = produitId;
    }

    public void setCommandeId(String commandeId) {
        this.commandeId = commandeId;
    }

    @Override
    public String toString() {
        return "LigneCommande: Produit=" + produitId + " | Qte=" + quantite +
               " | PrixU=" + prixUnitaire + " | SousTotal=" + sousTotal;
    }

    
}

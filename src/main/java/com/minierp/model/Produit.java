/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;

public class Produit {
    private final String id;
    private String nom;
    private String description;
    private double prixUnitaire;
    private int quantiteStock;
    private String categorie;
    private String fournisseurId;
    private String entrepriseId;

    // Constructeur
    public Produit(String nom, String description, double prixUnitaire, int quantiteStock, 
                   String categorie, String fournisseurId, String entrepriseId) {
        this.id = IdGenerator.generateProductId();
        this.nom = nom;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
        this.categorie = categorie;
        this.fournisseurId = fournisseurId;
        this.entrepriseId = entrepriseId;
    }

    // Getters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public int getQuantiteStock() { return quantiteStock; }
    public String getCategorie() { return categorie; }
    public String getFournisseurId() { return fournisseurId; }
    public String getEntrepriseId() { return entrepriseId; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setFournisseurId(String fournisseurId) { this.fournisseurId = fournisseurId; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }

    @Override
    public String toString() {
        return "Produit: " + nom + " | Catégorie: " + categorie +
               " | Prix: " + prixUnitaire + " | Stock: " + quantiteStock +
               " | Fournisseur ID: " + fournisseurId + " | Entreprise ID: " + entrepriseId;
    }
}

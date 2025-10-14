/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;

public class Categorie {
    private final String id;
    private String nom;
    private String description;
    private String entrepriseId;

    public Categorie(String nom, String description, String entrepriseId) {
        this.id = IdGenerator.generateCategoryId();
        this.nom = nom;
        this.description = description;
        this.entrepriseId = entrepriseId;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public String getEntrepriseId() { return entrepriseId; }

    public void setNom(String nom) { this.nom = nom; }
    public void setDescription(String description) { this.description = description; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }

    @Override
    public String toString() {
        return "Categorie: " + nom + " | Description: " + description + 
               " | Entreprise ID: " + entrepriseId;
    }
}

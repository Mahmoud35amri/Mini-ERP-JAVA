/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;

public class Fournisseur {
    private final String id;
    private String nom;
    private String email;
    private String tel;
    private String adresse;
    private String secteur;
    private String entrepriseId;

    // Constructeur
    public Fournisseur(String nom, String email, String tel, String adresse, String secteur, String entrepriseId) {
        this.id = IdGenerator.generateSupplierId();
        this.nom = nom;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.secteur = secteur;
        this.entrepriseId = entrepriseId;
    }

    // Getters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getTel() { return tel; }
    public String getAdresse() { return adresse; }
    public String getSecteur() { return secteur; }
    public String getEntrepriseId() { return entrepriseId; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) { this.email = email; }
    public void setTel(String tel) { this.tel = tel; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setSecteur(String secteur) { this.secteur = secteur; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }

    // toString
    @Override
    public String toString() {
        return "Fournisseur: " + nom + " | Email: " + email + " | Tel: " + tel +
               " | Secteur: " + secteur + " | Entreprise ID: " + entrepriseId;
    }
}

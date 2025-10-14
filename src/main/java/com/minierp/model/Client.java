/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;


public class Client {
    private final String idC;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String entrepriseId;
    //constructeur
    public Client( String nom, String prenom, String email, String telephone, String adresse, String entrepriseId) {
        this.idC = IdGenerator.generateUserId();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.entrepriseId = entrepriseId;
    }
    //getteurs & setteurs
    //1.getteurs
    public String getId() { return idC; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
    public String getEntrepriseId() { return entrepriseId; }
    //2.setteurs
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }
    //methode d'affichage
    @Override
    public String toString() {
        return "Client: " + nom + " " + prenom +
               " | Email: " + email + " | Tel: " + telephone +
               " | Adresse: " + adresse + " | Entreprise ID: " + entrepriseId;
    }
    
}

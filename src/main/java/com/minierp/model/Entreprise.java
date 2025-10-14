package com.minierp.model;

import com.minierp.util.IdGenerator;

public class Entreprise {
    //attributs
    private final String idEnt; 
    private String nom;
    private String adresse;
    private String telephone;
    private String secteur;
    //constructeur
    public Entreprise(String nom, String adresse, String telephone, String secteur){
        this.idEnt=IdGenerator.generateCompanyId();
        this.nom=nom;
        this.adresse=adresse;
        this.secteur=secteur;
        this.telephone=telephone;
    }
    //getteur & setteur
    //1.getteurs
    public String getId(){return idEnt;}
    public String getNom(){return nom;}
    public String getAdresse(){return adresse;}
    public String getTelephone(){return telephone;}
    public String getSecteur(){return secteur;}
    //2.setteurs
    public void setNom(String nom){this.nom=nom;}
    public void setAdresse(String adresse){this.adresse=adresse;}
    public void setTelephone(String telephone){this.telephone=telephone;}
    public void setSecteur(String secteur){this.secteur=secteur;}
    //methodes
    public boolean valider() {
        return nom != null && !nom.isEmpty() &&
               telephone != null && !telephone.isEmpty() &&
               secteur != null && !secteur.isEmpty();
    }
    public void modifier(String nom, String adresse, String telephone, String secteur) {
        if (valider()) {
            setNom(nom);
            setAdresse(adresse);
            setTelephone(telephone);
            setSecteur(secteur);
        }
    }
    

    //methode d'affichage
    @Override
    public String toString() {
        return "Entreprise: " + nom + " | Secteur: " + secteur +
               " | Adresse: " + adresse + " | Tel: " + telephone;
    }

}

package com.minierp.model;

import com.minierp.util.IdGenerator;

public class Utilisateur {
    //attributs
    private final String idU; 
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;
    private String entrepriseId;
    //constructeur
    public Utilisateur(String nom, String prenom, String email, String motDePasse, String role, String entrepriseId){
        this.idU=IdGenerator.generateUserId();
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.motDePasse=motDePasse;
        this.role=role;
        this.entrepriseId=entrepriseId;
    }
    //getteur & setteur
    //1.getteurs
    public String getId(){return idU;}
    public String getNom(){return nom;}
    public String getAdresse(){return prenom;}
    public String getemail(){return email;}
    public String getmotDePasse(){return motDePasse;}
    public String getrole(){return role;}
    public String getentrepriseId(){return entrepriseId;}
    //2.setteurs
    public void setNom(String nom){this.nom=nom;}
    public void setprenom(String prenom){this.prenom=prenom;}
    public void setemail(String email){this.email=email;}
    public void setmotDePasse(String motDePasse){this.motDePasse=motDePasse;}
    public void setrole(String role){this.role=role;}
    public void setentrepriseId(String entrepriseId){this.entrepriseId=entrepriseId;}
    //methode d'affichage
    @Override
    public String toString() {
        return "Utilisateur: " + nom + " " + prenom +
               " | Email: " + email + " | Role: " + role +
               " | Entreprise ID: " + entrepriseId;
    }

}

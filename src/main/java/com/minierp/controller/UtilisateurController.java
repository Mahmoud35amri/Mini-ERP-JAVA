package com.minierp.controller;

import java.util.List;
import com.minierp.dao.UtilisateurDao;
import com.minierp.model.Utilisateur;
import com.minierp.model.Utilisateur.Role;

public class UtilisateurController {
    private static UtilisateurController instance;
    private final UtilisateurDao utilisateurDao;

    private UtilisateurController() {
        this.utilisateurDao = new UtilisateurDao();
    }
    public static UtilisateurController getInstance() {
        if (instance == null) {
            instance = new UtilisateurController();
        }
        return instance;
    }
    public boolean creer(Utilisateur utilisateur) {
        return utilisateurDao.creer(utilisateur);
    }
    public boolean modifier(Utilisateur utilisateur) {
        return utilisateurDao.modifier(utilisateur);
    }
    public boolean supprimer(int id) {
        return utilisateurDao.supprimer(id);
    }
    public Utilisateur rechercherParId(int id) {
        return utilisateurDao.rechercherParId(id);
    }
    public Utilisateur rechercherParEmail(String email) {
        return utilisateurDao.rechercherParEmail(email);
    }
    public List<Utilisateur> listerTous() {
        return utilisateurDao.listerTous();
    }
    public List<Utilisateur> listerParRole(Role role) {
        return utilisateurDao.listerParRole(role);
    }
    public List<Utilisateur> listerActifs(){
        return utilisateurDao.listerActifs();
    }
    public Utilisateur authentifier(String email, String motDePasse){
        return utilisateurDao.authentifier(email, motDePasse);        
    }

    public boolean changerMotDePasse(int id, String nouveauMotDePasse) {
        return utilisateurDao.changerMotDePasse(id, nouveauMotDePasse);
    }
    public String reinitialiserMotDePasse(int id) {
        return utilisateurDao.reinitialiserMotDePasse(id);
    }
    public boolean verouiller(int id){
        return utilisateurDao.verouiller(id);
    }
    public boolean deverouiller(int id){
        return utilisateurDao.deverouiller(id);
    }
    public int compter(){
        return utilisateurDao.compter();
    }
    public List<Utilisateur> getUtilisateurConnecte() {
        return utilisateurDao.getUtilisateurConnecte();
    }
}
package com.minierp.controller;

import com.minierp.dao.EntrepriseDao;
import com.minierp.model.Entreprise;

import java.util.List;

public class EntrepriseController {

    // === Attributs ===
    private static EntrepriseController instance;
    private final EntrepriseDao entrepriseDao;

    // === Constructeur privé (Singleton) ===
    private EntrepriseController() {
        this.entrepriseDao = new EntrepriseDao();
    }

    // === Singleton ===
    public static EntrepriseController getInstance() {
        if (instance == null) {
            instance = new EntrepriseController();
        }
        return instance;
    }

    // === CRUD ===
    public boolean creer(Entreprise entreprise) {
        return entrepriseDao.creer(entreprise);
    }

    public boolean modifier(Entreprise entreprise) {
        return entrepriseDao.modifier(entreprise);}

    public boolean supprimer(int id) {
        return entrepriseDao.supprimer(id);
    }

    // === RECHERCHES ===
    public Entreprise rechercherParId(int id) {
        return entrepriseDao.rechercherParId(id);
    }

    public List<Entreprise> rechercherParNom(String nom) {
        return entrepriseDao.rechercherParNom(nom);
    }

    public List<Entreprise> listerTout() {
        return entrepriseDao.listerTout();
    }

    public List<Entreprise> listerActives() {
        return entrepriseDao.listerActives();
    }

    public int compter() {
        return entrepriseDao.compter();
    }

}
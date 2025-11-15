package com.minierp.controller;

import com.minierp.dao.EntrepriseDao;
import com.minierp.model.Entreprise;

import java.util.List;

public class EntrepriseController {
    private static EntrepriseController instance;
    private final EntrepriseDao entrepriseDao;

    private EntrepriseController() {this.entrepriseDao = new EntrepriseDao();}
    public static EntrepriseController getInstance() {
        if (instance == null) {instance = new EntrepriseController();}
        return instance;}
    public boolean creer(Entreprise entreprise) {return entrepriseDao.creer(entreprise);}
    public boolean modifier(Entreprise entreprise) {return entrepriseDao.modifier(entreprise);}
    public boolean supprimer(int id) {return entrepriseDao.supprimer(id);}
    public Entreprise rechercherParId(int id) {return entrepriseDao.rechercherParId(id);}
    public List<Entreprise> rechercherParNom(String nom) {return entrepriseDao.rechercherParNom(nom);}
    public List<Entreprise> listerTout() {return entrepriseDao.listerTout();}
    public List<Entreprise> listerActives() {return entrepriseDao.listerActives();}
    public int compter() {return entrepriseDao.compter();}

}
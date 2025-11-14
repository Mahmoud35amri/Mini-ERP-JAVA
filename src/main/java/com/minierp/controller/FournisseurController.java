package com.minierp.controller;

import com.minierp.dao.FournisseurDao;
import com.minierp.model.Fournisseur;

import java.util.List;


public class FournisseurController {

    private static FournisseurController instance;

    private final FournisseurDao fournisseurDao;

    private FournisseurController() {
        fournisseurDao = new FournisseurDao();
    }

    public static synchronized FournisseurController getInstance() {
        if (instance == null)
            instance = new FournisseurController();
        return instance;
    }

    public boolean creer(Fournisseur fournisseur) {
        return fournisseurDao.creer(fournisseur);
    }

    public boolean modifier(Fournisseur fournisseur) {
        return fournisseurDao.modifier(fournisseur);
    }

    public boolean supprimer(int id) {
        return fournisseurDao.supprimer(id);
    }

    public Fournisseur rechercherParId(int id) {
        return fournisseurDao.rechercherParId(id);
    }

    public Fournisseur rechercherParCode(String code) {
        return fournisseurDao.rechercherParCode(code);
    }

    public List<Fournisseur> rechercherParNom(String nom) {
        return fournisseurDao.rechercherParNom(nom);
    }

    public List<Fournisseur> listerTout() {
        return fournisseurDao.listerTout();
    }

    public List<Fournisseur> listerActifs() {
        return  fournisseurDao.listerActifs();
    }

    public List<Fournisseur> listerParEvaluation(int note) {
        return fournisseurDao.listerParEvaluation(note);
    }
    public double calculerTotalAchats(int id) {
        return fournisseurDao.calculerTotalAchats(id);
    }

    public boolean evaluer(int id, int note) {
        return fournisseurDao.evaluer(id, note);
    }

    public int compter() {
        return fournisseurDao.compter();
    }
}

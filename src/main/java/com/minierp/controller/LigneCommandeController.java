package com.minierp.controller;

import com.minierp.dao.LigneCommandeDao;
import com.minierp.model.LigneCommande;

import java.util.List;

public class LigneCommandeController {

    private static LigneCommandeController instance;
    private final LigneCommandeDao ligneCommandeDao;

    private LigneCommandeController() {
        ligneCommandeDao = new LigneCommandeDao();
    }

    public static synchronized LigneCommandeController getInstance() {
        if (instance == null) instance = new LigneCommandeController();
        return instance;
    }

    public boolean creer(LigneCommande l) {
        return ligneCommandeDao.creer(l);
    }

    public boolean modifier(LigneCommande l) {
        return ligneCommandeDao.modifier(l);
    }

    public boolean supprimer(int id) {
        return ligneCommandeDao.supprimer(id);
    }

    public LigneCommande rechercherParId(int id) {
        return ligneCommandeDao.rechercherParId(id);
    }

    public List<LigneCommande> listerParCommande(int idCommande) {
        return ligneCommandeDao.listerParCommande(idCommande);
    }

    public List<LigneCommande> listerParProduit(int idProduit) {
        return ligneCommandeDao.listerParProduit(idProduit);
    }

    public double calculerMontantTotal(int idCommande) {
        return ligneCommandeDao.calculerMontantTotal(idCommande);
    }

    public boolean verifierDisponibilite(LigneCommande l) {
        return ligneCommandeDao.verifierDisponibilite(l);
    }

    public int compter() {
        return ligneCommandeDao.compter();
    }
}

package com.minierp.controller;

import com.minierp.dao.ProduitDao;
import com.minierp.model.Produit;
import com.minierp.model.Categorie;
import com.minierp.model.Fournisseur;

import java.util.List;
import java.util.Date;

public class ProduitController {

    private static ProduitController instance;
    private final ProduitDao produitDao;

    private ProduitController() {
        produitDao = new ProduitDao();
    }

    public static synchronized ProduitController getInstance() {
        if (instance == null)
            instance = new ProduitController();
        return instance;
    }

    public boolean creer(Produit p) {
        return produitDao.creer(p);
    }

    public boolean modifier(Produit p) {
        return produitDao.modifier(p);
    }

    public boolean supprimer(int id) {
        return produitDao.supprimer(id);
    }

    public Produit rechercherParId(int id) {
        return produitDao.rechercherParId(id);
    }

    public Produit rechercherParReference(String reference) {
        return produitDao.rechercherParReference(reference);
    }

    public Produit rechercherParCodeBarres(String codeBarres) {
        return produitDao.rechercherParCodeBarres(codeBarres);
    }

    public List<Produit> rechercherParNom(String nom) {
        return produitDao.rechercherParNom(nom);
    }

    public List<Produit> listerTout() {
        return produitDao.listerTout();
    }

    public List<Produit> listerActifs() {
        return produitDao.listerActifs();
    }

    public List<Produit> listerParCategorie(Categorie c) {
        return produitDao.listerParCategorie(c);
    }

    public List<Produit> listerParFournisseur(Fournisseur f) {
        return produitDao.listerParFournisseur(f);
    }

    public List<Produit> listerEnRupture() {
        return produitDao.listerEnRupture();
    }

    public List<Produit> listerSousSeuilAlerte() {
        return produitDao.listerSousSeuilAlerte();
    }

    public List<Produit> listerEnPromotion() {
        return produitDao.listerEnPromotion();
    }

    public List<Produit> listerNouveautes() {
        return produitDao.listerNouveautes();
    }


    public boolean ajusterStock(int id, int quantite, String motif) {
        return produitDao.ajusterStock(id, quantite, motif);
    }

    public boolean appliquerPromotion(int id, double taux, Date dateFin) {
        return produitDao.appliquerPromotion(id, taux, dateFin);
    }

    public boolean annulerPromotion(int id) {
        return produitDao.annulerPromotion(id);
    }

    public double calculerValeurStock() {
        return produitDao.calculerValeurStock();
    }

    public int compter() {
        return produitDao.compter();
    }
}

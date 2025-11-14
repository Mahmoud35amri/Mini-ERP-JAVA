package com.minierp.controller;

import com.minierp.dao.StockDao;
import com.minierp.model.MouvementStock;
import com.minierp.model.Produit;
import com.minierp.model.Stock;

import java.util.Date;
import java.util.List;

public class StockController {
    private static StockController instance;
    private final StockDao stockDao = new StockDao();

    private StockController() {}

    public static StockController getInstance() {
        if (instance == null) instance = new StockController();
        return instance;
    }

    public boolean initialiser(int idProduit, int quantite) {
        return stockDao.initialiser(idProduit, quantite);
    }

    public Stock getStock(int idProduit) {
        return stockDao.getStock(idProduit);
    }

    public boolean ajouterStock(int idProduit, int quantite, String motif) {
        return stockDao.ajouterStock(idProduit, quantite, motif);
    }

    public boolean retirerStock(int idProduit, int quantite, String motif) {
        return stockDao.retirerStock(idProduit, quantite, motif);
    }

    public boolean ajusterStock(int idProduit, int quantite, String motif) {
        return stockDao.ajusterStock(idProduit, quantite, motif);
    }

    public boolean reserverStock(int idProduit, int quantite) {
        return stockDao.reserverStock(idProduit, quantite);
    }

    public boolean libererReservation(int idProduit, int quantite) {
        return stockDao.libererReservation(idProduit, quantite);
    }

    public boolean transferer(int idProduitSource, int idProduitDest, int quantite) {
        return stockDao.transferer(idProduitSource, idProduitDest, quantite);
    }

    public boolean inventorier(int idProduit, int quantiteReelle) {
        return stockDao.inventorier(idProduit, quantiteReelle);
    }

    public boolean inventorierTout() {
        return stockDao.inventorierTout();
    }

    public List<MouvementStock> getMouvements(int idProduit) {
        return stockDao.getMouvements(idProduit);
    }

    public List<MouvementStock> getMouvementsParPeriode(Date debut, Date fin) {
        return stockDao.getMouvementsParPeriode(debut, fin);
    }

    public double calculerValeurStockTotal() {
        return stockDao.calculerValeurStockTotal();
    }

    public List<Produit> listerProduitsEnRupture() {
        return stockDao.listerProduitsEnRupture();
    }

    public List<Produit> listerProduitsSousSeuilAlerte() {
        return stockDao.listerProduitsSousSeuilAlerte();
    }

    public List<Produit> listerProduitsSousSeuilMinimum() {
        return stockDao.listerProduitsSousSeuilMinimum();
    }
}

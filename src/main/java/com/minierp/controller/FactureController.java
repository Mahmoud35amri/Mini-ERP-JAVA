package com.minierp.controller;

import com.minierp.dao.FactureDao;
import com.minierp.model.Facture;
import com.minierp.model.Commande;

import java.util.Date;
import java.util.List;

import com.minierp.model.StatutsEnums;

public class FactureController {

    private static FactureController instance;
    private final FactureDao factureDao;


    private FactureController() {
        factureDao = new FactureDao();
    }

    public static synchronized FactureController getInstance() {
        if (instance == null) instance = new FactureController();
        return instance;
    }

    public boolean creer(Facture f) {
        return factureDao.creer(f);
    }

    public Facture genererDepuisCommande(Commande c) {
        return factureDao.genererDepuisCommande(c);
    }

    public boolean modifier(Facture f) {
        return factureDao.modifier(f);
    }

    public boolean supprimer(int id) {
        return factureDao.supprimer(id);
    }

    public Facture rechercherParId(int id) {
        return factureDao.rechercherParId(id);
    }

    public Facture rechercherParNumero(String numero) {
        return factureDao.rechercherParNumero(numero);
    }

    public Facture rechercherParCommande(int idCommande) {
        return factureDao.rechercherParCommande(idCommande);
    }

    public List<Facture> listerTout() {
        return factureDao.listerTout();
    }

    public List<Facture> listerParClient(int idClient) {
        return factureDao.listerParClient(idClient);
    }

    public List<Facture> listerParStatut(StatutsEnums.StatutFacture statut) {
        return factureDao.listerParStatut(statut);
    }

    public double calculerChiffreAffaires(Date debut, Date fin) {
        return factureDao.calculerChiffreAffaires(debut, fin);
    }

    public int compter() {
        return factureDao.compter();
    }
}

package com.minierp.controller;

import com.minierp.dao.PaiementDao;
import com.minierp.model.Paiement;
import com.minierp.model.StatutsEnums.ModePaiement;

import java.util.List;

public class PaiementController {

    private static PaiementController instance;
    private final PaiementDao paiementDao;

    private PaiementController() {
        paiementDao = new PaiementDao();
    }

    public static synchronized PaiementController getInstance() {
        if (instance == null) instance = new PaiementController();
        return instance;
    }

    public boolean creer(Paiement p) {
        return paiementDao.creer(p);
    }

    public boolean modifier(Paiement p) {
        return paiementDao.modifier(p);
    }

    public boolean supprimer(int id) {
        return paiementDao.supprimer(id);
    }

    public Paiement rechercherParId(int id) {
        return paiementDao.rechercherParId(id);
    }

    public Paiement rechercherParReference(String reference) {
        return paiementDao.rechercherParReference(reference);
    }

    public List<Paiement> listerParFacture(int idFacture) {
        return paiementDao.listerParFacture(idFacture);
    }

    public List<Paiement> listerParMode(ModePaiement mode) {
        return paiementDao.listerParMode(mode);
    }

    public boolean encaisser(int id) {
        return paiementDao.encaisser(id);
    }

    public boolean rejeter(int id, String motif) {
        return paiementDao.rejeter(id, motif);
    }

    public int compter() { return paiementDao.compter(); }
}

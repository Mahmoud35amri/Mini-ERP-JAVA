package com.minierp.dao;

import com.minierp.model.LigneCommande;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LigneCommandeDao {

    private final List<LigneCommande> lignesCommande = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public boolean creer(LigneCommande l) {
        l.setId(idCounter.getAndIncrement());
        lignesCommande.add(l);
        return true;
    }

    public boolean modifier(LigneCommande l) {
        if (l == null) return false;
        for (int i = 0; i < lignesCommande.size(); i++) {
            if (lignesCommande.get(i).getId() == l.getId()) {
                lignesCommande.set(i, l);
                break;
            }
        }
        
        return false;
    }

    public boolean supprimer(int id) {
        LigneCommande l = rechercherParId(id);
        if (l != null) {
            lignesCommande.remove(l);
            return true;
        }
        return false;
    }

    public LigneCommande rechercherParId(int id) {
        for (LigneCommande ligneCommande : lignesCommande) {
            if (ligneCommande.getId() == id) {
                return ligneCommande;
            }
        }
        return null;
    }

    public List<LigneCommande> listerParCommande(int idCommande) {
        return lignesCommande.stream().filter(l -> l.getCommande().getId() == idCommande).collect(Collectors.toList());
    }

    public List<LigneCommande> listerParProduit(int idProduit) {
        return lignesCommande.stream().filter(l -> l.getProduit().getId() == idProduit).collect(Collectors.toList());
    }

    public double calculerMontantTotal(int idCommande) {
        return listerParCommande(idCommande).stream().mapToDouble(l -> l.calculerMontantTTC()).sum();
    }

    public boolean verifierDisponibilite(LigneCommande l) {
        
        return true;
    }

    public int compter() {
        return lignesCommande.size();
    }
}

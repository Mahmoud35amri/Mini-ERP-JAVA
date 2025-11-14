package com.minierp.dao;

import com.minierp.model.Commande;
import com.minierp.model.Facture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.minierp.model.StatutsEnums;

public class FactureDao {

    // === Attributs ===
    private final List<Facture> factures = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    // === CRUD ===
    public boolean creer(Facture f) {
        if (f == null) return false;
        f.setId(idCounter.incrementAndGet());
        factures.add(f);
        return true;
    }

    public Facture genererDepuisCommande(Commande c) {
        if (c == null) return null;
        Facture f = new Facture();
        f.setId(idCounter.incrementAndGet());
        f.setCommande(c);
        f.setDateCreation(new Date());
        factures.add(f);
        return f;
    }

    public boolean modifier(Facture f) {
        if (f == null) return false;
        for (int i = 0; i < factures.size(); i++) {
            if (factures.get(i).getId() == f.getId()) {
                factures.set(i, f);
                return true;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        return factures.removeIf(f -> f.getId() == id);
    }

    // === RECHERCHES ===
    public Facture rechercherParId(int id) {
        return factures.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Facture rechercherParNumero(String numero) {
        if (numero == null) return null;
        return factures.stream()
                .filter(f -> f.getNumeroFacture() != null && f.getNumeroFacture().equalsIgnoreCase(numero))
                .findFirst()
                .orElse(null);
    }

    public Facture rechercherParCommande(int idCommande) {
        return factures.stream()
                .filter(f -> f.getCommande() != null && f.getCommande().getId() == idCommande)
                .findFirst()
                .orElse(null);
    }

    public List<Facture> listerTout() {
        return new ArrayList<>(factures);
    }

    public List<Facture> listerParClient(int idClient) {
        return factures.stream()
                .filter(f -> f.getCommande() != null && f.getCommande().getClient() != null
                        && f.getCommande().getClient().getId() == idClient)
                .collect(Collectors.toList());
    }

    public List<Facture> listerParStatut(StatutsEnums.StatutFacture statut) {
        if (statut == null) return new ArrayList<>();
        return factures.stream()
                .filter(f -> f.getStatut() != null && f.getStatut() == statut)
                .collect(Collectors.toList());
    }

    public double calculerChiffreAffaires(Date debut, Date fin) {
        if (debut == null || fin == null) return 0.0;
        return factures.stream()
                .filter(f -> f.getDateCreation() != null
                        && !f.getDateCreation().before(debut)
                        && !f.getDateCreation().after(fin))
                .mapToDouble(Facture::getMontantTTC)
                .sum();
    }

    public int compter() {
        return factures.size();
    }
}

package com.minierp.dao;

import com.minierp.model.Entreprise;


import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EntrepriseDao {

    // === Attributs ===
    private final List<Entreprise> entreprises = new ArrayList<>();;
    private final AtomicInteger idCounter = new AtomicInteger(1);
    // === CRUD ===
    public boolean creer(Entreprise entreprise) {
        if (entreprise == null) return false;
        entreprise.setId(idCounter.getAndIncrement());
        entreprises.add(entreprise);
        return true;
    }

    public boolean modifier(Entreprise entreprise) {
        if (entreprise == null) return false;
        for (int i = 0; i < entreprises.size(); i++) {
            if (entreprises.get(i).getId() == entreprise.getId()) {
                entreprises.set(i, entreprise);
                return true;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        Entreprise e = rechercherParId(id);
        if (e != null) {
            entreprises.remove(e);
            return true;
        }
        return false;
    }

    // === RECHERCHES ===
    public Entreprise rechercherParId(int id) {
        return entreprises.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Entreprise> rechercherParNom(String nom) {
        return entreprises.stream()
                .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Entreprise> listerTout() {
        return new ArrayList<>(entreprises);
    }

    public List<Entreprise> listerActives() {
        return entreprises.stream()
                .filter(Entreprise::isActif)
                .collect(Collectors.toList());
    }

    public int compter() {
        return entreprises.size();
    }

}
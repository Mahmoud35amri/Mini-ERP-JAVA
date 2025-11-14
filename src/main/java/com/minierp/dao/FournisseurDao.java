package com.minierp.dao;

import com.minierp.model.Fournisseur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FournisseurDao {

    private final List<Fournisseur> fournisseurs = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public boolean creer(Fournisseur fournisseur) {
        fournisseur.setId(idCounter.incrementAndGet());
        fournisseurs.add(fournisseur);
        return true;
    }

    public boolean modifier(Fournisseur fournisseur) {
        if (fournisseur == null) return false;
        for (int i = 0; i < fournisseurs.size(); i++) {
            if (fournisseurs.get(i).getId() == fournisseur.getId()) {
                fournisseurs.set(i, fournisseur);
                break;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        Fournisseur f = rechercherParId(id);
        if (f != null) {
            fournisseurs.remove(f);
            return true;
        }
        return false;
    }

    public Fournisseur rechercherParId(int id) {
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getId() == id) {
                return fournisseur;
            }
        }
        return null;
    }

    public Fournisseur rechercherParCode(String code) {
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getCode().equalsIgnoreCase(code)) {
                return fournisseur;
            }
        }
        return null;
    }

    public List<Fournisseur> rechercherParNom(String nom) {
        return fournisseurs.stream()
                .filter(f -> f.getNomEntreprise() != null && f.getNomEntreprise().toLowerCase().contains(nom.toLowerCase()))
                .toList();
    }

    public List<Fournisseur> listerTout() {
        return new ArrayList<>(fournisseurs);
    }

    public List<Fournisseur> listerActifs() {
        return fournisseurs.stream().filter(Fournisseur::isActif).toList();
    }

    public List<Fournisseur> listerParEvaluation(int note) {
        return fournisseurs.stream()
                .filter(f -> f.getEvaluation() == note)
                .toList();
    }

    public double calculerTotalAchats(int id) {
        // Simulé pour l'instant : renvoyer 0.0
        return 0.0;
    }

    public boolean evaluer(int id, int note) {
        Fournisseur f = rechercherParId(id);
        if (f != null) {
            f.setEvaluation(note);
            return modifier(f);
        }
        return false;
    }

    public int compter() {
        return fournisseurs.size();
    }

}

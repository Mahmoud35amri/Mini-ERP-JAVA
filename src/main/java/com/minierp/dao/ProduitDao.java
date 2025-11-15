package com.minierp.dao;

import com.minierp.model.Produit;
import com.minierp.model.Categorie;
import com.minierp.model.Fournisseur;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProduitDao {

    private final List<Produit> produits = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public boolean creer(Produit p) {
        p.setId(idCounter.getAndIncrement());
        produits.add(p);
        return true;
    }

    public boolean modifier(Produit p) {
        if (p == null) return false;
        for (int i = 0; i < produits.size(); i++) {
            if (produits.get(i).getId() == p.getId()) {
                produits.set(i, p);
                break;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        Produit p = rechercherParId(id);
        if (p != null) {
            produits.remove(p);
            return true;
        }
        return false;
    }

    public Produit rechercherParId(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }

    public Produit rechercherParReference(String reference) {
        for (Produit produit : produits) {
            if (produit.getReference().equalsIgnoreCase(reference)) {
                return produit;
            }
        }
        return null;
    }

    public Produit rechercherParCodeBarres(String codeBarres) {
        return produits.stream()
                .filter(p -> p.getCodeBarres() != null && p.getCodeBarres().equalsIgnoreCase(codeBarres))
                .findFirst().orElse(null);
    }

    public List<Produit> rechercherParNom(String nom) {
        return produits.stream()
                .filter(p -> p.getNom() != null && p.getNom().toLowerCase().contains(nom.toLowerCase()))
                .toList();
    }

    public List<Produit> listerTout() {
        return Collections.unmodifiableList(produits);
    }

    public List<Produit> listerActifs() {
        return produits.stream().filter(Produit::isActif).toList();
    }

    public List<Produit> listerParCategorie(Categorie c) {
        return produits.stream().filter(p -> p.getCategorie() != null && p.getCategorie().equals(c)).toList();
    }

    public List<Produit> listerParFournisseur(Fournisseur f) {
        return produits.stream().filter(p -> p.getFournisseur() != null && p.getFournisseur().equals(f)).toList();
    }

    public List<Produit> listerEnRupture() {
        return produits.stream().filter(Produit::estEnRupture).toList();
    }

    public List<Produit> listerSousSeuilAlerte() {
        return produits.stream().filter(p -> p.getQuantiteStock() > 0 && p.getQuantiteStock() <= p.getSeuilAlerte()).toList();
    }

    public List<Produit> listerEnPromotion() {
        return produits.stream().filter(Produit::isEnPromotion).toList();
    }

    public List<Produit> listerNouveautes() {
        return produits.stream()
                .filter(p -> p.getDateLancement() != null && p.getDateLancement().after(new Date(System.currentTimeMillis() - 30L * 24*60*60*1000))) // 30 derniers jours
                .toList();
    }
    public boolean ajusterStock(int id, int quantite, String motif) {
        Produit p = rechercherParId(id);
        if (p != null) {
            p.setQuantiteStock(p.getQuantiteStock() + quantite);
            return modifier(p);
        }
        return false;
    }

    public boolean appliquerPromotion(int id, double taux, Date dateFin) {
        Produit p = rechercherParId(id);
        if (p != null) {
            p.appliquerPromotion(taux, dateFin);
            return modifier(p);
        }
        return false;
    }

    public boolean annulerPromotion(int id) {
        Produit p = rechercherParId(id);
        if (p != null) {
            p.annulerPromotion();
            return modifier(p);
        }
        return false;
    }

    public double calculerValeurStock() {
        return produits.stream().mapToDouble(p -> p.getPrixAchat() * p.getQuantiteStock()).sum();
    }

    public int compter() {
        return produits.size();
    }
}

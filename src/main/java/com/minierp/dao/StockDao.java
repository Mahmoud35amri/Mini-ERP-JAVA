package com.minierp.dao;

import com.minierp.model.MouvementStock;
import com.minierp.model.Produit;
import com.minierp.model.Stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StockDao {

    private final List<Stock> stocks = new ArrayList<>();
    private final List<MouvementStock> mouvements = new ArrayList<>();
    private final Map<Integer, Integer> reservations = new HashMap<>(); // produitId -> quantite réservée
    private final AtomicInteger mouvementIdCounter = new AtomicInteger(0);

    public boolean initialiser(int idProduit, int quantite) {
        Stock s = findByProduitId(idProduit);
        if (s == null) {
            s = new Stock();
            Produit p = new Produit();
            p.setId(idProduit);
            s.setProduit(p);
            s.setQuantiteInitiale(quantite);
            s.setQuantiteActuelle(quantite);
            stocks.add(s);
        } else {
            s.setQuantiteInitiale(quantite);
            s.setQuantiteActuelle(quantite);
        }
        return true;
    }

    public Stock getStock(int idProduit) {
        Stock s = findByProduitId(idProduit);
        return s;
    }

    public boolean ajouterStock(int idProduit, int quantite, String motif) {
        if (quantite <= 0) return false;
        Stock s = findByProduitId(idProduit);
        if (s == null) {
            initialiser(idProduit, 0);
            s = findByProduitId(idProduit);
        }
        s.setQuantiteActuelle(s.getQuantiteActuelle() + quantite);
        // enregistre mouvement
        MouvementStock m = new MouvementStock();
        m.setId(mouvementIdCounter.incrementAndGet());
        m.setProduit(s.getProduit());
        m.setQuantite(quantite);
        m.setMotif(motif);
        m.setDateCreation(new Date());
        mouvements.add(m);
        return true;
    }

    public boolean retirerStock(int idProduit, int quantite, String motif) {
        if (quantite <= 0) return false;
        Stock s = findByProduitId(idProduit);
        if (s == null) return false;
        int reserved = reservations.getOrDefault(idProduit, 0);
        int disponible = s.getQuantiteActuelle() - reserved;
        if (disponible < quantite) return false;
        s.setQuantiteActuelle(s.getQuantiteActuelle() - quantite);
        MouvementStock m = new MouvementStock();
        m.setId(mouvementIdCounter.incrementAndGet());
        m.setProduit(s.getProduit());
        m.setQuantite(-quantite);
        m.setMotif(motif);
        m.setDateCreation(new Date());
        mouvements.add(m);
        return true;
    }

    public boolean ajusterStock(int idProduit, int quantite, String motif) {
        Stock s = findByProduitId(idProduit);
        if (s == null) return false;
        int diff = quantite - s.getQuantiteActuelle();
        s.setQuantiteActuelle(quantite);
        MouvementStock m = new MouvementStock();
        m.setId(mouvementIdCounter.incrementAndGet());
        m.setProduit(s.getProduit());
        m.setQuantite(diff);
        m.setMotif(motif);
        m.setDateCreation(new Date());
        mouvements.add(m);
        return true;
    }

    public boolean reserverStock(int idProduit, int quantite) {
        if (quantite <= 0) return false;
        Stock s = findByProduitId(idProduit);
        if (s == null) return false;
        int reserved = reservations.getOrDefault(idProduit, 0);
        int disponible = s.getQuantiteActuelle() - reserved;
        if (disponible < quantite) return false;
        reservations.put(idProduit, reserved + quantite);
        return true;
    }

    public boolean libererReservation(int idProduit, int quantite) {
        if (quantite <= 0) return false;
        int reserved = reservations.getOrDefault(idProduit, 0);
        if (reserved == 0) return false;
        if (quantite >= reserved) {
            reservations.remove(idProduit);
        } else {
            reservations.put(idProduit, reserved - quantite);
        }
        return true;
    }

    public boolean transferer(int idProduitSource, int idProduitDest, int quantite) {
        if (quantite <= 0) return false;
        if (!retirerStock(idProduitSource, quantite, "Transfert vers " + idProduitDest)) return false;
        ajouterStock(idProduitDest, quantite, "Transfert depuis " + idProduitSource);
        return true;
    }

    public boolean inventorier(int idProduit, int quantiteReelle) {
        return ajusterStock(idProduit, quantiteReelle, "Inventaire");
    }

    public boolean inventorierTout() {
        for (Stock s : new ArrayList<>(stocks)) {
            ajusterStock(s.getProduit().getId(), s.getQuantiteActuelle(), "Inventaire global");
        }
        return true;
    }

    public List<MouvementStock> getMouvements(int idProduit) {
        return mouvements.stream()
                .filter(m -> m.getProduit() != null && m.getProduit().getId() == idProduit)
                .collect(Collectors.toList());
    }

    public List<MouvementStock> getMouvementsParPeriode(Date debut, Date fin) {
        if (debut == null || fin == null) return new ArrayList<>();
        return mouvements.stream()
                .filter(m -> m.getDateCreation() != null
                        && !m.getDateCreation().before(debut)
                        && !m.getDateCreation().after(fin))
                .collect(Collectors.toList());
    }

    public double calculerValeurStockTotal() {
        return stocks.stream()
                .mapToDouble(Stock::calculerValeurStock)
                .sum();
    }

    public List<Produit> listerProduitsEnRupture() {
        return stocks.stream()
                .filter(s -> (s.getQuantiteActuelle() - reservations.getOrDefault(s.getProduit().getId(), 0)) <= 0)
                .map(Stock::getProduit)
                .collect(Collectors.toList());
    }

    public List<Produit> listerProduitsSousSeuilAlerte() {
        return stocks.stream()
                .filter(s -> {
                    Produit p = s.getProduit();
                    return p != null && s.getQuantiteActuelle() <= p.getSeuilAlerte();
                })
                .map(Stock::getProduit)
                .collect(Collectors.toList());
    }
    public List<Produit> listerProduitsSousSeuilMinimum() {
        return stocks.stream()
                .filter(s -> {
                    Produit p = s.getProduit();
                    return p != null && s.getQuantiteActuelle() <= p.getSeuilMinimum();
                })
                .map(Stock::getProduit)
                .collect(Collectors.toList());
    }

    private Stock findByProduitId(int idProduit) {
        return stocks.stream()
                .filter(s -> s.getProduit() != null && s.getProduit().getId() == idProduit)
                .findFirst()
                .orElse(null);
    }
}

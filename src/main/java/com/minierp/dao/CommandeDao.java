package com.minierp.dao;

import com.minierp.model.Commande;
import com.minierp.model.StatutsEnums.StatutCommande;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CommandeDao {

    private static final List<Commande> commandes = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public boolean creer(Commande c) {
        c.setId(idCounter.getAndIncrement());
        commandes.add(c);
        return true;
    }

    public boolean modifier(Commande c) {
        if (c == null) return false;
        for (int i = 0; i < commandes.size(); i++) {
            if (commandes.get(i).getId() == c.getId()) {
                commandes.set(i, c);
                return true;
            }
        }
        return false;
    }
    public boolean supprimer(int id) {
        Commande c = rechercherParId(id);
        if (c != null) {
            commandes.remove(c);
            return true;
        }
        return false;
    }

    public Commande rechercherParId(int id) {
        for (Commande commande : commandes) {
            if (commande.getId() == id) {
                return commande;
            }
        }
        return null;
    }

    public Commande rechercherParNum(String numero) {
        if (numero == null) return null;
        for (Commande c : commandes) {
            if (numero.equalsIgnoreCase(c.getNumeroCommande())) {
                return c;
            }
        }
        return null;
    }
    public List<Commande> listerTout() {
        return new ArrayList<>(commandes);
    }

    public List<Commande> listerParStatut(StatutCommande statut) {
        return commandes.stream()
                .filter(c -> c.getStatut() == statut)
                .collect(Collectors.toList());
    }

    public List<Commande> listerParClient(int idClient) {
        return commandes.stream()
                .filter(c -> c.getClient() != null && c.getClient().getId() == idClient)
                .collect(Collectors.toList());
    }

    public List<Commande> listerParFournisseur(int idFournisseur) {
        return commandes.stream()
                .filter(c -> c.getFournisseur() != null && c.getFournisseur().getId() == idFournisseur)
                .collect(Collectors.toList());
    }
}

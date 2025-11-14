package com.minierp.dao;

import com.minierp.model.Paiement;
import com.minierp.model.StatutsEnums.ModePaiement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PaiementDao {

    // === Attributs ===
    private final List<Paiement> paiements = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    // === CRUD ===
    public boolean creer(Paiement p) {
        if (p == null) return false;
        p.setId(idCounter.incrementAndGet());
        paiements.add(p);
        return true;
    }

    public boolean modifier(Paiement p) {
        if (p == null) return false;
        for (int i = 0; i < paiements.size(); i++) {
            if (paiements.get(i).getId() == p.getId()) {
                paiements.set(i, p);
                return true;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        return paiements.removeIf(p -> p.getId() == id);
    }

    // === RECHERCHES ===
    public Paiement rechercherParId(int id) {
        return paiements.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Paiement rechercherParReference(String reference) {
        if (reference == null) return null;
        return paiements.stream()
                .filter(p -> p.getReference() != null && p.getReference().equalsIgnoreCase(reference))
                .findFirst()
                .orElse(null);
    }

    public List<Paiement> listerParFacture(int idFacture) {
        return paiements.stream()
                .filter(p -> p.getFacture() != null && p.getFacture().getId() == idFacture)
                .collect(Collectors.toList());
    }

    public List<Paiement> listerParMode(ModePaiement mode) {
        if (mode == null) return new ArrayList<>();
        return paiements.stream()
                .filter(p -> p.getModePaiement() == mode)
                .collect(Collectors.toList());
    }

    public boolean encaisser(int id) {
        Paiement p = rechercherParId(id);
        if (p != null && p.getStatut() != null && p.getStatut().equalsIgnoreCase("EN_ATTENTE")) {
            p.setStatut("ENCASSE");
            return modifier(p);
        }
        return false;
    }

    public boolean rejeter(int id, String motif) {
        Paiement p = rechercherParId(id);
        if (p != null && p.getStatut() != null && p.getStatut().equalsIgnoreCase("EN_ATTENTE")) {
            p.rejeter(motif);
            return modifier(p);
        }
        return false;
    }

    public int compter() {
        return paiements.size();
    }
}

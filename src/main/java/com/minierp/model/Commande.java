/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private final String id;
    private LocalDate date;
    private String type; // CLIENT ou FOURNISSEUR
    private String clientId;
    private String fournisseurId;
    private double montantTotal;
    private String statut;
    private String entrepriseId;

    // new: list of order lines
    private final List<LigneCommande> lignes = new ArrayList<>();

    public Commande(LocalDate date, String type, String clientId, String fournisseurId, 
                    double montantTotal, String statut, String entrepriseId) {
        this.id = IdGenerator.generateOrderId();
        this.date = date;
        this.type = type;
        this.clientId = clientId;
        this.fournisseurId = fournisseurId;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.entrepriseId = entrepriseId;
    }
    // getteurs
    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getType() { return type; }
    public String getClientId() { return clientId; }
    public String getFournisseurId() { return fournisseurId; }
    public double getMontantTotal() { return montantTotal; }
    public String getStatut() { return statut; }
    public String getEntrepriseId() { return entrepriseId; }
    public List<LigneCommande> getLignes() { return new ArrayList<>(lignes); }
    // setteurs
    public void setDate(LocalDate date) { this.date = date; }
    public void setType(String type) { this.type = type; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setFournisseurId(String fournisseurId) { this.fournisseurId = fournisseurId; }
    // keep but prefer montantTotal computed from lines
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }
    public void setStatut(String statut) { this.statut = statut; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }

    // manage lines
    public void addLigne(String commandeId,String produitId, int quantite, double prixUnitaire) {
        lignes.add(new LigneCommande(commandeId,produitId, quantite, prixUnitaire));
        recalcMontantTotal();
    }

    public void removeLigne(String produitId) {
        lignes.removeIf(l -> l.getProduitId().equals(produitId));
        recalcMontantTotal();
    }

    private void recalcMontantTotal() {
        this.montantTotal = lignes.stream().mapToDouble(LigneCommande::getSousTotal).sum();
    }

    @Override
    public String toString() {
        return "Commande: " + id + " | Type: " + type + " | Montant: " + montantTotal +
               " | Statut: " + statut + " | Date: " + date + " | Entreprise ID: " + entrepriseId;
    }
}


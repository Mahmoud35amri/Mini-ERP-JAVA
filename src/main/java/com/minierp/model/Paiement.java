package com.minierp.model;

import java.util.Date;
import java.util.Objects;
import com.minierp.model.StatutsEnums.ModePaiement;

public class Paiement {

    private int id;
    private Facture facture;
    private String numeroPaiement;
    private Date datePaiement;
    private double montant;
    private ModePaiement modePaiement;
    private String reference;
    private String banque;
    private String numeroCheque;
    private Date dateEncaissement;
    private String observations;
    private String statut; // EN_ATTENTE, ENCAISSE, REJETE
    private Date dateCreation;

    public Paiement() {
        this.dateCreation = new Date();
        this.statut = "EN_ATTENTE";
    }

    public Paiement(Facture facture, double montant, ModePaiement mode) {
        this();
        this.facture = facture;
        this.montant = montant;
        this.modePaiement = mode;
        this.datePaiement = new Date();
        this.numeroPaiement = genererNumeroPaiement();
    }

    private String genererNumeroPaiement() {
        return "PAY-" + System.currentTimeMillis();
    }

    public boolean valider() {
        if (facture == null || montant <= 0) return false;
        this.statut = "ENCAISSE";
        this.dateEncaissement = new Date();
        if (facture != null) facture.ajouterPaiement(this);
        return true;
    }

    public void encaisser() {
        valider();
    }

    public void rejeter(String motif) {
        this.statut = "REJETE";
        this.observations = motif;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Facture getFacture() { return facture; }
    public void setFacture(Facture facture) { this.facture = facture; }
    public String getNumeroPaiement() { return numeroPaiement; }
    public Date getDatePaiement() { return datePaiement; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getBanque() { return banque; }
    public void setBanque(String banque) { this.banque = banque; }
    public String getNumeroCheque() { return numeroCheque; }
    public void setNumeroCheque(String numeroCheque) { this.numeroCheque = numeroCheque; }
    public Date getDateEncaissement() { return dateEncaissement; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Paiement{" +
                "numero='" + numeroPaiement + '\'' +
                ", montant=" + montant +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paiement)) return false;
        Paiement p = (Paiement) o;
        return id == p.id || Objects.equals(numeroPaiement, p.numeroPaiement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroPaiement);
    }
}

package com.minierp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.minierp.model.StatutsEnums.StatutFacture;
import com.minierp.model.StatutsEnums.ModePaiement;
import com.minierp.model.StatutsEnums.TypeFacture;

public class Facture {

    private int id;
    private String numeroFacture; // FAC-YYYYMMDD-XXXX
    private TypeFacture typeFacture;
    private Commande commande;
    private Client client;
    private Date dateEmission;
    private Date dateEcheance;
    private Date dateLimite;
    private double montantHT;
    private double montantTVA;
    private double montantTTC;
    private double montantPaye;
    private double montantRestant;
    private StatutFacture statut;
    private ModePaiement modePaiement;
    private Date dateReglement;
    private String referenceReglement;
    private String conditions;
    private String mentions;
    private String observations;
    private List<Paiement> paiements;
    private int relances;
    private Date dateRelance;
    private boolean estAvoir;
    private Facture factureOriginale;
    private Date dateCreation;
    private Date dateModification;

    public Facture() {
        this.dateCreation = new Date();
        this.dateEmission = new Date();
        this.paiements = new ArrayList<>();
        this.statut = StatutFacture.BROUILLON;
        this.numeroFacture = genererNumeroFacture();
    }

    public Facture(Commande commande, Client client) {
        this();
        this.commande = commande;
        this.client = client;
        calculerMontants();
    }

    private String genererNumeroFacture() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int rand = (int)(Math.random() * 9000) + 1000;
        return String.format("FAC-%s-%04d", datePart, rand);
    }

    public void calculerMontants() {
        if (commande != null) {
            this.montantHT = commande.getMontantHT();
            this.montantTVA = commande.calculerMontantTVA();
            this.montantTTC = commande.calculerMontantTTC();
        }
        calculerMontantRestant();
        this.dateModification = new Date();
    }

    public void ajouterPaiement(Paiement paiement) {
        if (paiement == null) return;
        this.paiements.add(paiement);
        this.montantPaye += paiement.getMontant();
        calculerMontantRestant();
        if (montantRestant <= 0) {
            this.statut = StatutFacture.PAYEE;
            this.dateReglement = paiement.getDatePaiement();
        } else {
            this.statut = StatutFacture.PAYEE_PARTIELLEMENT;
        }
        this.dateModification = new Date();
    }

    public void marquerPayee(Date date, ModePaiement mode, String reference) {
        this.modePaiement = mode;
        this.referenceReglement = reference;
        this.dateReglement = date;
        this.montantPaye = this.montantTTC;
        this.montantRestant = 0.0;
        this.statut = StatutFacture.PAYEE;
        this.dateModification = new Date();
    }

    public void marquerPayeePartiellement(double montant) {
        this.montantPaye += montant;
        calculerMontantRestant();
        if (montantRestant <= 0) this.statut = StatutFacture.PAYEE;
        else this.statut = StatutFacture.PAYEE_PARTIELLEMENT;
        this.dateModification = new Date();
    }

    public double calculerMontantRestant() {
        this.montantRestant = montantTTC - montantPaye;
        return this.montantRestant;
    }

    public boolean estPayee() {
        return this.statut == StatutFacture.PAYEE || this.montantRestant <= 0.0;
    }

    public boolean estPayeePartiellement() {
        return this.statut == StatutFacture.PAYEE_PARTIELLEMENT;
    }

    public boolean estEnRetard() {
        if (dateEcheance == null) return false;
        return new Date().after(dateEcheance) && !estPayee();
    }

    public int getNombreJoursRetard() {
        if (!estEnRetard()) return 0;
        long diff = new Date().getTime() - dateEcheance.getTime();
        return (int)(diff / (1000L * 60 * 60 * 24));
    }

    public Facture genererAvoir() {
        Facture avoir = new Facture();
        avoir.typeFacture = TypeFacture.AVOIR;
        avoir.client = this.client;
        avoir.montantTTC = -this.montantTTC;
        avoir.montantHT = -this.montantHT;
        avoir.montantTVA = -this.montantTVA;
        avoir.factureOriginale = this;
        return avoir;
    }

    public void relancer() {
        this.relances++;
        this.dateRelance = new Date();
    }

    public double appliquerPenaliteRetard() {
        int jours = getNombreJoursRetard();
        double penalite = 0.0;
        if (jours > 0) {
            penalite = Math.min(this.montantTTC * 0.1, jours * 1.0); // exemple
            this.montantTTC += penalite;
            calculerMontantRestant();
        }
        return penalite;
    }
    public boolean valider() {
        if (client == null) {
            System.out.println("Erreur : le client n'est pas défini.");
            return false;
        }
        if (montantTTC < 0) {
            System.out.println("Erreur : le montant est négatif.");
            return false;
        }
        if (statut == null) {
            System.out.println("Erreur : le statut n'est pas défini.");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "numero='" + numeroFacture + '\'' +
                ", client=" + (client != null ? client.getCode() : "null") +
                ", montantTTC=" + montantTTC +
                ", statut=" + statut +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facture)) return false;
        Facture f = (Facture) o;
        return id == f.id || Objects.equals(numeroFacture, f.numeroFacture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroFacture);
    }

    // getters & setters omitted for brevity — add as needed
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Commande getCommande() { return commande; }
    public String getNumeroFacture() { return numeroFacture; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public Date getDateCreation() { return dateCreation; }
    public StatutFacture getStatut() { return statut; }
    public void setStatut(StatutFacture statut) { this.statut = statut; }
    public double getMontantHT() { return montantHT; }
    public double getMontantTTC() { return montantTTC; }
    public Client getClient() { return client; }
    public void setMontantHT(double montantHT) { this.montantHT = montantHT; }
    public void setMontantTTC(double montantTTC) { this.montantTTC = montantTTC; }
    public Date getDateLimite() { return dateLimite;}
    public void setDateLimite(Date dateLimite) { this.dateLimite = dateLimite; }
}

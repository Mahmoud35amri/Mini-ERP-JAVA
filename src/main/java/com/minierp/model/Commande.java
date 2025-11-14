package com.minierp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.minierp.model.StatutsEnums.StatutCommande;
import com.minierp.model.StatutsEnums.TypeCommande;

public class Commande {

    private int id;
    private String numeroCommande; // CMD-YYYYMMDD-XXXX
    private TypeCommande typeCommande;
    private Client client;
    private Fournisseur fournisseur;
    private Utilisateur utilisateur;
    private Date dateCommande;
    private Date dateValidation;
    private Date dateLivraisonPrevue;
    private Date dateLivraisonEffective;
    private Date dateAnnulation;
    private StatutCommande statut;
    private String modeLivraison;
    private String adresseLivraison;
    private String villeLivraison;
    private String codePostalLivraison;
    private String paysLivraison;
    private List<LigneCommande> lignesCommande;
    private double montantHT;
    private double montantRemise;
    private double montantTVA;
    private double montantTTC;
    private double fraisPort;
    private double tauxRemise;
    private String observations;
    private String conditionsPaiement;
    private int validitePrix;
    private boolean estUrgente;
    private Date dateCreation;
    private Date dateModification;

    public Commande() {
        this.lignesCommande = new ArrayList<>();
        this.dateCommande = new Date();
        this.dateCreation = new Date();
        this.statut = StatutCommande.BROUILLON;
        this.numeroCommande = genererNumeroCommande();
    }

    public Commande(TypeCommande typeCommande, Client client, Utilisateur utilisateur) {
        this();
        this.typeCommande = typeCommande;
        this.client = client;
        this.utilisateur = utilisateur;
    }

    // Getters / Setters (add as needed)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroCommande() { return numeroCommande; }
    public TypeCommande getTypeCommande() { return typeCommande; }
    public void setTypeCommande(TypeCommande typeCommande) { this.typeCommande = typeCommande; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Date getDateCommande() { return dateCommande; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }
    public StatutCommande getStatut() { return statut; }
    public void setStatut(StatutCommande statut) { this.statut = statut; }
    public List<LigneCommande> getLignes() { return lignesCommande; }
    public int getNombreLignes() { return lignesCommande.size(); }
    public double getMontantHT() { return montantHT; }
    public double getMontantTTC() { return montantTTC; }
    public void setMontantTTC(double montantTTC) { this.montantTTC = montantTTC; }
    public void setMontantHT(double montantHT) { this.montantHT = montantHT; }  

    // Méthodes
    private String genererNumeroCommande() {
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int rand = (int)(Math.random() * 9000) + 1000;
        return String.format("CMD-%s-%04d", datePart, rand);
    }

    public void ajouterLigne(LigneCommande ligne) {
        if (ligne == null) return;
        ligne.setCommande(this);
        ligne.recalculer();
        this.lignesCommande.add(ligne);
        recalculerMontants();
    }

    public void supprimerLigne(int idLigne) {
        lignesCommande.removeIf(l -> l.getId() == idLigne);
        recalculerMontants();
    }

    public void modifierLigne(LigneCommande ligne) {
        for (int i = 0; i < lignesCommande.size(); i++) {
            if (lignesCommande.get(i).getId() == ligne.getId()) {
                ligne.recalculer();
                ligne.setCommande(this);
                lignesCommande.set(i, ligne);
                break;
            }
        }
        recalculerMontants();
    }

    public double calculerMontantHT() {
        double sum = 0.0;
        for (LigneCommande l : lignesCommande) sum += l.calculerMontantHT();
        this.montantHT = sum;
        return this.montantHT;
    }

    public double calculerMontantTVA() {
        double sum = 0.0;
        for (LigneCommande l : lignesCommande) {
            l.calculerMontantTVA();
            sum += l.getMontantTVA();
        }
        this.montantTVA = sum;
        return this.montantTVA;
    }

    public double calculerMontantTTC() {
        calculerMontantHT();
        calculerMontantTVA();
        this.montantTTC = montantHT + montantTVA + fraisPort - montantRemise;
        return this.montantTTC;
    }

    public void appliquerRemise(double taux) {
        this.tauxRemise = taux;
        this.montantRemise = montantHT * (taux / 100.0);
        recalculerMontants();
    }

    public boolean valider() {
        if (this.client == null && this.fournisseur == null) return false;
        if (this.lignesCommande.isEmpty()) return false;
        this.statut = StatutCommande.CONFIRMEE;
        this.dateValidation = new Date();
        recalculerMontants();
        return true;
    }

    public void annuler(String motif) {
        this.statut = StatutCommande.ANNULEE;
        this.dateAnnulation = new Date();
        this.observations = motif;
    }

    public void livrer() {
        this.statut = StatutCommande.LIVREE;
        this.dateLivraisonEffective = new Date();
    }

    public void changerStatut(StatutCommande nouveauStatut) {
        this.statut = nouveauStatut;
        this.dateModification = new Date();
    }

    public boolean verifierStock() {
        for (LigneCommande l : lignesCommande) {
            Produit p = l.getProduit();
            if (p != null && p.getQuantiteStock() < l.getQuantite()) return false;
        }
        return true;
    }

    private void recalculerMontants() {
        calculerMontantHT();
        // recalculer remises sur lignes
        double remiseLignes = 0.0;
        for (LigneCommande l : lignesCommande) {
            remiseLignes += l.getMontantRemise();
        }
        this.montantRemise = remiseLignes + (this.montantHT * (tauxRemise / 100.0));
        calculerMontantTVA();
        calculerMontantTTC();
        this.dateModification = new Date();
    }

    @Override
    public String toString() {
        return "Commande{" +
                "numero='" + numeroCommande + '\'' +
                ", type=" + typeCommande +
                ", statut=" + statut +
                ", lignes=" + lignesCommande.size() +
                ", montantTTC=" + montantTTC +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande)) return false;
        Commande c = (Commande) o;
        return id == c.id || Objects.equals(numeroCommande, c.numeroCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroCommande);
    }
}

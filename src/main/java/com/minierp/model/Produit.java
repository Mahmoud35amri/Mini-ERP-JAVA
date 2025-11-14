package com.minierp.model;

import java.util.Date;
import java.util.Objects;

public class Produit {

    public enum Unite {
        PIECE, KG, GRAMME, LITRE, MILLILITRE, METRE, CENTIMETRE, PACK, CARTON
    }

    // Attributs
    private int id;
    private String reference;
    private String codeBarres;
    private String nom;
    private String description;
    private String descriptionLongue;
    private Categorie categorie;
    private Categorie sousCategorie;
    private String marque;
    private String modele;
    private Fournisseur fournisseur;
    private double prixAchat;
    private double prixVente;
    private double tauxPromo;
    private double tauxTVA;
    private int quantiteStock;
    private int seuilAlerte;
    private int seuilMinimum;
    private Unite unite;
    private double poids;
    private String dimensions;
    private boolean actif;
    private boolean enPromotion;
    private boolean nouveau;
    private boolean bestSeller;
    private Date dateLancement;
    private Date dateFinPromo;
    private Date dateModification;
    private int garantie; // mois
    private String observations;

    // Constructeurs
    public Produit() {
        this.actif = true;
        this.unite = Unite.PIECE;
    }

    public Produit( String reference, String nom, double prixVente) {
        this();
        
        this.reference = reference;
        this.nom = nom;
        this.prixVente = prixVente;
    }

    public Produit( String reference, String nom, Categorie categorie, Fournisseur fournisseur,
                   double prixAchat, double prixVente, int quantiteStock) {
        this( reference, nom, prixVente);
        this.categorie = categorie;
        this.fournisseur = fournisseur;
        this.prixAchat = prixAchat;
        this.quantiteStock = quantiteStock;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getCodeBarres() { return codeBarres; }
    public void setCodeBarres(String codeBarres) { this.codeBarres = codeBarres; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDescriptionLongue() { return descriptionLongue; }
    public void setDescriptionLongue(String descriptionLongue) { this.descriptionLongue = descriptionLongue; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public Categorie getSousCategorie() { return sousCategorie; }
    public void setSousCategorie(Categorie sousCategorie) { this.sousCategorie = sousCategorie; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public double getPrixAchat() { return prixAchat; }
    public void setPrixAchat(double prixAchat) { this.prixAchat = prixAchat; }

    public double getPrixVente() { return prixVente; }
    public void setPrixVente(double prixVente) { this.prixVente = prixVente; }

    public double getTauxPromo() { return tauxPromo; }
    public void setTauxPromo(double tauxPromo) { this.tauxPromo = tauxPromo; }

    public double getTauxTVA() { return tauxTVA; }
    public void setTauxTVA(double tauxTVA) { this.tauxTVA = tauxTVA; }

    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }

    public int getSeuilAlerte() { return seuilAlerte; }
    public void setSeuilAlerte(int seuilAlerte) { this.seuilAlerte = seuilAlerte; }

    public int getSeuilMinimum() { return seuilMinimum; }
    public void setSeuilMinimum(int seuilMinimum) { this.seuilMinimum = seuilMinimum; }

    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }

    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public boolean isEnPromotion() { return enPromotion; }
    public void setEnPromotion(boolean enPromotion) { this.enPromotion = enPromotion; }

    public boolean isNouveau() { return nouveau; }
    public void setNouveau(boolean nouveau) { this.nouveau = nouveau; }

    public boolean isBestSeller() { return bestSeller; }
    public void setBestSeller(boolean bestSeller) { this.bestSeller = bestSeller; }

    public Date getDateLancement() { return dateLancement; }
    public void setDateLancement(Date dateLancement) { this.dateLancement = dateLancement; }

    public Date getDateFinPromo() { return dateFinPromo; }
    public void setDateFinPromo(Date dateFinPromo) { this.dateFinPromo = dateFinPromo; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    public int getGarantie() { return garantie; }
    public void setGarantie(int garantie) { this.garantie = garantie; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    // Méthodes métier
    public double calculerMarge() {
        return this.prixVente - this.prixAchat;
    }

    public double calculerPourcentageMarge() {
        if (this.prixVente == 0) return 0;
        return (calculerMarge() / this.prixVente) * 100.0;
    }

    public double calculerPrixTTC() {
        return this.prixVente * (1 + this.tauxTVA / 100.0);
    }

    public double getPrixVenteActuel() {
        if (this.enPromotion && this.dateFinPromo != null && new Date().before(this.dateFinPromo)) {
            return this.prixVente * (1 - (this.tauxPromo / 100.0));
        }
        return this.prixVente;
    }

    public boolean estEnRupture() {
        return this.quantiteStock <= 0;
    }

    public boolean estSousSeuilAlerte() {
        return this.quantiteStock <= this.seuilAlerte;
    }

    public boolean estSousSeuilMinimum() {
        return this.quantiteStock <= this.seuilMinimum;
    }

    public void ajusterStock(int quantite) {
        this.quantiteStock += quantite;
        if (this.quantiteStock < 0) this.quantiteStock = 0;
        this.dateModification = new Date();
    }

    public void appliquerPromotion(double pourcentage, Date dateFin) {
        this.enPromotion = true;
        this.tauxPromo = pourcentage;
        this.dateFinPromo = dateFin;
        this.dateModification = new Date();
    }

    public void annulerPromotion() {
        this.enPromotion = false;
        this.tauxPromo = 0;
        this.dateFinPromo = null;
        this.dateModification = new Date();
    }

    public boolean valider() {
        return this.reference != null && !this.reference.isEmpty() && this.nom != null && !this.nom.isEmpty();
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", ref='" + reference + '\'' +
                ", nom='" + nom + '\'' +
                ", prixVente=" + prixVente +
                ", stock=" + quantiteStock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit)) return false;
        Produit produit = (Produit) o;
        return id == produit.id || Objects.equals(reference, produit.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference);
    }
}

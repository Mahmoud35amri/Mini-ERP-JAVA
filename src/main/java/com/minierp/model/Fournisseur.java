package com.minierp.model;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Fournisseur {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(5000);

    // Attributs
    private int id;
    private String code; // FRS-XXXX
    private String nomEntreprise;
    private String nomContact;
    private String prenomContact;
    private String fonctionContact;
    private String adresse;
    private String adresseComplement;
    private String ville;
    private String codePostal;
    private String pays;
    private String telephone;
    private String fax;
    private String email;
    private String siteWeb;
    private String numeroFiscal;
    private String numeroRegistreCommerce;
    private String conditionsPaiement;
    private int delaiPaiement;
    private int delaiLivraison;
    private double fraisPort;
    private double minimumCommande;
    private String devise;
    private String iban;
    private String bic;
    private String observations;
    private Date dateDebut;
    private Date dateModification;
    private boolean actif;
    private int evaluation; // 1-5
    private double totalAchats;
    private int nombreCommandes;

    // Constructeurs
    public Fournisseur() {
        this.code = genererCode();
        this.actif = true;
        this.dateDebut = new Date();
    }

    public Fournisseur(int id, String nomEntreprise, String email) {
        this();
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.email = email;
    }

    public Fournisseur(int id, String nomEntreprise, String nomContact, String prenomContact,
                       String telephone, String email, double minimumCommande) {
        this();
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.nomContact = nomContact;
        this.prenomContact = prenomContact;
        this.telephone = telephone;
        this.email = email;
        this.minimumCommande = minimumCommande;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNomEntreprise() { return nomEntreprise; }
    public void setNomEntreprise(String nomEntreprise) { this.nomEntreprise = nomEntreprise; }

    public String getNomContact() { return nomContact; }
    public void setNomContact(String nomContact) { this.nomContact = nomContact; }

    public String getPrenomContact() { return prenomContact; }
    public void setPrenomContact(String prenomContact) { this.prenomContact = prenomContact; }

    public String getFonctionContact() { return fonctionContact; }
    public void setFonctionContact(String fonctionContact) { this.fonctionContact = fonctionContact; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getAdresseComplement() { return adresseComplement; }
    public void setAdresseComplement(String adresseComplement) { this.adresseComplement = adresseComplement; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }

    public String getNumeroFiscal() { return numeroFiscal; }
    public void setNumeroFiscal(String numeroFiscal) { this.numeroFiscal = numeroFiscal; }

    public String getNumeroRegistreCommerce() { return numeroRegistreCommerce; }
    public void setNumeroRegistreCommerce(String numeroRegistreCommerce) { this.numeroRegistreCommerce = numeroRegistreCommerce; }

    public String getConditionsPaiement() { return conditionsPaiement; }
    public void setConditionsPaiement(String conditionsPaiement) { this.conditionsPaiement = conditionsPaiement; }

    public int getDelaiPaiement() { return delaiPaiement; }
    public void setDelaiPaiement(int delaiPaiement) { this.delaiPaiement = delaiPaiement; }

    public int getDelaiLivraison() { return delaiLivraison; }
    public void setDelaiLivraison(int delaiLivraison) { this.delaiLivraison = delaiLivraison; }

    public double getFraisPort() { return fraisPort; }
    public void setFraisPort(double fraisPort) { this.fraisPort = fraisPort; }

    public double getMinimumCommande() { return minimumCommande; }
    public void setMinimumCommande(double minimumCommande) { this.minimumCommande = minimumCommande; }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public String getBic() { return bic; }
    public void setBic(String bic) { this.bic = bic; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public int getEvaluation() { return evaluation; }
    public void setEvaluation(int evaluation) { this.evaluation = Math.max(1, Math.min(5, evaluation)); }

    public double getTotalAchats() { return totalAchats; }
    public void setTotalAchats(double totalAchats) { this.totalAchats = totalAchats; }

    public int getNombreCommandes() { return nombreCommandes; }
    public void setNombreCommandes(int nombreCommandes) { this.nombreCommandes = nombreCommandes; }

    // Méthodes métier
    private String genererCode() {
        int seq = SEQUENCE.getAndIncrement();
        return String.format("FRS-%04d", seq);
    }

    public double calculerTotalAchats() {
        // Retourne la valeur stockée ; en vrai on sommerait les commandes d'achat
        return this.totalAchats;
    }

    public int getDelaiLivraisonMoyen() {
        return this.delaiLivraison;
    }

    public void evaluer(int note) {
        setEvaluation(note);
    }

    public boolean estFiable() {
        return this.evaluation >= 3 && this.actif;
    }

    public boolean valider() {
        return this.nomEntreprise != null && !this.nomEntreprise.isEmpty()
                && this.email != null && this.email.contains("@");
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", entreprise='" + nomEntreprise + '\'' +
                ", contact='" + prenomContact + " " + nomContact + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fournisseur)) return false;
        Fournisseur that = (Fournisseur) o;
        return id == that.id || Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}

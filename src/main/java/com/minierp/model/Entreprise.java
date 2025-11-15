package com.minierp.model;

import java.util.Date;
import java.util.Objects;

public class Entreprise {

    private int id;
    private String nom;
    private String raisonSociale;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String telephone;
    private String fax;
    private String email;
    private String siteWeb;
    private String numeroRegistreCommerce;
    private String numeroIdentificationFiscale;
    private String formeJuridique; // SARL, SA, SAS, etc.
    private double capital;
    private Date dateCreation;
    private boolean actif;
    private Date dateModification;

    public Entreprise() {
        this.actif = true;
        this.dateCreation = new Date();
        this.dateModification = new Date();
    }

    public Entreprise( String nom, String raisonSociale, String email, String pays) {
        this();
        this.nom = nom;
        this.raisonSociale = raisonSociale;
        this.email = email;
        this.pays = pays;
    }

    public Entreprise(int id, String nom, String raisonSociale, String adresse, String ville,
                      String codePostal, String pays, String telephone, String fax,
                      String email, String siteWeb, String numeroRegistreCommerce,
                      String numeroIdentificationFiscale, String formeJuridique, double capital,
                      Date dateCreation, boolean actif, Date dateModification) {
        this.id = id;
        this.nom = nom;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.telephone = telephone;
        this.fax = fax;
        this.email = email;
        this.siteWeb = siteWeb;
        this.numeroRegistreCommerce = numeroRegistreCommerce;
        this.numeroIdentificationFiscale = numeroIdentificationFiscale;
        this.formeJuridique = formeJuridique;
        this.capital = capital;
        this.dateCreation = dateCreation;
        this.actif = actif;
        this.dateModification = dateModification;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getRaisonSociale() { return raisonSociale; }
    public void setRaisonSociale(String raisonSociale) { this.raisonSociale = raisonSociale; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

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

    public String getNumeroRegistreCommerce() { return numeroRegistreCommerce; }
    public void setNumeroRegistreCommerce(String numeroRegistreCommerce) {
        this.numeroRegistreCommerce = numeroRegistreCommerce;
    }

    public String getNumeroIdentificationFiscale() { return numeroIdentificationFiscale; }
    public void setNumeroIdentificationFiscale(String numeroIdentificationFiscale) {
        this.numeroIdentificationFiscale = numeroIdentificationFiscale;
    }

    public String getFormeJuridique() { return formeJuridique; }
    public void setFormeJuridique(String formeJuridique) { this.formeJuridique = formeJuridique; }

    public double getCapital() { return capital; }
    public void setCapital(double capital) { this.capital = capital; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }


    public boolean valider() {
        return nom != null && !nom.isEmpty()
                && email != null && email.contains("@")
                && raisonSociale != null && !raisonSociale.isEmpty();
    }
    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", email='" + email + '\'' +
                ", actif=" + actif +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Entreprise)) return false;
        Entreprise other = (Entreprise) obj;
        return id == other.id && Objects.equals(email, other.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}

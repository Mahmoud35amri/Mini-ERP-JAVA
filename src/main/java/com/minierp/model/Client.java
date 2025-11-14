package com.minierp.model;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    public enum TypeClient { PARTICULIER, ENTREPRISE }
    public enum CategorieClient { A, B, C }

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1000);

    // Attributs
    private int id;
    private String code; // CLI-XXXX
    private TypeClient type;
    private String civilite;
    private String nom;
    private String prenom;
    private String nomEntreprise;
    private String adresse;
    private String adresseComplement;
    private String ville;
    private String codePostal;
    private String pays;
    private String telephone;
    private String telephoneMobile;
    private String fax;
    private String email;
    private String siteWeb;
    private String numeroFiscal;
    private String numeroRegistreCommerce;
    private String conditionsPaiement;
    private int delaiPaiement;
    private double plafondCredit;
    private double remiseHabituelle;
    private String observations;
    private Date dateInscription;
    private Date dateModification;
    private boolean actif;
    private CategorieClient categorie;
    private double chiffreAffaires;
    private int nombreCommandes;
    private Date dernierAchat;


    private String genererCode() {
            int seq = SEQUENCE.getAndIncrement();
            return String.format("CLI-%04d", seq);
        }
        
    // Constructeurs
    public Client() {

        this.code = genererCode();
        this.type = TypeClient.PARTICULIER;
        this.actif = true;
        this.dateInscription = new Date();
    }

    public Client( String nom, String prenom, String email) {
        this();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Client(TypeClient type, String civilite, String nom, String prenom,
                  String nomEntreprise, String adresse, String ville, String codePostal,
                  String pays, String telephone, String email, double plafondCredit) {
        this(nom, prenom, email);
        this.type = type;
        this.civilite = civilite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.telephone = telephone;
        this.plafondCredit = plafondCredit;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public TypeClient getType() { return type; }
    public void setType(TypeClient type) { this.type = type; }

    public String getCivilite() { return civilite; }
    public void setCivilite(String civilite) { this.civilite = civilite; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNomEntreprise() { return nomEntreprise; }
    public void setNomEntreprise(String nomEntreprise) { this.nomEntreprise = nomEntreprise; }

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

    public String getTelephoneMobile() { return telephoneMobile; }
    public void setTelephoneMobile(String telephoneMobile) { this.telephoneMobile = telephoneMobile; }

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

    public double getPlafondCredit() { return plafondCredit; }
    public void setPlafondCredit(double plafondCredit) { this.plafondCredit = plafondCredit; }

    public double getRemiseHabituelle() { return remiseHabituelle; }
    public void setRemiseHabituelle(double remiseHabituelle) { this.remiseHabituelle = remiseHabituelle; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public Date getDateInscription() { return dateInscription; }
    public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public CategorieClient getCategorie() { return categorie; }
    public void setCategorie(CategorieClient categorie) { this.categorie = categorie; }

    public double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }

    public int getNombreCommandes() { return nombreCommandes; }
    public void setNombreCommandes(int nombreCommandes) { this.nombreCommandes = nombreCommandes; }

    public Date getDernierAchat() { return dernierAchat; }
    public void setDernierAchat(Date dernierAchat) { this.dernierAchat = dernierAchat; }

    // Méthodes métier
    public String getNomComplet() {
        if (nom == null && prenom == null) return "";
        if (prenom == null) return nom;
        if (nom == null) return prenom;
        return prenom + " " + nom;
    }

    

    public double calculerChiffreAffaires() {
        // Ici on renvoie la valeur stockée ; en vrai on ferait la somme des factures liées
        return this.chiffreAffaires;
    }

    public String getCategorieClient() {
        if (categorie == null) return "N/A";
        return categorie.name();
    }

    public boolean peutCommander() {
        if (!actif) return false;
        // Simple règle : si plafondCredit >= 0 ou client particulier, autorisé
        return (this.plafondCredit <= 0) ? true : (this.chiffreAffaires <= this.plafondCredit);
    }

    public boolean estBloque() {
        // Exemple simple : inactif ou dépassement de plafond
        return !this.actif || (this.plafondCredit > 0 && this.chiffreAffaires > this.plafondCredit);
    }

    public boolean valider() {
        return (this.nom != null && !this.nom.isEmpty()) || (this.nomEntreprise != null && !this.nomEntreprise.isEmpty())
                && this.email != null && this.email.contains("@");
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", entreprise='" + nomEntreprise + '\'' +
                ", email='" + email + '\'' +
                ", actif=" + actif +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id || Objects.equals(code, client.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}

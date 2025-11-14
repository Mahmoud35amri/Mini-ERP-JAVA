package com.minierp.model;

import java.util.Date;
import java.util.Objects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utilisateur {

    // === Énumération Role ===
    public enum Role {
        ADMIN,       // toutes permissions
        GERANT,      // gestion complète sauf utilisateurs
        EMPLOYE,     // lecture/écriture limitée
        VENDEUR      // commandes et clients uniquement
    }

    // === Attributs ===
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse; // hashé
    private String salt;
    private Role role;
    private String telephone;
    private String adresse;
    private Date dateNaissance;
    private Date dateEmbauche;
    private double salaire;
    private boolean actif;
    private Date dernierConnexion;
    private int nombreConnexions;
    private int tentativesEchouees;
    private Date dateCreation;
    private Date dateModification;

    // === Constructeurs ===
    public Utilisateur() {
        this.actif = true;
        this.dateCreation = new Date();
        this.dateModification = new Date();
        this.salt = genererSalt();
    }

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, Role role) {
        this();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = hasherMotDePasse(motDePasse, this.salt);
        this.role = role;
        
    }

    // === Getters et Setters ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = hasherMotDePasse(motDePasse, this.salt);
    }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }

    public Date getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(Date dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public Date getDernierConnexion() { return dernierConnexion; }
    public void setDernierConnexion(Date dernierConnexion) { this.dernierConnexion = dernierConnexion; }

    public int getNombreConnexions() { return nombreConnexions; }
    public void setNombreConnexions(int nombreConnexions) { this.nombreConnexions = nombreConnexions; }

    public int getTentativesEchouees() { return tentativesEchouees; }
    public void setTentativesEchouees(int tentativesEchouees) { this.tentativesEchouees = tentativesEchouees; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    // === Méthodes utilitaires ===
    private String genererSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private String hasherMotDePasse(String motDePasse, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String input = motDePasse + salt;
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }

    // === Méthodes métiers ===
    public boolean authentifier(String email, String motDePasse) {
        if (!this.email.equalsIgnoreCase(email)) return false;
        String hash = hasherMotDePasse(motDePasse, this.salt);
        if (hash.equals(this.motDePasse)) {
            this.tentativesEchouees = 0;
            this.nombreConnexions++;
            this.dernierConnexion = new Date();
            return true;
        } else {
            this.tentativesEchouees++;
            return false;
        }
    }

    public boolean changerMotDePasse(String ancienMdp, String nouveauMdp) {
        if (authentifier(this.email, ancienMdp)) {
            setMotDePasse(nouveauMdp);
            this.dateModification = new Date();
            return true;
        }
        return false;
    }

    public String reinitialiserMotDePasse() {
        String nouveau = "Tmp@" + (int)(Math.random() * 10000);
        setMotDePasse(nouveau);
        this.dateModification = new Date();
        return nouveau;
    }

    public void verouiller() {
        this.actif = false;
    }

    public void deverouiller() {
        this.actif = true;
        this.tentativesEchouees = 0;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public int getAge() {
        if (dateNaissance == null) return 0;
        long diff = new Date().getTime() - dateNaissance.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365));
    }

    public int getAnciennete() {
        if (dateEmbauche == null) return 0;
        long diff = new Date().getTime() - dateEmbauche.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365));
    }

    public boolean hasPermission(String permission) {
        if (role == Role.ADMIN) return true;
        if (role == Role.GERANT && !permission.equalsIgnoreCase("UTILISATEUR")) return true;
        if (role == Role.VENDEUR && (permission.equalsIgnoreCase("COMMANDE") || permission.equalsIgnoreCase("CLIENT")))
            return true;
        if (role == Role.EMPLOYE && permission.equalsIgnoreCase("LECTURE")) return true;
        else; return false;
    }
    public boolean valider() {
        if (nom == null || nom.trim().isEmpty()) return false;
        if (prenom == null || prenom.trim().isEmpty()) return false;
        if (email == null || !email.contains("@")) return false;
        if (motDePasse == null || motDePasse.length() < 6) return false;
        return role != null;
    }

    // === toString, equals, hashCode ===
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", actif=" + actif +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Utilisateur)) return false;
        Utilisateur other = (Utilisateur) obj;
        return id == other.id && Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}

package com.minierp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Categorie {

   
    private int id;
    private String code;
    private String nom;
    private String description;
    private Categorie categorieParente;
    private int niveau; // 0=racine,1=catégorie,2=sous-catégorie
    private int ordre;
    private boolean actif;
    private Date dateCreation;
    private Date dateModification;
    private int nombreProduits;

    public Categorie() {
        this.actif = true;
        this.dateCreation = new Date();
    }

    public Categorie(int id, String code, String nom) {
        this();
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    public Categorie(int id, String code, String nom, Categorie parent) {
        this(id, code, nom);
        this.categorieParente = parent;
        this.niveau = parent == null ? 0 : parent.getNiveau() + 1;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Categorie getCategorieParente() { return categorieParente; }
    public void setCategorieParente(Categorie categorieParente) {
        this.categorieParente = categorieParente;
        this.niveau = categorieParente == null ? 0 : categorieParente.getNiveau() + 1;
    }

    public int getNiveau() { return niveau; }
    public void setNiveau(int niveau) { this.niveau = niveau; }

    public int getOrdre() { return ordre; }
    public void setOrdre(int ordre) { this.ordre = ordre; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    public int getNombreProduits() { return nombreProduits; }
    public void setNombreProduits(int nombreProduits) { this.nombreProduits = nombreProduits; }

    public boolean estSousCategorie() {
        return this.categorieParente != null;
    }

    public boolean estCategorieRacine() {
        return this.categorieParente == null;
    }

    public String getCheminComplet() {
        List<String> path = new ArrayList<>();
        Categorie current = this;
        while (current != null) {
            path.add(0, current.getNom());
            current = current.getCategorieParente();
        }
        return String.join(" > ", path);
    }

    public List<Categorie> getSousCategories() {
        return new ArrayList<>();
    }

    public List<Produit> getProduits() {
        return new ArrayList<>();
    }

    public int compterProduits() {
        return this.nombreProduits;
    }
    public boolean valider(){
        if(code == null || code.isEmpty() || nom == null || nom.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", niveau=" + niveau +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id || Objects.equals(code, categorie.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}

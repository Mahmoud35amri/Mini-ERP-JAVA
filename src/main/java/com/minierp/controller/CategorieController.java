package com.minierp.controller;

import com.minierp.dao.CategorieDao;
import com.minierp.model.Categorie;

import java.util.List;

public class CategorieController {

    private static CategorieController instance;
    private final CategorieDao categorieDao;

    private CategorieController() {
        categorieDao = new CategorieDao();
    }

    public static synchronized CategorieController getInstance() {
        if (instance == null)
            instance = new CategorieController();
        return instance;
    }

    public boolean creer(Categorie c) {
        return categorieDao.creer(c);
    }

    public boolean modifier(Categorie c) {
        return categorieDao.modifier(c);
    }

    public boolean supprimer(int id) {
        return categorieDao.supprimer(id);
    }

    public Categorie rechercherParId(int id) {
        return categorieDao.rechercherParId(id);
    }

    public Categorie rechercherParCode(String code) {
        return categorieDao.rechercherParCode(code);
    }

    public List<Categorie> rechercherParNom(String nom) {
        return categorieDao.rechercherParNom(nom);
    }

    public List<Categorie> listerTout() {
        return categorieDao.listerTout();
    }

    public List<Categorie> listerActives() {
        return categorieDao.listerActives();
    }

    public List<Categorie> listerCategoriesRacines() {
        return categorieDao.listerCategoriesRacines();
    }

    public List<Categorie> listerSousCategories(int idParent) {
        return categorieDao.listerSousCategories(idParent);
    }

    public String getCheminComplet(int id) {
        return categorieDao.getCheminComplet(id);
    }

    public boolean deplacerCategorie(int id, int idNouveauParent) {
        return categorieDao.deplacerCategorie(id, idNouveauParent);
    }

    public int compterProduits(int idCategorie) {
        return categorieDao.compterProduits(idCategorie);
    }

    public int compter() {
        return categorieDao.compter();
    }
}

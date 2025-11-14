package com.minierp.dao;

import com.minierp.model.Categorie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CategorieDao {

    private final List<Categorie> categories = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public boolean creer(Categorie c) {
        c.setId(idCounter.incrementAndGet());
        categories.add(c);
        return true;
    }

    public boolean modifier(Categorie c) {
        if (c == null) return false;
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getId() == c.getId()) {
                    categories.set(i, c);
                    break;
                }
            }
        return false;
    }

    public boolean supprimer(int id) {
        Categorie c = rechercherParId(id);
        if (c != null) {
            categories.remove(c);
            return true;
        }
        return false;
    }

    public Categorie rechercherParId(int id) {
        for (Categorie categorie : categories) {
            if (categorie.getId() == id) {
                return categorie;
            }
        }
        return null;
    }

    public Categorie rechercherParCode(String code) {
        return categories.stream().filter(c -> c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }

    public List<Categorie> rechercherParNom(String nom) {
        return categories.stream().filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase())).toList();
    }

    public List<Categorie> listerTout() {
        return Collections.unmodifiableList(categories);
    }

    public List<Categorie> listerActives() {
        return categories.stream().filter(Categorie::isActif).toList();
    }

    public List<Categorie> listerCategoriesRacines() {
        return categories.stream().filter(c -> c.getNiveau() == 0 || c.getCategorieParente() == null).toList();
    }

    public List<Categorie> listerSousCategories(int idParent) {
        return categories.stream().filter(c -> c.getNiveau() != 0 && c.getCategorieParente().getId() == idParent).toList();
    }


    public String getCheminComplet(int id) {
        Categorie c = rechercherParId(id);
        if (c == null) return "";
        List<String> path = new ArrayList<>();
        while (c != null) {
            path.add(c.getNom());
            c = c.getCategorieParente();
        }
        Collections.reverse(path);
        return String.join(" > ", path);
    }

    public boolean deplacerCategorie(int id, int idNouveauParent) {
        Categorie c = rechercherParId(id);
        Categorie nouveauParent = rechercherParId(idNouveauParent);
        if (c != null) {
            c.setCategorieParente(nouveauParent);
            return modifier(c);
        }
        return false;
    }

    public int compterProduits(int idCategorie) {
        // Simplifié pour l’instant (peut être relié à ProduitController plus tard)
        return 0;
    }

    public int compter() {
        return categories.size();
    }
}

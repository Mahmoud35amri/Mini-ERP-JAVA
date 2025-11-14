package com.minierp.test;

import com.minierp.controller.CategorieController;
import com.minierp.model.Categorie;
import java.util.List;

public class TestCategorieController {

    public static void main(String[] args) {
        CategorieController controller = CategorieController.getInstance();
        
        System.out.println("===== TEST CATEGORIE CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        Categorie c1 = new Categorie(1, "Électronique", "Produits électroniques");
        Categorie c2 = new Categorie(2, "Informatique", "Matériel informatique");
        Categorie c3 = new Categorie(3, "Mobilier", "Mobilier de bureau");
        
        System.out.println("Création c1: " + controller.creer(c1));
        System.out.println("Création c2: " + controller.creer(c2));
        System.out.println("Création c3: " + controller.creer(c3));
        System.out.println("Total catégories: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Categorie> toutesCategories = controller.listerTout();
        toutesCategories.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Categorie catTrouvee = controller.rechercherParId(1);
        System.out.println("Catégorie ID=1: " + catTrouvee);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR CODE
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Code ---");
        Categorie catParCode = controller.rechercherParCode("INFO");
        System.out.println("Catégorie code INFO: " + catParCode);

        // ---------------------------------------------
        // 5. TEST RECHERCHE PAR NOM
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Recherche par Nom ---");
        List<Categorie> catParNom = controller.rechercherParNom("Informatique");
        System.out.println("Recherche 'Informatique':");
        catParNom.forEach(System.out::println);

        // ---------------------------------------------
        // 6. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Modification ---");
        if (c1 != null) {
            c1.setNom("Électronique Grand Public");
            c1.setDescription("Tous les produits électroniques");
            System.out.println("Modification c1: " + controller.modifier(c1));
            System.out.println("Après modification: " + controller.rechercherParId(c1.getId()));
        }

        // ---------------------------------------------
        // 7. TEST CATEGORIES ACTIVES
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister Actives ---");
        List<Categorie> actives = controller.listerActives();
        System.out.println("Catégories actives (" + actives.size() + "):");
        actives.forEach(System.out::println);

        // ---------------------------------------------
        // 8. TEST CATEGORIES RACINES
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Lister Catégories Racines ---");
        List<Categorie> racines = controller.listerCategoriesRacines();
        System.out.println("Catégories racines (" + racines.size() + "):");
        racines.forEach(System.out::println);

        // ---------------------------------------------
        // 9. TEST SOUS-CATEGORIES
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Sous-Catégories ---");
        Categorie sousCategorie = new Categorie(4, "Ordinateurs", "PC et portables");
        sousCategorie.setCategorieParente(c2); // Sous-catégorie de Informatique
        controller.creer(sousCategorie);
        
        List<Categorie> sousCategories = controller.listerSousCategories(c2.getId());
        System.out.println("Sous-catégories de " + c2.getNom() + ":");
        sousCategories.forEach(System.out::println);

        // ---------------------------------------------
        // 10. TEST CHEMIN COMPLET
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Chemin Complet ---");
        String chemin = controller.getCheminComplet(sousCategorie.getId());
        System.out.println("Chemin de la sous-catégorie: " + chemin);

        // ---------------------------------------------
        // 11. TEST DEPLACER CATEGORIE
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Déplacer Catégorie ---");
        System.out.println("Déplacement vers c1: " + 
            controller.deplacerCategorie(sousCategorie.getId(), c1.getId()));
        System.out.println("Nouveau chemin: " + 
            controller.getCheminComplet(sousCategorie.getId()));

        // ---------------------------------------------
        // 12. TEST COMPTER PRODUITS
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Compter Produits ---");
        int nbProduits = controller.compterProduits(c1.getId());
        System.out.println("Nombre de produits dans " + c1.getNom() + ": " + nbProduits);

        // ---------------------------------------------
        // 13. TEST DESACTIVATION
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Désactivation ---");
        c3.setActif(false);
        controller.modifier(c3);
        System.out.println("Catégories actives après désactivation:");
        controller.listerActives().forEach(c -> System.out.println(c.getNom()));

        // ---------------------------------------------
        // 14. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Suppression ---");
        System.out.println("Suppression c3: " + controller.supprimer(c3.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 15. TEST RECHERCHE INEXISTANTE
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Recherche Inexistante ---");
        Categorie inexistante = controller.rechercherParId(999);
        System.out.println("Catégorie ID=999: " + 
            (inexistante == null ? "Non trouvée" : inexistante));

        System.out.println("\n===== FIN DES TESTS CATEGORIE =====");
    }
}
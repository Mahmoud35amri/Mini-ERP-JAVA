package com.minierp.test;

import com.minierp.controller.ProduitController;
import com.minierp.controller.CategorieController;
import com.minierp.controller.FournisseurController;
import com.minierp.model.Produit;
import com.minierp.model.Categorie;
import com.minierp.model.Fournisseur;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class TestProduitController {

    public static void main(String[] args) {
        ProduitController controller = ProduitController.getInstance();
        CategorieController catCtrl = CategorieController.getInstance();
        FournisseurController fourCtrl = FournisseurController.getInstance();
        
        System.out.println("===== TEST PRODUIT CONTROLLER =====\n");

        // Récupérer catégorie et fournisseur pour les tests
        Categorie categorie = catCtrl.rechercherParId(1);
        Fournisseur fournisseur = fourCtrl.rechercherParId(1);

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        Produit p1 = new Produit("REF001","PC Dell 15 pouces",  categorie, fournisseur,  
                                 2500.0, 3000,20);
        p1.setCodeBarres("1234567890123");
        
        Produit p2 = new Produit("REF002", "Souris Sans Fil", categorie , fournisseur
                                 ,45.0, 50,20);
        p2.setCodeBarres("2234567890123");
        
        Produit p3 = new Produit("REF003", "Clavier Mécanique",categorie, fournisseur 
                                 ,150.0, 200, 20);
        p3.setCodeBarres("3234567890123");
        p3.setSeuilAlerte(10);
        
        System.out.println("Création p1: " + controller.creer(p1));
        System.out.println("Création p2: " + controller.creer(p2));
        System.out.println("Création p3: " + controller.creer(p3));
        System.out.println("Total produits: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Produit> tousProduits = controller.listerTout();
        tousProduits.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHES
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherches ---");
        System.out.println("Par ID: " + controller.rechercherParId(1));
        System.out.println("Par Référence: " + controller.rechercherParReference("REF002"));
        System.out.println("Par Code-barres: " + controller.rechercherParCodeBarres("1234567890123"));
        
        List<Produit> parNom = controller.rechercherParNom("Souris");
        System.out.println("Par Nom 'Souris': " + parNom.size() + " résultat(s)");
        parNom.forEach(p -> System.out.println("  " + p.getNom()));

        // ---------------------------------------------
        // 4. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Modification ---");
        if (p1 != null) {
            p1.setPrixVente(3300.0);
            p1.setDescription("PC Dell 15 pouces - Nouvelle génération");
            System.out.println("Modification p1: " + controller.modifier(p1));
            System.out.println("Après modification: " + controller.rechercherParId(p1.getId()));
        }

        // ---------------------------------------------
        // 5. TEST PRODUITS ACTIFS
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Lister Actifs ---");
        List<Produit> actifs = controller.listerActifs();
        System.out.println("Produits actifs (" + actifs.size() + "):");
        actifs.forEach(p -> System.out.println("  " + p.getNom()));

        // ---------------------------------------------
        // 6. TEST LISTER PAR CATEGORIE
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Lister par Catégorie ---");
        List<Produit> parCategorie = controller.listerParCategorie(categorie);
        System.out.println("Produits de la catégorie " + categorie.getNom() + 
                          " (" + parCategorie.size() + "):");
        parCategorie.forEach(p -> System.out.println("  " + p.getNom()));

        // ---------------------------------------------
        // 7. TEST LISTER PAR FOURNISSEUR
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister par Fournisseur ---");
        List<Produit> parFournisseur = controller.listerParFournisseur(fournisseur);
        System.out.println("Produits du fournisseur " + fournisseur.getNomEntreprise() + 
                          " (" + parFournisseur.size() + "):");
        parFournisseur.forEach(p -> System.out.println("  " + p.getNom()));

        // ---------------------------------------------
        // 8. TEST AJUSTEMENT STOCK
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Ajustement Stock ---");
        System.out.println("Stock actuel p1: " + p1.getQuantiteStock());
        System.out.println("Ajout de 20 unités: " + 
            controller.ajusterStock(p1.getId(), 20, "Réapprovisionnement"));
        System.out.println("Nouveau stock: " + 
            controller.rechercherParId(p1.getId()).getQuantiteStock());
        
        System.out.println("Retrait de 5 unités: " + 
            controller.ajusterStock(p1.getId(), -5, "Vente"));
        System.out.println("Nouveau stock: " + 
            controller.rechercherParId(p1.getId()).getQuantiteStock());

        // ---------------------------------------------
        // 9. TEST RUPTURE DE STOCK
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Rupture de Stock ---");
        Produit pRupture = new Produit("REF999", "Produit Épuisé",categorie, fournisseur , 
                                       100.0, 150 ,0);
        controller.creer(pRupture);
        
        List<Produit> enRupture = controller.listerEnRupture();
        System.out.println("Produits en rupture (" + enRupture.size() + "):");
        enRupture.forEach(p -> System.out.println("  " + p.getNom() + " - Stock: " + 
                                                   p.getQuantiteStock()));

        // ---------------------------------------------
        // 10. TEST SEUIL ALERTE
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Seuil Alerte ---");
        List<Produit> sousSeuilAlerte = controller.listerSousSeuilAlerte();
        System.out.println("Produits sous seuil d'alerte (" + sousSeuilAlerte.size() + "):");
        sousSeuilAlerte.forEach(p -> System.out.println("  " + p.getNom() + 
            " - Stock: " + p.getQuantiteStock() + " / Seuil: " + p.getSeuilAlerte()));

        // ---------------------------------------------
        // 11. TEST PROMOTIONS
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Promotions ---");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date dateFin = cal.getTime();
        
        System.out.println("Appliquer promotion 20% sur p2: " + 
            controller.appliquerPromotion(p2.getId(), 20.0, dateFin));
        Produit p2Updated = controller.rechercherParId(p2.getId());
        System.out.println("Prix original: " + p2.getPrixVente() + " DT");
        System.out.println("Taux promotion: " + p2Updated.getTauxPromo() + "%");
        System.out.println("Prix promotionnel: " + p2Updated.getPrixVenteActuel() + " DT");
        
        List<Produit> enPromotion = controller.listerEnPromotion();
        System.out.println("\nProduits en promotion (" + enPromotion.size() + "):");
        enPromotion.forEach(p -> System.out.println("  " + p.getNom() + 
            " - " + p.getTauxPromo() + "% de réduction"));

        // ---------------------------------------------
        // 12. TEST ANNULER PROMOTION
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Annuler Promotion ---");
        System.out.println("Annuler promotion p2: " + controller.annulerPromotion(p2.getId()));
        p2Updated = controller.rechercherParId(p2.getId());
        System.out.println("Taux promotion après annulation: " + p2Updated.getTauxPromo());

        // ---------------------------------------------
        // 13. TEST NOUVEAUTES
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Nouveautés ---");
        List<Produit> nouveautes = controller.listerNouveautes();
        System.out.println("Nouveaux produits (" + nouveautes.size() + "):");
        nouveautes.forEach(p -> System.out.println("  " + p.getNom() + 
            " - Créé le: " + p.getDateLancement()));

        // ---------------------------------------------
        // 14. TEST VALEUR STOCK
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Valeur Stock ---");
        double valeurStock = controller.calculerValeurStock();
        System.out.println("Valeur totale du stock: " + String.format("%.2f", valeurStock) + " DT");

        // ---------------------------------------------
        // 15. TEST DESACTIVATION
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Désactivation ---");
        pRupture.setActif(false);
        controller.modifier(pRupture);
        System.out.println("Produits actifs après désactivation: " + 
            controller.listerActifs().size());

        // ---------------------------------------------
        // 16. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Suppression ---");
        System.out.println("Suppression pRupture: " + controller.supprimer(pRupture.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 17. TEST RECHERCHES INEXISTANTES
        // ---------------------------------------------
        System.out.println("\n--- 17. Test Recherches Inexistantes ---");
        System.out.println("Produit ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Référence XXX: " + 
            (controller.rechercherParReference("XXX") == null ? "Non trouvée" : "Trouvée"));
        System.out.println("Code-barres 999: " + 
            (controller.rechercherParCodeBarres("999") == null ? "Non trouvé" : "Trouvé"));

        // ---------------------------------------------
        // 18. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 18. Test Validation ---");
        Produit invalide = new Produit("", "",null, null , -10.0, -5, -2);
        System.out.println("Produit invalide valide? " + invalide.valider());
        System.out.println("Produit p1 valide? " + p1.valider());

        // ---------------------------------------------
        // 19. TEST STATISTIQUES
        // ---------------------------------------------
        System.out.println("\n--- 19. Test Statistiques ---");
        System.out.println("Total produits: " + controller.compter());
        System.out.println("Produits actifs: " + controller.listerActifs().size());
        System.out.println("Produits en rupture: " + controller.listerEnRupture().size());
        System.out.println("Produits sous seuil alerte: " + controller.listerSousSeuilAlerte().size());
        System.out.println("Produits en promotion: " + controller.listerEnPromotion().size());
        System.out.println("Nouveautés: " + controller.listerNouveautes().size());

        System.out.println("\n===== FIN DES TESTS PRODUIT =====");
    }
}
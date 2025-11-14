package com.minierp.test;

import com.minierp.controller.StockController;
import com.minierp.controller.ProduitController;
import com.minierp.model.Stock;
import com.minierp.model.MouvementStock;
import com.minierp.model.Produit;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class TestStockController {

    public static void main(String[] args) {
        StockController controller = StockController.getInstance();
        ProduitController produitCtrl = ProduitController.getInstance();
        
        System.out.println("===== TEST STOCK CONTROLLER =====\n");

        // Récupérer des produits pour les tests
        Produit p1 = produitCtrl.rechercherParId(1);
        Produit p2 = produitCtrl.rechercherParId(2);
        Produit p3 = produitCtrl.rechercherParId(3);

        // ---------------------------------------------
        // 1. TEST INITIALISATION STOCK
        // ---------------------------------------------
        System.out.println("--- 1. Test Initialisation Stock ---");
        if (p1 != null) {
            System.out.println("Initialiser stock p1 (100 unités): " + 
                controller.initialiser(p1.getId(), 100));
        }
        if (p2 != null) {
            System.out.println("Initialiser stock p2 (50 unités): " + 
                controller.initialiser(p2.getId(), 50));
        }
        if (p3 != null) {
            System.out.println("Initialiser stock p3 (25 unités): " + 
                controller.initialiser(p3.getId(), 25));
        }

        // ---------------------------------------------
        // 2. TEST GET STOCK
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Get Stock ---");
        if (p1 != null) {
            Stock stock1 = controller.getStock(p1.getId());
            System.out.println("Stock p1: " + stock1);
        }
        if (p2 != null) {
            Stock stock2 = controller.getStock(p2.getId());
            System.out.println("Stock p2: " + stock2);
        }

        // ---------------------------------------------
        // 3. TEST AJOUTER STOCK
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Ajouter Stock ---");
        if (p1 != null) {
            System.out.println("Stock actuel p1: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
            System.out.println("Ajouter 30 unités: " + 
                controller.ajouterStock(p1.getId(), 30, "Réapprovisionnement"));
            System.out.println("Nouveau stock: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 4. TEST RETIRER STOCK
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Retirer Stock ---");
        if (p1 != null) {
            System.out.println("Stock actuel p1: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
            System.out.println("Retirer 20 unités: " + 
                controller.retirerStock(p1.getId(), 20, "Vente"));
            System.out.println("Nouveau stock: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 5. TEST AJUSTER STOCK
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Ajuster Stock ---");
        if (p2 != null) {
            System.out.println("Stock actuel p2: " + 
                controller.getStock(p2.getId()).getQuantiteDisponible());
            System.out.println("Ajuster +15 unités: " + 
                controller.ajusterStock(p2.getId(), 15, "Correction inventaire"));
            System.out.println("Nouveau stock: " + 
                controller.getStock(p2.getId()).getQuantiteDisponible());
            
            System.out.println("Ajuster -10 unités: " + 
                controller.ajusterStock(p2.getId(), -10, "Produits endommagés"));
            System.out.println("Nouveau stock: " + 
                controller.getStock(p2.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 6. TEST RESERVER STOCK
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Réserver Stock ---");
        if (p1 != null) {
            Stock avant = controller.getStock(p1.getId());
            System.out.println("Avant réservation - Disponible: " + 
                avant.getQuantiteDisponible() + ", Réservé: " + avant.getQuantiteReservee());
            
            System.out.println("Réserver 25 unités: " + 
                controller.reserverStock(p1.getId(), 25));
            
            Stock apres = controller.getStock(p1.getId());
            System.out.println("Après réservation - Disponible: " + 
                apres.getQuantiteDisponible() + ", Réservé: " + apres.getQuantiteReservee());
        }

        // ---------------------------------------------
        // 7. TEST LIBERER RESERVATION
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Libérer Réservation ---");
        if (p1 != null) {
            Stock avant = controller.getStock(p1.getId());
            System.out.println("Avant libération - Disponible: " + 
                avant.getQuantiteDisponible() + ", Réservé: " + avant.getQuantiteReservee());
            
            System.out.println("Libérer 15 unités: " + 
                controller.libererReservation(p1.getId(), 15));
            
            Stock apres = controller.getStock(p1.getId());
            System.out.println("Après libération - Disponible: " + 
                apres.getQuantiteDisponible() + ", Réservé: " + apres.getQuantiteReservee());
        }

        // ---------------------------------------------
        // 8. TEST TRANSFERER
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Transfert ---");
        if (p1 != null && p3 != null) {
            System.out.println("Stock p1 avant: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
            System.out.println("Stock p3 avant: " + 
                controller.getStock(p3.getId()).getQuantiteDisponible());
            
            System.out.println("Transférer 10 unités de p1 vers p3: " + 
                controller.transferer(p1.getId(), p3.getId(), 10));
            
            System.out.println("Stock p1 après: " + 
                controller.getStock(p1.getId()).getQuantiteDisponible());
            System.out.println("Stock p3 après: " + 
                controller.getStock(p3.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 9. TEST INVENTORIER
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Inventaire ---");
        if (p2 != null) {
            System.out.println("Stock théorique p2: " + 
                controller.getStock(p2.getId()).getQuantiteDisponible());
            System.out.println("Inventorier p2 (quantité réelle: 60): " + 
                controller.inventorier(p2.getId(), 60));
            System.out.println("Stock après inventaire: " + 
                controller.getStock(p2.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 10. TEST INVENTORIER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Inventaire Global ---");
        System.out.println("Inventorier tous les stocks: " + 
            controller.inventorierTout());

        // ---------------------------------------------
        // 11. TEST GET MOUVEMENTS
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Historique Mouvements ---");
        if (p1 != null) {
            List<MouvementStock> mouvements = controller.getMouvements(p1.getId());
            System.out.println("Mouvements du produit " + p1.getNom() + 
                " (" + mouvements.size() + "):");
            mouvements.forEach(m -> System.out.println("  " + m.getTypeMouvement() + 
                " - Quantité: " + m.getQuantite() + " - Motif: " + m.getMotif()));
        }

        // ---------------------------------------------
        // 12. TEST GET MOUVEMENTS PAR PERIODE
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Mouvements par Période ---");
        Calendar cal = Calendar.getInstance();
        Date fin = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date debut = cal.getTime();
        
        List<MouvementStock> mouvementsPeriode = 
            controller.getMouvementsParPeriode(debut, fin);
        System.out.println("Mouvements des 7 derniers jours (" + 
            mouvementsPeriode.size() + "):");
        mouvementsPeriode.forEach(m -> System.out.println("  " + 
            m.getProduit().getNom() + " - " + m.getTypeMouvement() + " - " + m.getQuantite()));

        // ---------------------------------------------
        // 13. TEST VALEUR STOCK TOTAL
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Valeur Stock Total ---");
        double valeurStock = controller.calculerValeurStockTotal();
        System.out.println("Valeur totale du stock: " + 
            String.format("%.2f", valeurStock) + " DT");

        // ---------------------------------------------
        // 14. TEST PRODUITS EN RUPTURE
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Produits en Rupture ---");
        List<Produit> enRupture = controller.listerProduitsEnRupture();
        System.out.println("Produits en rupture de stock (" + enRupture.size() + "):");
        enRupture.forEach(p -> System.out.println("  " + p.getNom() + 
            " - Stock: " + p.getQuantiteStock()));

        // ---------------------------------------------
        // 15. TEST PRODUITS SOUS SEUIL ALERTE
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Produits Sous Seuil Alerte ---");
        List<Produit> sousSeuilAlerte = controller.listerProduitsSousSeuilAlerte();
        System.out.println("Produits sous seuil d'alerte (" + sousSeuilAlerte.size() + "):");
        sousSeuilAlerte.forEach(p -> {
            Stock s = controller.getStock(p.getId());
            System.out.println("  " + p.getNom() + " - Stock: " + 
                s.getQuantiteDisponible() + " / Seuil alerte: " + p.getSeuilAlerte());
        });

        // ---------------------------------------------
        // 16. TEST PRODUITS SOUS SEUIL MINIMUM
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Produits Sous Seuil Minimum ---");
        List<Produit> sousSeuilMinimum = controller.listerProduitsSousSeuilMinimum();
        System.out.println("Produits sous seuil minimum (" + sousSeuilMinimum.size() + "):");
        sousSeuilMinimum.forEach(p -> {
            Stock s = controller.getStock(p.getId());
            System.out.println("  " + p.getNom() + " - Stock: " + 
                s.getQuantiteDisponible() + " / Seuil minimum: " + p.getSeuilMinimum());
        });

        // ---------------------------------------------
        // 17. TEST TENTATIVE RETRAIT STOCK INSUFFISANT
        // ---------------------------------------------
        System.out.println("\n--- 17. Test Retrait Stock Insuffisant ---");
        if (p3 != null) {
            Stock stock3 = controller.getStock(p3.getId());
            System.out.println("Stock actuel p3: " + stock3.getQuantiteDisponible());
            System.out.println("Tentative de retirer 1000 unités: " + 
                controller.retirerStock(p3.getId(), 1000, "Test"));
            System.out.println("Stock après tentative: " + 
                controller.getStock(p3.getId()).getQuantiteDisponible());
        }

        // ---------------------------------------------
        // 18. TEST STATISTIQUES
        // ---------------------------------------------
        System.out.println("\n--- 18. Test Statistiques ---");
        int totalProduits = produitCtrl.compter();
        int nbRupture = controller.listerProduitsEnRupture().size();
        int nbSousAlerte = controller.listerProduitsSousSeuilAlerte().size();
        int nbSousMinimum = controller.listerProduitsSousSeuilMinimum().size();
        
        System.out.println("Total produits: " + totalProduits);
        System.out.println("Produits en rupture: " + nbRupture + 
            " (" + String.format("%.1f", (nbRupture * 100.0 / totalProduits)) + "%)");
        System.out.println("Produits sous seuil alerte: " + nbSousAlerte);
        System.out.println("Produits sous seuil minimum: " + nbSousMinimum);
        System.out.println("Valeur totale stock: " + 
            String.format("%.2f", valeurStock) + " DT");

        System.out.println("\n===== FIN DES TESTS STOCK =====");
    }
}
package com.minierp.test;

import com.minierp.controller.LigneCommandeController;
import com.minierp.controller.CommandeController;
import com.minierp.controller.ProduitController;
import com.minierp.model.LigneCommande;
import com.minierp.model.Commande;
import com.minierp.model.Produit;
import java.util.List;

public class TestLigneCommandeController {

    public static void main(String[] args) {
        LigneCommandeController controller = LigneCommandeController.getInstance();
        CommandeController cmdCtrl = CommandeController.getInstance();
        ProduitController produitCtrl = ProduitController.getInstance();
        
        System.out.println("===== TEST LIGNE COMMANDE CONTROLLER =====\n");

        // Récupérer commandes et produits pour les tests
        Commande cmd1 = cmdCtrl.rechercherParId(1);
        Commande cmd2 = cmdCtrl.rechercherParId(2);
        Produit p1 = produitCtrl.rechercherParId(1);
        Produit p2 = produitCtrl.rechercherParId(2);
        Produit p3 = produitCtrl.rechercherParId(3);

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        LigneCommande lc1 = new LigneCommande( p1, 2, p1.getPrixVente(),19);
        LigneCommande lc2 = new LigneCommande( p2, 5, p2.getPrixVente(),19);
        LigneCommande lc3 = new LigneCommande( p1, 1, p1.getPrixVente(),19);
        LigneCommande lc4 = new LigneCommande( p3, 3, p3.getPrixVente(),19);
        
        System.out.println("Création lc1: " + controller.creer(lc1));
        System.out.println("Création lc2: " + controller.creer(lc2));
        System.out.println("Création lc3: " + controller.creer(lc3));
        System.out.println("Création lc4: " + controller.creer(lc4));
        System.out.println("Total lignes: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Recherche par ID ---");
        LigneCommande ligneTrouvee = controller.rechercherParId(1);
        System.out.println("Ligne ID=1: " + ligneTrouvee);

        // ---------------------------------------------
        // 3. TEST LISTER PAR COMMANDE
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Lister par Commande ---");
        if (cmd1 != null) {
            List<LigneCommande> lignesCmd1 = controller.listerParCommande(cmd1.getId());
            System.out.println("Lignes de la commande " + cmd1.getNumeroCommande() + 
                " (" + lignesCmd1.size() + "):");
            for (LigneCommande lc : lignesCmd1) {
                System.out.println("  " + lc.getProduit().getNom() + 
                    " - Qté: " + lc.getQuantite() + 
                    " - PU: " + lc.getPrixUnitaireHT() + " DT" +
                    " - Total: " + (lc.getQuantite() * lc.getPrixUnitaireHT()) + " DT");
            }
        }

        if (cmd2 != null) {
            List<LigneCommande> lignesCmd2 = controller.listerParCommande(cmd2.getId());
            System.out.println("\nLignes de la commande " + cmd2.getNumeroCommande() + 
                " (" + lignesCmd2.size() + "):");
            for (LigneCommande lc : lignesCmd2) {
                System.out.println("  " + lc.getProduit().getNom() + 
                    " - Qté: " + lc.getQuantite() + 
                    " - PU: " + lc.getPrixUnitaireHT() + " DT");
            }
        }

        // ---------------------------------------------
        // 4. TEST LISTER PAR PRODUIT
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Lister par Produit ---");
        if (p1 != null) {
            List<LigneCommande> lignesP1 = controller.listerParProduit(p1.getId());
            System.out.println("Lignes contenant " + p1.getNom() + 
                " (" + lignesP1.size() + "):");
            for (LigneCommande lc : lignesP1) {
                System.out.println("  Commande " + lc.getCommande().getNumeroCommande() + 
                    " - Qté: " + lc.getQuantite());
            }
        }

        // ---------------------------------------------
        // 5. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Modification ---");
        if (lc1 != null) {
            System.out.println("Avant modification:");
            System.out.println("  Quantité: " + lc1.getQuantite());
            System.out.println("  Prix unitaire: " + lc1.getPrixUnitaireHT() + " DT");
            
            lc1.setQuantite(3);
            lc1.setPrixUnitaireHT(2200.0);
            lc1.setTauxRemise(10.0);
            System.out.println("Modification lc1: " + controller.modifier(lc1));
            
            LigneCommande lc1Updated = controller.rechercherParId(lc1.getId());
            System.out.println("Après modification:");
            System.out.println("  Quantité: " + lc1Updated.getQuantite());
            System.out.println("  Prix unitaire: " + lc1Updated.getPrixUnitaireHT() + " DT");
            System.out.println("  Remise: " + lc1Updated.getTauxRemise() + "%");
            System.out.println("  Montant total: " + lc1Updated.getMontantTTC() + " DT");
        }

        // ---------------------------------------------
        // 6. TEST CALCUL MONTANT TOTAL COMMANDE
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Calcul Montant Total ---");
        if (cmd1 != null) {
            double montantTotal = controller.calculerMontantTotal(cmd1.getId());
            System.out.println("Montant total de la commande " + cmd1.getNumeroCommande() + 
                ": " + String.format("%.2f", montantTotal) + " DT");
            
            List<LigneCommande> lignes = controller.listerParCommande(cmd1.getId());
            System.out.println("Détail:");
            for (LigneCommande lc : lignes) {
                double sousTotal = lc.getQuantite() * lc.getPrixUnitaireHT();
                double remise = sousTotal * lc.getTauxRemise() / 100;
                double total = sousTotal - remise;
                System.out.println("  " + lc.getProduit().getNom() + 
                    ": " + lc.getQuantite() + " x " + lc.getPrixUnitaireHT() + 
                    " DT - Remise " + lc.getTauxRemise() + "% = " + 
                    String.format("%.2f", total) + " DT");
            }
        }

        // ---------------------------------------------
        // 7. TEST VERIFIER DISPONIBILITE
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Vérifier Disponibilité ---");
        System.out.println("Disponibilité lc1: " + controller.verifierDisponibilite(lc1));
        System.out.println("Disponibilité lc2: " + controller.verifierDisponibilite(lc2));
        
        // Créer une ligne avec quantité excessive
        LigneCommande lcExcessive = new LigneCommande( p1, 10000, 200,19);
        System.out.println("Disponibilité ligne excessive (10000 unités): " + 
            controller.verifierDisponibilite(lcExcessive));

        // ---------------------------------------------
        // 8. TEST REMISE
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Application de Remise ---");
        lc2.setTauxRemise(15.0);
        controller.modifier(lc2);
        LigneCommande lc2Updated = controller.rechercherParId(lc2.getId());
        
        double montantSansRemise = lc2Updated.getQuantite() * lc2Updated.getPrixUnitaireHT();
        double montantRemise = montantSansRemise * lc2Updated.getTauxRemise() / 100;
        double montantAvecRemise = montantSansRemise - montantRemise;
        
        System.out.println("Produit: " + lc2Updated.getProduit().getNom());
        System.out.println("Quantité: " + lc2Updated.getQuantite());
        System.out.println("Prix unitaire: " + lc2Updated.getPrixUnitaireHT() + " DT");
        System.out.println("Montant sans remise: " + String.format("%.2f", montantSansRemise) + " DT");
        System.out.println("Remise " + lc2Updated.getTauxRemise() + "%: " + 
            String.format("%.2f", montantRemise) + " DT");
        System.out.println("Montant avec remise: " + String.format("%.2f", montantAvecRemise) + " DT");

        // ---------------------------------------------
        // 9. TEST STATISTIQUES PRODUIT
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Statistiques par Produit ---");
        if (p1 != null) {
            List<LigneCommande> lignesP1 = controller.listerParProduit(p1.getId());
            int quantiteTotale = lignesP1.stream()
                .mapToInt(LigneCommande::getQuantite)
                .sum();
            double caTotal = lignesP1.stream()
                .mapToDouble(lc -> lc.getQuantite() * lc.getPrixUnitaireHT() * 
                    (1 - lc.getTauxRemise() / 100))
                .sum();
            
            System.out.println("Statistiques pour " + p1.getNom() + ":");
            System.out.println("  Nombre de commandes: " + lignesP1.size());
            System.out.println("  Quantité totale vendue: " + quantiteTotale);
            System.out.println("  CA total généré: " + String.format("%.2f", caTotal) + " DT");
        }

        // ---------------------------------------------
        // 10. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Suppression ---");
        System.out.println("Suppression lc4: " + controller.supprimer(lc4.getId()));
        System.out.println("Total après suppression: " + controller.compter());
        
        if (cmd2 != null) {
            System.out.println("Lignes restantes pour commande " + cmd2.getNumeroCommande() + 
                ": " + controller.listerParCommande(cmd2.getId()).size());
        }

        // ---------------------------------------------
        // 11. TEST RECHERCHE INEXISTANTE
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Recherche Inexistante ---");
        System.out.println("Ligne ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvée" : "Trouvée"));

        // ---------------------------------------------
        // 12. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Validation ---");
        LigneCommande invalide = new LigneCommande(null, 0, -5, -100.0);
        System.out.println("Ligne invalide valide? " + invalide.valider());
        System.out.println("Ligne lc1 valide? " + lc1.valider());

        // ---------------------------------------------
        // 13. TEST STATISTIQUES GLOBALES
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Statistiques Globales ---");
        System.out.println("Total lignes de commande: " + controller.compter());
        
        int quantiteTotale = 0;
        double caTotal = 0.0;
        for (int i = 1; i <= controller.compter(); i++) {
            LigneCommande lc = controller.rechercherParId(i);
            if (lc != null) {
                quantiteTotale += lc.getQuantite();
                caTotal += lc.getQuantite() * lc.getPrixUnitaireHT() * 
                    (1 - lc.getTauxRemise() / 100);
            }
        }
        
        System.out.println("Quantité totale articles: " + quantiteTotale);
        System.out.println("CA total: " + String.format("%.2f", caTotal) + " DT");
        System.out.println("Panier moyen par ligne: " + 
            String.format("%.2f", caTotal / controller.compter()) + " DT");

        System.out.println("\n===== FIN DES TESTS LIGNE COMMANDE =====");
    }
}
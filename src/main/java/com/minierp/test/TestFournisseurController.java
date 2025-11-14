package com.minierp.test;

import com.minierp.controller.FournisseurController;
import com.minierp.model.Fournisseur;
import java.util.List;

public class TestFournisseurController {

    public static void main(String[] args) {
        FournisseurController controller = FournisseurController.getInstance();
        
        System.out.println("===== TEST FOURNISSEUR CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        Fournisseur f1 = new Fournisseur(1, "ElectroDistrib", "contact@electro.com");
        Fournisseur f2 = new Fournisseur(2, "InfoStock", "info@infostock.com");
        Fournisseur f3 = new Fournisseur(3, "MegaSupply", "sales@megasupply.com");
        
        System.out.println("Création f1: " + controller.creer(f1));
        System.out.println("Création f2: " + controller.creer(f2));
        System.out.println("Création f3: " + controller.creer(f3));
        System.out.println("Total fournisseurs: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Fournisseur> tousFournisseurs = controller.listerTout();
        tousFournisseurs.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Fournisseur fournisseurTrouve = controller.rechercherParId(1);
        System.out.println("Fournisseur ID=1: " + fournisseurTrouve);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR CODE
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Code ---");
        Fournisseur fournisseurParCode = controller.rechercherParCode("FOUR002");
        System.out.println("Fournisseur code FOUR002: " + fournisseurParCode);

        // ---------------------------------------------
        // 5. TEST RECHERCHE PAR NOM
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Recherche par Nom ---");
        List<Fournisseur> fournisseursParNom = controller.rechercherParNom("Info");
        System.out.println("Recherche 'Info':");
        fournisseursParNom.forEach(System.out::println);

        // ---------------------------------------------
        // 6. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Modification ---");
        if (f1 != null) {
            f1.setTelephone("0698765432");
            f1.setAdresse("999 Nouvelle Adresse");
            System.out.println("Modification f1: " + controller.modifier(f1));
            System.out.println("Après modification: " + controller.rechercherParId(f1.getId()));
        }

        // ---------------------------------------------
        // 7. TEST FOURNISSEURS ACTIFS
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister Actifs ---");
        List<Fournisseur> actifs = controller.listerActifs();
        System.out.println("Fournisseurs actifs (" + actifs.size() + "):");
        actifs.forEach(f -> System.out.println("  " + f.getNomEntreprise()));

        // ---------------------------------------------
        // 8. TEST EVALUATION
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Évaluation ---");
        System.out.println("Évaluation f1 (note 5): " + controller.evaluer(f1.getId(), 5));
        System.out.println("Évaluation f2 (note 4): " + controller.evaluer(f2.getId(), 4));
        System.out.println("Évaluation f3 (note 3): " + controller.evaluer(f3.getId(), 3));
        
        System.out.println("\nFournisseurs après évaluation:");
        controller.listerTout().forEach(f -> 
            System.out.println(f.getNomEntreprise() + " - Note: " + f.getEvaluation()));

        // ---------------------------------------------
        // 9. TEST LISTER PAR EVALUATION
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Lister par Évaluation ---");
        List<Fournisseur> note5 = controller.listerParEvaluation(5);
        System.out.println("Fournisseurs avec note 5 (" + note5.size() + "):");
        note5.forEach(f -> System.out.println("  " + f.getNomEntreprise()));
        
        List<Fournisseur> note4 = controller.listerParEvaluation(4);
        System.out.println("Fournisseurs avec note 4 (" + note4.size() + "):");
        note4.forEach(f -> System.out.println("  " + f.getNomEntreprise()));

        // ---------------------------------------------
        // 10. TEST CALCUL TOTAL ACHATS
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Calcul Total Achats ---");
        double totalF1 = controller.calculerTotalAchats(f1.getId());
        double totalF2 = controller.calculerTotalAchats(f2.getId());
        System.out.println("Total achats " + f1.getNomEntreprise() + ": " + totalF1 + " DT");
        System.out.println("Total achats " + f2.getNomEntreprise() + ": " + totalF2 + " DT");

        // ---------------------------------------------
        // 11. TEST DESACTIVATION
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Désactivation ---");
        f3.setActif(false);
        controller.modifier(f3);
        System.out.println("Fournisseurs actifs après désactivation: " + 
            controller.listerActifs().size());

        // ---------------------------------------------
        // 12. TEST MEILLEURS FOURNISSEURS
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Meilleurs Fournisseurs (par évaluation) ---");
        List<Fournisseur> tousOrdonnés = controller.listerTout();
        tousOrdonnés.sort((a, b) -> Integer.compare(b.getEvaluation(), a.getEvaluation()));
        System.out.println("Top 3 fournisseurs:");
        tousOrdonnés.stream().limit(3).forEach(f -> 
            System.out.println("  " + f.getNomEntreprise() + " - Note: " + f.getEvaluation()));

        // ---------------------------------------------
        // 13. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Suppression ---");
        System.out.println("Suppression f3: " + controller.supprimer(f3.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 14. TEST RECHERCHES INEXISTANTES
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Recherches Inexistantes ---");
        System.out.println("Fournisseur ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Fournisseur code XXX: " + 
            (controller.rechercherParCode("XXX") == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Fournisseurs 'Inexistant': " + 
            controller.rechercherParNom("Inexistant").size() + " résultat(s)");

        // ---------------------------------------------
        // 15. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Validation ---");
        Fournisseur invalide = new Fournisseur(0, "", "email-invalide");
        System.out.println("Fournisseur invalide valide? " + invalide.valider());
        System.out.println("Fournisseur f1 valide? " + f1.valider());

        // ---------------------------------------------
        // 16. TEST STATISTIQUES
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Statistiques ---");
        System.out.println("Total fournisseurs: " + controller.compter());
        System.out.println("Fournisseurs actifs: " + controller.listerActifs().size());
        double evaluationMoyenne = controller.listerTout().stream()
            .mapToInt(Fournisseur::getEvaluation)
            .average()
            .orElse(0.0);
        System.out.println("Évaluation moyenne: " + String.format("%.2f", evaluationMoyenne));

        System.out.println("\n===== FIN DES TESTS FOURNISSEUR =====");
    }
}
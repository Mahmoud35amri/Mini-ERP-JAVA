package com.minierp.test;

import com.minierp.controller.PaiementController;
import com.minierp.controller.FactureController;
import com.minierp.model.Paiement;
import com.minierp.model.Facture;
import com.minierp.model.StatutsEnums.ModePaiement;
import java.util.List;

public class TestPaiementController {

    public static void main(String[] args) {
        PaiementController controller = PaiementController.getInstance();
        FactureController factureCtrl = FactureController.getInstance();
        
        System.out.println("===== TEST PAIEMENT CONTROLLER =====\n");

        // Récupérer des factures pour les tests
        Facture f1 = factureCtrl.rechercherParId(1);
        Facture f2 = factureCtrl.rechercherParId(2);

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        Paiement p1 = new Paiement( f1,1200.0, ModePaiement.CARTE_BANCAIRE);
        Paiement p2 = new Paiement( f1,800.0, ModePaiement.VIREMENT );
        Paiement p3 = new Paiement( f2,2400.0, ModePaiement.ESPECES);
        
        System.out.println("Création p1: " + controller.creer(p1));
        System.out.println("Création p2: " + controller.creer(p2));
        System.out.println("Création p3: " + controller.creer(p3));
        System.out.println("Total paiements: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Recherche par ID ---");
        Paiement paiementTrouve = controller.rechercherParId(1);
        System.out.println("Paiement ID=1: " + paiementTrouve);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR REFERENCE
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par Référence ---");
        Paiement paiementParRef = controller.rechercherParReference("PAY002");
        System.out.println("Paiement PAY002: " + paiementParRef);

        // ---------------------------------------------
        // 4. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Modification ---");
        if (p1 != null) {
            p1.setMontant(1250.0);
            System.out.println("Modification p1: " + controller.modifier(p1));
            System.out.println("Après modification: " + controller.rechercherParId(p1.getId()));
        }

        // ---------------------------------------------
        // 5. TEST LISTER PAR FACTURE
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Lister par Facture ---");
        if (f1 != null) {
            List<Paiement> paiementsF1 = controller.listerParFacture(f1.getId());
            System.out.println("Paiements de la facture " + f1.getNumeroFacture() + 
                " (" + paiementsF1.size() + "):");
            double totalPaye = 0.0;
            for (Paiement p : paiementsF1) {
                System.out.println("  " + p.getReference() + " - " + p.getMontant() + 
                    " DT - " + p.getModePaiement());
                totalPaye += p.getMontant();
            }
            System.out.println("Total payé: " + totalPaye + " DT sur " + 
                f1.getMontantTTC() + " DT");
        }

        // ---------------------------------------------
        // 6. TEST LISTER PAR MODE
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Lister par Mode de Paiement ---");
        List<Paiement> parCarte = controller.listerParMode(ModePaiement.CARTE_BANCAIRE);
        System.out.println("Paiements par CARTE_BANCAIRE (" + parCarte.size() + "):");
        parCarte.forEach(p -> System.out.println("  " + p.getReference() + " - " + 
            p.getMontant() + " DT"));
        
        List<Paiement> parVirement = controller.listerParMode(ModePaiement.VIREMENT);
        System.out.println("Paiements par VIREMENT (" + parVirement.size() + "):");
        parVirement.forEach(p -> System.out.println("  " + p.getReference() + " - " + 
            p.getMontant() + " DT"));
        
        List<Paiement> parEspeces = controller.listerParMode(ModePaiement.ESPECES);
        System.out.println("Paiements par ESPECES (" + parEspeces.size() + "):");
        parEspeces.forEach(p -> System.out.println("  " + p.getReference() + " - " + 
            p.getMontant() + " DT"));

        // ---------------------------------------------
        // 7. TEST ENCAISSER
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Encaisser Paiement ---");
        System.out.println("État avant encaissement p1: " + 
            controller.rechercherParId(p1.getId()).getStatut());
        System.out.println("Encaisser p1: " + controller.encaisser(p1.getId()));
        System.out.println("État après encaissement: " + 
            controller.rechercherParId(p1.getId()).getStatut());

        // ---------------------------------------------
        // 8. TEST REJETER
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Rejeter Paiement ---");
        System.out.println("État avant rejet p2: " + 
            controller.rechercherParId(p2.getId()).getStatut());
        System.out.println("Rejeter p2: " + 
            controller.rejeter(p2.getId(), "Provision insuffisante"));
        Paiement p2Updated = controller.rechercherParId(p2.getId());
        System.out.println("État après rejet: " + p2Updated.getStatut());
        System.out.println("Motif rejet: " + p2Updated.getObservations());

        // ---------------------------------------------
        // 9. TEST PAIEMENTS MULTIPLES POUR UNE FACTURE
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Paiements Multiples ---");
        if (f1 != null) {
            Paiement p4 = new Paiement(f1, 500.0, ModePaiement.CHEQUE);
            controller.creer(p4);
            
            List<Paiement> tousF1 = controller.listerParFacture(f1.getId());
            System.out.println("Tous les paiements de " + f1.getNumeroFacture() + " (" + 
                tousF1.size() + "):");
            double totalEncaisse = 0.0;
            for (Paiement p : tousF1) {
                System.out.println("  " + p.getReference() + " - " + p.getMontant() + 
                    " DT - " + p.getStatut());
                if (p.getStatut() != null && p.getStatut().contains("ENCAISSE")) {
                    totalEncaisse += p.getMontant();
                }
            }
            System.out.println("Total encaissé: " + totalEncaisse + " DT");
            System.out.println("Reste à payer: " + (f1.getMontantTTC() - totalEncaisse) + " DT");
        }

        // ---------------------------------------------
        // 10. TEST STATISTIQUES PAR MODE
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Statistiques par Mode ---");
        System.out.println("Répartition des paiements par mode:");
        for (ModePaiement mode : ModePaiement.values()) {
            List<Paiement> parMode = controller.listerParMode(mode);
            if (!parMode.isEmpty()) {
                double total = parMode.stream()
                    .mapToDouble(Paiement::getMontant)
                    .sum();
                System.out.println("  " + mode + ": " + parMode.size() + 
                    " paiement(s) - Total: " + String.format("%.2f", total) + " DT");
            }
        }

        // ---------------------------------------------
        // 11. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Suppression ---");
        System.out.println("Suppression p2: " + controller.supprimer(p2.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 12. TEST RECHERCHES INEXISTANTES
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Recherches Inexistantes ---");
        System.out.println("Paiement ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Référence XXX: " + 
            (controller.rechercherParReference("XXX") == null ? "Non trouvée" : "Trouvée"));

        // ---------------------------------------------
        // 13. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Validation ---");
        Paiement invalide = new Paiement( null, -100.0, null);
        System.out.println("Paiement invalide valide? " + invalide.valider());
        System.out.println("Paiement p1 valide? " + p1.valider());

        // ---------------------------------------------
        // 14. TEST CREATION CHEQUE
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Paiement par Chèque ---");
        Paiement cheque = new Paiement( f2, 1500.0, ModePaiement.CHEQUE);
        cheque.setNumeroCheque("1234567");
        cheque.setBanque("Banque Nationale");
        controller.creer(cheque);
        System.out.println("Paiement chèque créé: " + cheque);

        // ---------------------------------------------
        // 15. TEST STATISTIQUES GLOBALES
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Statistiques Globales ---");
        double montantTotal = 0.0;
        int nbEncaisses = 0;
        int nbEnAttente = 0;
        int nbRejetes = 0;
        
        for (int i = 1; i <= controller.compter(); i++) {
            Paiement p = controller.rechercherParId(i);
            if (p != null) {
                montantTotal += p.getMontant();
                String statut = p.getStatut() != null ? p.getStatut() : "";
                if (statut.contains("ENCAISSE")) nbEncaisses++;
                else if (statut.contains("ATTENTE")) nbEnAttente++;
                else if (statut.contains("REJETE")) nbRejetes++;
            }
        }
        
        System.out.println("Total paiements: " + controller.compter());
        System.out.println("Montant total: " + String.format("%.2f", montantTotal) + " DT");
        System.out.println("Encaissés: " + nbEncaisses);
        System.out.println("En attente: " + nbEnAttente);
        System.out.println("Rejetés: " + nbRejetes);

        System.out.println("\n===== FIN DES TESTS PAIEMENT =====");
    }
}
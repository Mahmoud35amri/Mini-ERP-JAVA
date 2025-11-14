package com.minierp.test;

import com.minierp.controller.FactureController;
import com.minierp.controller.CommandeController;
import com.minierp.controller.ClientController;
import com.minierp.controller.UtilisateurController;
import com.minierp.model.Facture;
import com.minierp.model.Commande;
import com.minierp.model.Client;
import com.minierp.model.Utilisateur;
import com.minierp.model.StatutsEnums.StatutFacture;
import com.minierp.model.StatutsEnums.TypeCommande;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class TestFactureController {

    public static void main(String[] args) {
        FactureController controller = FactureController.getInstance();
        CommandeController cmdCtrl = CommandeController.getInstance();
        ClientController clientCtrl = ClientController.getInstance();
        
        System.out.println("===== TEST FACTURE CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION (adapté aux constructeurs demandés)
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");

        // assurer l'existence des clients utilisés
        Client cl1 = clientCtrl.rechercherParId(1);
        if (cl1 == null) {
            cl1 = new Client("Dupont", "Jean", "jean.dupont@example.com");
            clientCtrl.creer(cl1);
            cl1 = clientCtrl.rechercherParCode(cl1.getCode());
        }

        Client cl2 = clientCtrl.rechercherParId(2);
        if (cl2 == null) {
            cl2 = new Client("Durand", "Alice", "alice.durand@example.com");
            clientCtrl.creer(cl2);
            cl2 = clientCtrl.rechercherParCode(cl2.getCode());
        }

        // assurer l'existence d'une commande (utilisateur minimal créé si nécessaire)
        Commande commande1 = cmdCtrl.rechercherParId(1);
        if (commande1 == null) {
            UtilisateurController userCtrl = UtilisateurController.getInstance();
            Utilisateur u = userCtrl.rechercherParId(1);
            if (u == null) {
                u = new Utilisateur();
                u.setNom("system");
                u.setMotDePasse("system");
                userCtrl.creer(u);
                u = userCtrl.listerTous().stream().findFirst().orElse(u);
            }
            commande1 = new Commande(TypeCommande.VENTE, cl1, u);
            cmdCtrl.creer(commande1);
            // tenter de récupérer la commande persistée (si creer() n'initialise pas l'id)
            commande1 = cmdCtrl.rechercherParNumero(commande1.getNumeroCommande());
            if (commande1 == null) {
                // fallback : récupérer la première commande
                commande1 = cmdCtrl.listerTout().stream().findFirst().orElse(commande1);
            }
        }

        // créer des factures en utilisant le constructeur Facture(Commande, Client)
        Facture f1 = new Facture(commande1, cl1);
        f1.setMontantHT(1000.0);
        f1.setMontantTTC(1200.0);
        
        // créer une seconde commande/facture si nécessaire
        Commande commande2 = cmdCtrl.rechercherParId(2);
        if (commande2 == null) {
            Utilisateur u2 = UtilisateurController.getInstance().listerTous().stream().findFirst().orElse(null);
            if (u2 == null) {
                u2 = new Utilisateur();
                u2.setNom("user2");
                u2.setMotDePasse("user2");
                UtilisateurController.getInstance().creer(u2);
                u2 = UtilisateurController.getInstance().listerTous().stream().findFirst().orElse(u2);
            }
            commande2 = new Commande(TypeCommande.VENTE, cl2, u2);
            cmdCtrl.creer(commande2);
            commande2 = cmdCtrl.rechercherParNumero(commande2.getNumeroCommande());
            if (commande2 == null) commande2 = cmdCtrl.listerTout().stream().findFirst().orElse(commande2);
        }

        Facture f2 = new Facture(commande2, cl2);
        f2.setMontantHT(2000.0);
        f2.setMontantTTC(2400.0);

        // troisième facture (ex. même client/commande différente ou réutilisation)
        Commande commande3 = cmdCtrl.rechercherParId(3);
        if (commande3 == null) {
            // réutiliser commande1 si pas d'autre disponible
            commande3 = commande1;
        }

        Facture f3 = new Facture(commande3, cl1);
        f3.setMontantHT(500.0);
        f3.setMontantTTC(600.0);
        
        System.out.println("Création f1: " + controller.creer(f1));
        System.out.println("Création f2: " + controller.creer(f2));
        System.out.println("Création f3: " + controller.creer(f3));
        System.out.println("Total factures: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Facture> toutesFactures = controller.listerTout();
        toutesFactures.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Facture factureTrouvee = controller.rechercherParId(1);
        System.out.println("Facture ID=1: " + factureTrouvee);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR NUMERO
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Numéro ---");
        Facture factureParNum = controller.rechercherParNumero("FACT002");
        System.out.println("Facture numéro FACT002: " + factureParNum);

        // ---------------------------------------------
        // 5. TEST GENERER DEPUIS COMMANDE
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Générer depuis Commande ---");
        Commande commande = cmdCtrl.rechercherParId(1);
        if (commande != null) {
            Facture factureGeneree = controller.genererDepuisCommande(commande);
            System.out.println("Facture générée: " + factureGeneree);
            if (factureGeneree != null) {
                controller.creer(factureGeneree);
                System.out.println("Facture créée avec succès");
            }
        }

        // ---------------------------------------------
        // 6. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Modification ---");
        if (f1 != null) {
            f1.setStatut(StatutFacture.PAYEE);
            f1.setDateCreation(new Date());
            System.out.println("Modification f1: " + controller.modifier(f1));
            System.out.println("Après modification: " + controller.rechercherParId(f1.getId()));
        }

        // ---------------------------------------------
        // 7. TEST LISTER PAR CLIENT
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister par Client ---");
        List<Facture> facturesClient1 = controller.listerParClient(1);
        System.out.println("Factures du client ID=1 (" + facturesClient1.size() + "):");
        facturesClient1.forEach(f -> System.out.println("  " + f.getNumeroFacture() + 
            " - " + f.getMontantTTC() + " DT - " + f.getStatut()));

        // ---------------------------------------------
        // 8. TEST LISTER PAR STATUT
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Lister par Statut ---");
        List<Facture> emises = controller.listerParStatut(StatutFacture.EMISE);
        System.out.println("Factures EMISES: " + emises.size());
        
        List<Facture> payees = controller.listerParStatut(StatutFacture.PAYEE);
        System.out.println("Factures PAYEES: " + payees.size());
        payees.forEach(f -> System.out.println("  " + f.getNumeroFacture() + " - " + 
                                                f.getMontantTTC() + " DT"));

        List<Facture> enAttente = controller.listerParStatut(StatutFacture.EN_RETARD);
        System.out.println("Factures EN_RETARD: " + enAttente.size());

        // ---------------------------------------------
        // 9. TEST RECHERCHE PAR COMMANDE
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Recherche par Commande ---");
        if (commande != null) {
            Facture factureCommande = controller.rechercherParCommande(commande.getId());
            System.out.println("Facture de la commande " + commande.getNumeroCommande() + ": " + 
                              (factureCommande != null ? factureCommande.getNumeroFacture() : "Non trouvée"));
        }

        // ---------------------------------------------
        // 10. TEST CALCUL CHIFFRE D'AFFAIRES
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Calcul Chiffre d'Affaires ---");
        Calendar cal = Calendar.getInstance();
        Date fin = cal.getTime();
        cal.add(Calendar.MONTH, -1);
        Date debut = cal.getTime();
        
        double ca = controller.calculerChiffreAffaires(debut, fin);
        System.out.println("CA du dernier mois: " + String.format("%.2f", ca) + " DT");
        
        // CA total
        double caTotal = controller.calculerChiffreAffaires(new Date(0), new Date());
        System.out.println("CA total: " + String.format("%.2f", caTotal) + " DT");

        // ---------------------------------------------
        // 11. TEST CHANGEMENT STATUT
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Changement de Statut ---");
        f3.setStatut(StatutFacture.PAYEE);
        controller.modifier(f3);
        System.out.println("Nouveau statut f3: " + 
            controller.rechercherParId(f3.getId()).getStatut());

        // ---------------------------------------------
        // 12. TEST STATISTIQUES PAR STATUT
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Statistiques par Statut ---");
        System.out.println("Total factures: " + controller.compter());
        System.out.println("  EN_RETARD: " + 
            controller.listerParStatut(StatutFacture.EN_RETARD).size());
        System.out.println("  EMISE: " + 
            controller.listerParStatut(StatutFacture.EMISE).size());
        System.out.println("  VALIDEE: " + 
            controller.listerParStatut(StatutFacture.PAYEE).size());
        System.out.println("  PAYEE: " + 
            controller.listerParStatut(StatutFacture.PAYEE).size());
        System.out.println("  ANNULEE: " + 
            controller.listerParStatut(StatutFacture.ANNULEE).size());

        // ---------------------------------------------
        // 13. TEST FACTURES IMPAYEES
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Factures Impayées ---");
        List<Facture> impayees = controller.listerTout().stream()
            .filter(f -> f.getStatut() != StatutFacture.PAYEE && 
                        f.getStatut() != StatutFacture.ANNULEE)
            .toList();
        System.out.println("Factures impayées (" + impayees.size() + "):");
        double totalImpaye = 0.0;
        for (Facture f : impayees) {
            System.out.println("  " + f.getNumeroFacture() + " - " + 
                              f.getMontantTTC() + " DT - " + f.getStatut());
            totalImpaye += f.getMontantTTC();
        }
        System.out.println("Montant total impayé: " + String.format("%.2f", totalImpaye) + " DT");

        // ---------------------------------------------
        // 14. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Suppression ---");
        System.out.println("Suppression f3: " + controller.supprimer(f3.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 15. TEST RECHERCHES INEXISTANTES
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Recherches Inexistantes ---");
        System.out.println("Facture ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvée" : "Trouvée"));
        System.out.println("Facture numéro XXX: " + 
            (controller.rechercherParNumero("XXX") == null ? "Non trouvée" : "Trouvée"));
        System.out.println("Facture commande 999: " + 
            (controller.rechercherParCommande(999) == null ? "Non trouvée" : "Trouvée"));

        // ---------------------------------------------
        // 16. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Validation ---");
        Facture invalide = new Facture(null, null);
        System.out.println("Facture invalide valide? " + invalide.valider());
        System.out.println("Facture f1 valide? " + f1.valider());

        // ---------------------------------------------
        // 17. TEST DELAIS DE PAIEMENT
        // ---------------------------------------------
        System.out.println("\n--- 17. Test Délais de Paiement ---");
        for (Facture f : controller.listerTout()) {
            if (f.getDateLimite() != null) {
                long diff = f.getDateLimite().getTime() - f.getDateCreation().getTime();
                long jours = diff / (1000 * 60 * 60 * 24);
                System.out.println(f.getNumeroFacture() + " - Délai: " + jours + " jours");
            }
        }

        System.out.println("\n===== FIN DES TESTS FACTURE =====");
    }
}
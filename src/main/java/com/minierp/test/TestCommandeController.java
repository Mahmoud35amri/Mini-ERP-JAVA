package com.minierp.test;

import com.minierp.controller.CommandeController;
import com.minierp.controller.ClientController;
import com.minierp.controller.UtilisateurController;
import com.minierp.model.Commande;
import com.minierp.model.Client;
import com.minierp.model.Utilisateur;
import com.minierp.model.StatutsEnums.StatutCommande;
import com.minierp.model.StatutsEnums.TypeCommande;
import java.util.List;

public class TestCommandeController {

    public static void main(String[] args) {
        CommandeController controller = CommandeController.getInstance();
        ClientController clientCtrl = ClientController.getInstance();
        UtilisateurController userCtrl = UtilisateurController.getInstance();
        
        System.out.println("===== TEST COMMANDE CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION (adapté aux constructeurs demandés)
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        // récupérer/assurer l'existence d'un client et d'un utilisateur de test
        Client c1 = clientCtrl.rechercherParId(1);
        if (c1 == null) {
            c1 = new Client("Dupont", "Jean", "jean.dupont@example.com");
            clientCtrl.creer(c1);
            // tenter de récupérer l'objet persisté (si creer n'initialise pas l'id)
            c1 = clientCtrl.rechercherParCode(c1.getCode());
        }

        Utilisateur u1 = userCtrl.rechercherParId(1);
        if (u1 == null) {
            u1 = new Utilisateur();
            u1.setNom("admin");
            u1.setMotDePasse("admin");
            userCtrl.creer(u1);
            u1 = userCtrl.listerTous().stream().findFirst().orElse(u1);
        }

        // création des commandes avec le constructeur : Commande(TypeCommande, Client, Utilisateur)
        Commande cmd1 = new Commande(TypeCommande.VENTE, c1, u1);
        Commande cmd2 = new Commande(TypeCommande.DEVIS, c1, u1);
        Commande cmd3 = new Commande(TypeCommande.ACHAT, c1, u1);
        
        System.out.println("Création cmd1: " + controller.creer(cmd1));
        System.out.println("Création cmd2: " + controller.creer(cmd2));
        System.out.println("Création cmd3: " + controller.creer(cmd3));

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Commande> toutesCommandes = controller.listerTout();
        System.out.println("Total commandes: " + toutesCommandes.size());
        toutesCommandes.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Commande cmdTrouvee = controller.rechercherParId(2);
        System.out.println("Commande ID=2: " + cmdTrouvee);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR NUMERO
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Numéro ---");
        Commande cmdParNum = controller.rechercherParNumero("CMD-202511");
        System.out.println("Commande numéro CMD002: " + cmdParNum);

        // ---------------------------------------------
        // 5. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Modification ---");
        if (cmd1 != null) {
            cmd1.setStatut(StatutCommande.CONFIRMEE);
            cmd1.setMontantTTC(1500.0);
            System.out.println("Modification cmd1: " + controller.modifier(cmd1));
            System.out.println("Après modification: " + controller.rechercherParId(cmd1.getId()));
        }

        // ---------------------------------------------
        // 6. TEST LISTER PAR CLIENT
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Lister par Client ---");
        List<Commande> commandesClient1 = controller.listerParClient(2);
        System.out.println("Commandes du client ID=1 (" + commandesClient1.size() + "):");
        commandesClient1.forEach(c -> System.out.println("  " + c.getNumeroCommande() + " - " + c.getStatut()));

        // ---------------------------------------------
        // 7. TEST LISTER PAR FOURNISSEUR
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister par Fournisseur ---");
        List<Commande> commandesFournisseur = controller.listerParFournisseur(2);
        System.out.println("Commandes du fournisseur ID=1: " + commandesFournisseur.size());

        // ---------------------------------------------
        // 8. TEST LISTER PAR STATUT
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Lister par Statut ---");
        List<Commande> enAttente = controller.listerParStatut(StatutCommande.EN_ATTENTE);
        System.out.println("Commandes EN_ATTENTE: " + enAttente.size());
        
        List<Commande> validees = controller.listerParStatut(StatutCommande.CONFIRMEE);
        System.out.println("Commandes CONFIRMEES: " + validees.size());
        validees.forEach(c -> System.out.println("  " + c.getNumeroCommande()));

        // ---------------------------------------------
        // 9. TEST CHANGEMENT STATUT
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Changement de Statut ---");
        cmd3.setStatut(StatutCommande.LIVREE);
        System.out.println("Changement statut cmd3: " + controller.modifier(cmd3));
        System.out.println("Nouveau statut: " + controller.rechercherParId(cmd3.getId()).getStatut());

        // ---------------------------------------------
        // 10. TEST STATISTIQUES
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Statistiques ---");
        System.out.println("Total commandes: " + controller.listerTout().size());
        System.out.println("Commandes EN_ATTENTE: " + 
            controller.listerParStatut(StatutCommande.EN_ATTENTE).size());
        System.out.println("Commandes CONFIRMEE: " + 
            controller.listerParStatut(StatutCommande.CONFIRMEE).size());
        System.out.println("Commandes EN_PREPARATION: " + 
            controller.listerParStatut(StatutCommande.EN_PREPARATION).size());
        System.out.println("Commandes LIVREE: " + 
            controller.listerParStatut(StatutCommande.LIVREE).size());

        // ---------------------------------------------
        // 11. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Suppression ---");
        System.out.println("Suppression cmd3: " + controller.supprimer(cmd3.getId()));
        System.out.println("Total après suppression: " + controller.listerTout().size());

        // ---------------------------------------------
        // 12. TEST RECHERCHE INEXISTANTE
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Recherches Inexistantes ---");
        System.out.println("Commande ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvée" : "Trouvée"));
        System.out.println("Commande numéro XXX: " + 
            (controller.rechercherParNumero("XXX") == null ? "Non trouvée" : "Trouvée"));

        // ---------------------------------------------
        // 13. TEST COMMANDES AVEC MONTANTS
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Commandes avec Montants ---");
        cmd1.setMontantHT(1250.0);
        cmd1.setMontantTTC(1500.0);
        cmd2.setMontantHT(2083.33);
        cmd2.setMontantTTC(2500.0);
        controller.modifier(cmd1);
        controller.modifier(cmd2);
        
        double totalCA = controller.listerTout().stream()
            .mapToDouble(Commande::getMontantTTC)
            .sum();
        System.out.println("CA total des commandes: " + totalCA + " DT");

        // ---------------------------------------------
        // 14. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Validation ---");
        Commande invalide = new Commande( null, null, null);
        System.out.println("Commande invalide valide? " + invalide.valider());
        System.out.println("Commande cmd1 valide? " + cmd1.valider());

        System.out.println("\n===== FIN DES TESTS COMMANDE =====");
    }
}
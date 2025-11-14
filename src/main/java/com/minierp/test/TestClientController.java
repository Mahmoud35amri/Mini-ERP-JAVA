package com.minierp.test;

import com.minierp.controller.ClientController;
import com.minierp.model.Client;
import com.minierp.model.Client.TypeClient;
import java.util.List;

public class TestClientController {

    public static void main(String[] args) {
        ClientController controller = ClientController.getInstance();
        
        System.out.println("===== TEST CLIENT CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        // Utilisation des constructeurs définis dans Client.java
        Client c1 = new Client("Dupont", "Jean", "jean.dupont@email.com");
        Client c2 = new Client(
            TypeClient.ENTREPRISE,    // type
            "M.",                    // civilite
            "Durand",                // nom
            "Alice",                 // prenom
            "TechSolutions SARL",    // nomEntreprise
            "10 Rue Exemple",        // adresse
            "Tunis",                 // ville
            "1000",                  // codePostal
            "Tunisie",               // pays
            "0623456789",            // telephone
            "contact@techsol.com",   // email
            50000.0                  // plafondCredit
        );
        Client c3 = new Client("Martin", "Sophie", "sophie.martin@email.com");
        
        System.out.println("Création c1: " + controller.creer(c1));
        System.out.println("Création c2: " + controller.creer(c2));
        System.out.println("Création c3: " + controller.creer(c3));
        System.out.println("Total clients: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUT
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tout ---");
        List<Client> tousClients = controller.listerTout();
        tousClients.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Client clientTrouve = controller.rechercherParId(1);
        System.out.println("Client ID=1: " + clientTrouve);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR CODE
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Code ---");
        Client clientParCode = controller.rechercherParCode("CLI002");
        System.out.println("Client code CLI002: " + clientParCode);

        // ---------------------------------------------
        // 5. TEST RECHERCHE PAR NOM
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Recherche par Nom ---");
        List<Client> clientsParNom = controller.rechercherParNom("Martin");
        System.out.println("Recherche 'Martin':");
        clientsParNom.forEach(System.out::println);

        // ---------------------------------------------
        // 6. TEST RECHERCHE PAR EMAIL
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Recherche par Email ---");
        Client clientParEmail = controller.rechercherParEmail("contact@techsol.com");
        System.out.println("Client email contact@techsol.com: " + clientParEmail);

        // ---------------------------------------------
        // 7. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Modification ---");
        if (c1 != null) {
            c1.setTelephone("0698765432");
            c1.setCategorie(Client.CategorieClient.A);
            System.out.println("Modification c1: " + controller.modifier(c1));
            System.out.println("Après modification: " + controller.rechercherParId(c1.getId()));
        }

        // ---------------------------------------------
        // 8. TEST CLIENTS ACTIFS
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Lister Actifs ---");
        List<Client> actifs = controller.listerActifs();
        System.out.println("Clients actifs (" + actifs.size() + "):");
        actifs.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom()));

        // ---------------------------------------------
        // 9. TEST LISTER PAR TYPE
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Lister par Type ---");
        List<Client> particuliers = controller.listerParType(TypeClient.PARTICULIER);
        System.out.println("Clients particuliers (" + particuliers.size() + "):");
        particuliers.forEach(c -> System.out.println(c.getNom()));

        List<Client> entreprises = controller.listerParType(TypeClient.ENTREPRISE);
        System.out.println("Clients entreprises (" + entreprises.size() + "):");
        entreprises.forEach(c -> System.out.println(c.getNom()));

        // ---------------------------------------------
        // 10. TEST LISTER PAR CATEGORIE
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Lister par Catégorie ---");
        List<Client> vip = controller.listerParCategorie("VIP");
        System.out.println("Clients VIP (" + vip.size() + "):");
        vip.forEach(c -> System.out.println(c.getNom()));

        // ---------------------------------------------
        // 11. TEST MEILLEURS CLIENTS
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Meilleurs Clients ---");
        List<Client> meilleurs = controller.listerMeilleurs(3);
        System.out.println("Top 3 meilleurs clients:");
        meilleurs.forEach(c -> System.out.println(c.getNom() + " - CA: " + 
            controller.calculerChiffreAffaires(c.getId())));

        // ---------------------------------------------
        // 12. TEST CALCUL CHIFFRE D'AFFAIRES
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Calcul Chiffre d'Affaires ---");
        double ca1 = controller.calculerChiffreAffaires(c1.getId());
        System.out.println("CA de " + c1.getNom() + ": " + ca1 + " DT");

        // ---------------------------------------------
        // 13. TEST DESACTIVATION
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Désactivation ---");
        c3.setActif(false);
        controller.modifier(c3);
        System.out.println("Clients actifs après désactivation: " + 
            controller.listerActifs().size());

        // ---------------------------------------------
        // 14. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Suppression ---");
        System.out.println("Suppression c3: " + controller.supprimer(c3.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 15. TEST RECHERCHE INEXISTANTE
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Recherches Inexistantes ---");
        System.out.println("Client ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Client code XXX: " + 
            (controller.rechercherParCode("XXX") == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Client email inexistant: " + 
            (controller.rechercherParEmail("inexistant@email.com") == null ? "Non trouvé" : "Trouvé"));

        // ---------------------------------------------
        // 16. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Validation ---");
        Client invalide = new Client("", "", "email-invalide");
        System.out.println("Client invalide valide? " + invalide.valider());
        System.out.println("Client c1 valide? " + c1.valider());

        System.out.println("\n===== FIN DES TESTS CLIENT =====");
    }
}
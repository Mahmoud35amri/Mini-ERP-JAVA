package com.minierp.test;

import com.minierp.controller.UtilisateurController;
import com.minierp.model.Utilisateur;
import com.minierp.model.Utilisateur.Role;
import java.util.List;

public class TestUtilisateurController {

    public static void main(String[] args) {
        UtilisateurController controller = UtilisateurController.getInstance();
        
        System.out.println("===== TEST UTILISATEUR CONTROLLER =====\n");

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        System.out.println("--- 1. Test Création ---");
        Utilisateur u1 = new Utilisateur(1,"Admin", "Système", "admin@minierp.com", "admin123", Role.ADMIN);
        Utilisateur u2 = new Utilisateur(2,"Dupont", "Jean", "jean.dupont@minierp.com","pass123", Role.GERANT);
        Utilisateur u3 = new Utilisateur(3,"Martin", "Sophie", "sophie.martin@minierp.com","pass456", Role.EMPLOYE);
        
        System.out.println("Création u1: " + controller.creer(u1));
        System.out.println("Création u2: " + controller.creer(u2));
        System.out.println("Création u3: " + controller.creer(u3));
        System.out.println("Total utilisateurs: " + controller.compter());

        // ---------------------------------------------
        // 2. TEST LISTER TOUS
        // ---------------------------------------------
        System.out.println("\n--- 2. Test Lister Tous ---");
        List<Utilisateur> tousUtilisateurs = controller.listerTous();
        tousUtilisateurs.forEach(System.out::println);

        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- 3. Test Recherche par ID ---");
        Utilisateur utilisateurTrouve = controller.rechercherParId(1);
        System.out.println("Utilisateur ID=1: " + utilisateurTrouve);

        // ---------------------------------------------
        // 4. TEST RECHERCHE PAR EMAIL
        // ---------------------------------------------
        System.out.println("\n--- 4. Test Recherche par Email ---");
        Utilisateur utilisateurParEmail = controller.rechercherParEmail("jean.dupont@minierp.com");
        System.out.println("Utilisateur email jean.dupont@minierp.com: " + utilisateurParEmail);

        // ---------------------------------------------
        // 5. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 5. Test Modification ---");
        if (u2 != null) {
            u2.setPrenom("Jean-Pierre");
            u2.setRole(Role.ADMIN);
            System.out.println("Modification u2: " + controller.modifier(u2));
            System.out.println("Après modification: " + controller.rechercherParId(u2.getId()));
        }

        // ---------------------------------------------
        // 6. TEST LISTER PAR ROLE
        // ---------------------------------------------
        System.out.println("\n--- 6. Test Lister par Rôle ---");
        List<Utilisateur> admins = controller.listerParRole(Role.ADMIN);
        System.out.println("Administrateurs (" + admins.size() + "):");
        admins.forEach(u -> System.out.println("  " + u.getNom() + " " + u.getPrenom()));
        
        List<Utilisateur> gestionnaires = controller.listerParRole(Role.GERANT);
        System.out.println("gerants (" + gestionnaires.size() + "):");
        gestionnaires.forEach(u -> System.out.println("  " + u.getNom() + " " + u.getPrenom()));
        
        List<Utilisateur> utilisateurs = controller.listerParRole(Role.EMPLOYE);
        System.out.println("employes (" + utilisateurs.size() + "):");
        utilisateurs.forEach(u -> System.out.println("  " + u.getNom() + " " + u.getPrenom()));

        // ---------------------------------------------
        // 7. TEST LISTER ACTIFS
        // ---------------------------------------------
        System.out.println("\n--- 7. Test Lister Actifs ---");
        List<Utilisateur> actifs = controller.listerActifs();
        System.out.println("Utilisateurs actifs (" + actifs.size() + "):");
        actifs.forEach(u -> System.out.println("  " + u.getEmail()));

        // ---------------------------------------------
        // 8. TEST AUTHENTIFICATION
        // ---------------------------------------------
        System.out.println("\n--- 8. Test Authentification ---");
        Utilisateur auth1 = controller.authentifier("admin@minierp.com", "admin123");
        System.out.println("Connexion admin (correct): " + 
            (auth1 != null ? "Succès - " + auth1.getNom() : "Échec"));
        
        Utilisateur auth2 = controller.authentifier("admin@minierp.com", "mauvais_mdp");
        System.out.println("Connexion admin (mauvais mdp): " + 
            (auth2 != null ? "Succès" : "Échec"));
        
        Utilisateur auth3 = controller.authentifier("inexistant@email.com", "pass");
        System.out.println("Connexion email inexistant: " + 
            (auth3 != null ? "Succès" : "Échec"));

        // ---------------------------------------------
        // 9. TEST UTILISATEUR CONNECTE
        // ---------------------------------------------
        System.out.println("\n--- 9. Test Utilisateur Connecté ---");
        if (auth1 != null) {
            Utilisateur connecte = controller.getUtilisateurConnecte();
            System.out.println("Utilisateur actuellement connecté: " + 
                (connecte != null ? connecte.getEmail() : "Aucun"));
        }

        // ---------------------------------------------
        // 10. TEST CHANGER MOT DE PASSE
        // ---------------------------------------------
        System.out.println("\n--- 10. Test Changer Mot de Passe ---");
        System.out.println("Changement mdp u3: " + 
            controller.changerMotDePasse(u3.getId(), "nouveau_pass789"));
        
        // Vérifier avec nouvelle authentification
        controller.deconnecter();
        Utilisateur authNouveau = controller.authentifier("sophie.martin@minierp.com", 
                                                          "nouveau_pass789");
        System.out.println("Connexion avec nouveau mdp: " + 
            (authNouveau != null ? "Succès" : "Échec"));

        // ---------------------------------------------
        // 11. TEST REINITIALISER MOT DE PASSE
        // ---------------------------------------------
        System.out.println("\n--- 11. Test Réinitialiser Mot de Passe ---");
        String nouveauMdp = controller.reinitialiserMotDePasse(u2.getId());
        System.out.println("Mot de passe réinitialisé pour u2: " + nouveauMdp);
        
        controller.deconnecter();
        Utilisateur authReinit = controller.authentifier("jean.dupont@minierp.com", nouveauMdp);
        System.out.println("Connexion avec mdp réinitialisé: " + 
            (authReinit != null ? "Succès" : "Échec"));

        // ---------------------------------------------
        // 12. TEST VERROUILLAGE
        // ---------------------------------------------
        System.out.println("\n--- 12. Test Verrouillage ---");
        System.out.println("Verrouiller u3: " + controller.verouiller(u3.getId()));
        Utilisateur u3Updated = controller.rechercherParId(u3.getId());
        System.out.println("u3 actif? " + u3Updated.isActif());
        
        // Tenter de se connecter avec compte verrouillé
        controller.deconnecter();
        Utilisateur authVerrouille = controller.authentifier("sophie.martin@minierp.com", 
                                                             "nouveau_pass789");
        System.out.println("Connexion compte verrouillé: " + 
            (authVerrouille != null ? "Succès (ne devrait pas)" : "Échec (normal)"));

        // ---------------------------------------------
        // 13. TEST DEVERROUILLAGE
        // ---------------------------------------------
        System.out.println("\n--- 13. Test Déverrouillage ---");
        System.out.println("Déverrouiller u3: " + controller.deverouiller(u3.getId()));
        u3Updated = controller.rechercherParId(u3.getId());
        System.out.println("u3 verrouillé? " + u3Updated.isActif());
        
        Utilisateur authDeverrouille = controller.authentifier("sophie.martin@minierp.com", 
                                                               "nouveau_pass789");
        System.out.println("Connexion après déverrouillage: " + 
            (authDeverrouille != null ? "Succès" : "Échec"));

        // ---------------------------------------------
        // 14. TEST DECONNEXION
        // ---------------------------------------------
        System.out.println("\n--- 14. Test Déconnexion ---");
        controller.deconnecter();
        Utilisateur connecteApres = controller.getUtilisateurConnecte();
        System.out.println("Utilisateur connecté après déconnexion: " + 
            (connecteApres != null ? connecteApres.getEmail() : "Aucun"));

        // ---------------------------------------------
        // 15. TEST DESACTIVATION
        // ---------------------------------------------
        System.out.println("\n--- 15. Test Désactivation ---");
        u3.setActif(false);
        controller.modifier(u3);
        System.out.println("Utilisateurs actifs après désactivation: " + 
            controller.listerActifs().size());

        // ---------------------------------------------
        // 16. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- 16. Test Suppression ---");
        System.out.println("Suppression u3: " + controller.supprimer(u3.getId()));
        System.out.println("Total après suppression: " + controller.compter());

        // ---------------------------------------------
        // 17. TEST RECHERCHES INEXISTANTES
        // ---------------------------------------------
        System.out.println("\n--- 17. Test Recherches Inexistantes ---");
        System.out.println("Utilisateur ID=999: " + 
            (controller.rechercherParId(999) == null ? "Non trouvé" : "Trouvé"));
        System.out.println("Email inexistant: " + 
            (controller.rechercherParEmail("inexistant@email.com") == null ? 
             "Non trouvé" : "Trouvé"));

        // ---------------------------------------------
        // 18. TEST VALIDATION
        // ---------------------------------------------
        System.out.println("\n--- 18. Test Validation ---");
        Utilisateur invalide = new Utilisateur(0, "", "email-invalide", "", "", null);
        System.out.println("Utilisateur invalide valide? " + invalide.valider());
        System.out.println("Utilisateur u1 valide? " + u1.valider());

        // ---------------------------------------------
        // 19. TEST STATISTIQUES
        // ---------------------------------------------
        System.out.println("\n--- 19. Test Statistiques ---");
        System.out.println("Total utilisateurs: " + controller.compter());
        System.out.println("Utilisateurs actifs: " + controller.listerActifs().size());
        System.out.println("Administrateurs: " + controller.listerParRole(Role.ADMIN).size());
        System.out.println("Gerants: " + controller.listerParRole(Role.GERANT).size());
        System.out.println("Employes: " + controller.listerParRole(Role.EMPLOYE).size());

        System.out.println("\n===== FIN DES TESTS UTILISATEUR =====");
    }
}
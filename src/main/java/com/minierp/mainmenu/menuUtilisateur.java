package com.minierp.mainmenu;
import com.minierp.controller.UtilisateurController;
import com.minierp.model.Utilisateur;
import com.minierp.model.Utilisateur.Role;
import static com.minierp.util.InputUtils.lireInt;
import static com.minierp.util.InputUtils.lireString;

import java.util.List;
import java.util.Scanner;
public class menuUtilisateur {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        UtilisateurController controller = UtilisateurController.getInstance();

        System.out.println("===== MINI ERP - GESTION UTILISATEURS =====");

        int choix;

        do {
            afficherMenu();
            choix = lireInt("Choisissez une option : ");

            switch (choix) {

                case 1 -> creerUtilisateur(controller);
                case 2 -> listerTous(controller);
                case 3 -> rechercherParId(controller);
                case 4 -> rechercherParEmail(controller);
                case 5 -> modifierUtilisateur(controller);
                case 6 -> supprimerUtilisateur(controller);
                case 7 -> authentifier(controller);
                case 8 -> changerMotDePasse(controller);
                case 9 -> listerActifs(controller);
                case 10 -> listerParRole(controller);
                case 11 -> listerUtlisateursConnectes(controller);
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println(" Choix invalide.");

            }

            System.out.println("\n---------------------------------------------\n");

        } while (choix != 0);
        
        // NE PAS FERMER LE SCANNER pour permettre la continuation du menu général
        // scanner.close();
    }

    // ============================================================
    // MENU
    // ============================================================
    private static void afficherMenu() {
        System.out.println("""
                
                ---------- MENU UTILISATEUR ----------
                1. Créer un utilisateur
                2. Lister tous les utilisateurs
                3. Rechercher par ID
                4. Rechercher par email
                5. Modifier un utilisateur
                6. Supprimer un utilisateur
                7. Authentifier un utilisateur
                8. Changer mot de passe
                9. Lister utilisateurs actifs
                10. Lister par rôle
                11. Lister utilisateurs connectés
                0. Retour au menu principal
                """);
    }

    // ============================================================
    // UTILITAIRES SÉCURISÉS
    // ============================================================

    private static Role lireRole() {
        while (true) {
            System.out.print("Rôle (ADMIN / GERANT / EMPLOYE) : ");
            try {
                return Role.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(" Rôle invalide !");
            }
        }
    }

    // ============================================================
    // ACTIONS
    // ============================================================

    private static void creerUtilisateur(UtilisateurController controller) {

        int id = lireInt("ID : ");
        String nom = lireString("Nom : ");
        String prenom = lireString("Prénom : ");
        String email = lireString("Email : ");
        String mdp = lireString("Mot de passe : ");
        Role role = lireRole();

        Utilisateur u = new Utilisateur(id, nom, prenom, email, mdp, role);

        if (!u.valider()) {
            System.out.println(" Données invalides !");
            return;
        }

        boolean resultat = controller.creer(u);
        System.out.println(resultat ? " Utilisateur créé." : " Erreur : email ou ID déjà existant.");
    }

    private static void listerTous(UtilisateurController controller) {
        List<Utilisateur> liste = controller.listerTous();
        if (liste.isEmpty()) {
            System.out.println(" Aucun utilisateur trouvé.");
            return;
        }
        liste.forEach(System.out::println);
    }

    private static void rechercherParId(UtilisateurController controller) {
        int id = lireInt("ID de l'utilisateur : ");
        Utilisateur u = controller.rechercherParId(id);
        System.out.println(u == null ? " Non trouvé." : u);
    }

    private static void rechercherParEmail(UtilisateurController controller) {
        String email = lireString("Email : ");
        Utilisateur u = controller.rechercherParEmail(email);
        System.out.println(u == null ? " Non trouvé." : u);
    }

    private static void modifierUtilisateur(UtilisateurController controller) {
        int id = lireInt("ID de l'utilisateur à modifier : ");
        Utilisateur u = controller.rechercherParId(id);

        if (u == null) {
            System.out.println(" Utilisateur introuvable.");
            return;
        }

        String nom = lireString("Nouveau nom : ");
        String prenom = lireString("Nouveau prénom : ");
        Role role = lireRole();

        u.setNom(nom);
        u.setPrenom(prenom);
        u.setRole(role);

        boolean ok = controller.modifier(u);
        System.out.println(ok ? " Modifié." : " Échec.");
    }

    private static void supprimerUtilisateur(UtilisateurController controller) {
        int id = lireInt("ID à supprimer : ");
        boolean ok = controller.supprimer(id);
        System.out.println(ok ? " Supprimé." : " Impossible de supprimer.");
    }

    private static void authentifier(UtilisateurController controller) {
        String email = lireString("Email : ");
        String mdp = lireString("Mot de passe : ");

        Utilisateur u = controller.authentifier(email, mdp);
        System.out.println(u == null ? " Connexion échouée." : " Connecté : " + u.getNom());
    }

    private static void changerMotDePasse(UtilisateurController controller) {
        int id = lireInt("ID : ");
        String newPass = lireString("Nouveau mot de passe : ");

        boolean ok = controller.changerMotDePasse(id, newPass);
        System.out.println(ok ? " Mot de passe modifié." : " Échec.");
    }

    private static void listerActifs(UtilisateurController controller) {
        List<Utilisateur> actifs = controller.listerActifs();
        System.out.println("Actifs : " + actifs.size());
        actifs.forEach(u -> System.out.println(u.getEmail()));
    }

    private static void listerParRole(UtilisateurController controller) {
        Role role = lireRole();
        List<Utilisateur> liste = controller.listerParRole(role);
        System.out.println("Utilisateurs " + role + " : " + liste.size());
        liste.forEach(System.out::println);
    }
    private static void listerUtlisateursConnectes(UtilisateurController controller) {
        List<Utilisateur> connectes = controller.getUtilisateurConnecte();
        if (connectes == null || connectes.isEmpty()) {
            System.out.println(" Aucun utilisateur connecté.");
            return;
        }
        System.out.println("Utilisateurs connectés : " + connectes.size());
        connectes.forEach(u -> System.out.println(u.getEmail()));
    }
}
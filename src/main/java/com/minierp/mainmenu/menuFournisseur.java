package com.minierp.mainmenu;
import com.minierp.controller.FournisseurController;
import com.minierp.model.Fournisseur;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class menuFournisseur {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FournisseurController controller = FournisseurController.getInstance();

        System.out.println("===== MENU TEST FOURNISSEUR CONTROLLER =====");

        int choix;
        do {
            afficherMenu();
            choix = lireInt("Votre choix : ");

            switch (choix) {
                case 1 -> creerFournisseur(controller);
                case 2 -> listerTout(controller);
                case 3 -> rechercherParId(controller);
                case 4 -> rechercherParCode(controller);
                case 5 -> rechercherParNom(controller);
                case 6 -> modifierFournisseur(controller);
                case 7 -> listerActifs(controller);
                case 8 -> evaluerFournisseur(controller);
                case 9 -> listerParEvaluation(controller);
                case 10 -> calculerTotalAchats(controller);
                case 11 -> desactiverFournisseur(controller);
                case 12 -> supprimerFournisseur(controller);
                case 13 -> validerFournisseur(controller);
                case 14 -> statistiques(controller);
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("❌ Choix invalide !");
            }

        } while (choix != 0);
        
        // NE PAS FERMER LE SCANNER pour permettre la continuation du menu général
        // scanner.close();
    }

    // ========================== MENU ============================= //
    private static void afficherMenu() {
        System.out.println("\n========= MENU FOURNISSEUR =========");
        System.out.println("1. Créer un fournisseur");
        System.out.println("2. Lister tous les fournisseurs");
        System.out.println("3. Rechercher par ID");
        System.out.println("4. Rechercher par Code");
        System.out.println("5. Rechercher par Nom");
        System.out.println("6. Modifier un fournisseur");
        System.out.println("7. Lister les fournisseurs actifs");
        System.out.println("8. Évaluer un fournisseur");
        System.out.println("9. Lister par évaluation");
        System.out.println("10. Calculer total achats");
        System.out.println("11. Désactiver un fournisseur");
        System.out.println("12. Supprimer un fournisseur");
        System.out.println("13. Tester validation");
        System.out.println("14. Statistiques");
        System.out.println("0. Retour au menu principal");
        System.out.println("==================================");
    }

    // ====================== OUTILS DE LECTURE ========================= //

    private static int lireInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Veuillez entrer un nombre entier !");
                scanner.nextLine();
            }
        }
    }

    private static String lireString(String message) {
        System.out.print(message);
        scanner.nextLine();
        return scanner.nextLine().trim();
    }

    // ====================== FONCTIONS DU MENU ========================== //

    private static void creerFournisseur(FournisseurController controller) {
        System.out.println("\n--- Création Fournisseur ---");

        int id = lireInt("ID : ");
        scanner.nextLine();

        String nom = lireString("Nom entreprise : ");
        String email = lireString("Email : ");

        Fournisseur f = new Fournisseur(id, nom, email);

        System.out.println(controller.creer(f) ?
                "✅ Fournisseur créé !" :
                "❌ Échec création !");
    }

    private static void listerTout(FournisseurController controller) {
        System.out.println("\n--- Liste des Fournisseurs ---");
        controller.listerTout().forEach(System.out::println);
    }

    private static void rechercherParId(FournisseurController controller) {
        int id = lireInt("ID du fournisseur : ");

        Fournisseur f = controller.rechercherParId(id);
        System.out.println(f != null ? f : "❌ Non trouvé");
    }

    private static void rechercherParCode(FournisseurController controller) {
        scanner.nextLine();
        String code = lireString("Code fournisseur : ");

        Fournisseur f = controller.rechercherParCode(code);
        System.out.println(f != null ? f : "❌ Non trouvé");
    }

    private static void rechercherParNom(FournisseurController controller) {
        scanner.nextLine();
        String nom = lireString("Nom (ou partie) : ");

        List<Fournisseur> resultats = controller.rechercherParNom(nom);

        if (resultats.isEmpty())
            System.out.println("❌ Aucun résultat");
        else
            resultats.forEach(System.out::println);
    }

    private static void modifierFournisseur(FournisseurController controller) {
        int id = lireInt("ID du fournisseur à modifier : ");
        Fournisseur f = controller.rechercherParId(id);

        if (f == null) {
            System.out.println("❌ Fournisseur introuvable");
            return;
        }

        scanner.nextLine();
        System.out.println("Laisser vide pour ne pas modifier.");

        String tel = lireString("Nouveau téléphone : ");
        if (!tel.isEmpty()) f.setTelephone(tel);

        String adr = lireString("Nouvelle adresse : ");
        if (!adr.isEmpty()) f.setAdresse(adr);

        System.out.println(controller.modifier(f) ?
                "✅ Modifié !" :
                "❌ Échec modification");
    }

    private static void listerActifs(FournisseurController controller) {
        System.out.println("\n--- Fournisseurs Actifs ---");
        controller.listerActifs().forEach(f -> System.out.println(f.getNomEntreprise()));
    }

    private static void evaluerFournisseur(FournisseurController controller) {
        int id = lireInt("ID : ");
        int note = lireInt("Note (1-5) : ");

        System.out.println(controller.evaluer(id, note) ?
                "✅ Évalué !" :
                "❌ Échec évaluation");
    }

    private static void listerParEvaluation(FournisseurController controller) {
        int note = lireInt("Note recherchée : ");
        List<Fournisseur> liste = controller.listerParEvaluation(note);

        if (liste.isEmpty())
            System.out.println("❌ Aucun fournisseur trouvée");
        else
            liste.forEach(f -> System.out.println(f.getNomEntreprise()));
    }

    private static void calculerTotalAchats(FournisseurController controller) {
        int id = lireInt("ID fournisseur : ");
        double total = controller.calculerTotalAchats(id);

        System.out.println("Total Achats : " + total + " DT");
    }

    private static void desactiverFournisseur(FournisseurController controller) {
        int id = lireInt("ID : ");
        Fournisseur f = controller.rechercherParId(id);

        if (f == null) {
            System.out.println("❌ Introuvable !");
            return;
        }

        f.setActif(false);
        controller.modifier(f);

        System.out.println("✅ Désactivé !");
    }

    private static void supprimerFournisseur(FournisseurController controller) {
        int id = lireInt("ID à supprimer : ");

        System.out.println(controller.supprimer(id) ?
                "✅ Supprimé !" :
                "❌ Échec suppression");
    }

    private static void validerFournisseur(FournisseurController controller) {
        System.out.println("\n--- Test Validation ---");

        Fournisseur invalide = new Fournisseur(0, "", "fake-email");
        System.out.println("Fournisseur invalide valide ? " + invalide.valider());

        if (!controller.listerTout().isEmpty()) {
            Fournisseur f = controller.listerTout().get(0);
            System.out.println("Premier fournisseur valide ? " + f.valider());
        }
    }

    private static void statistiques(FournisseurController controller) {
        System.out.println("\n--- Statistiques ---");
        System.out.println("Total : " + controller.compter());
        System.out.println("Actifs : " + controller.listerActifs().size());

        double evalMoy = controller.listerTout()
                .stream()
                .mapToInt(Fournisseur::getEvaluation)
                .average()
                .orElse(0);

        System.out.println("Évaluation moyenne : " + String.format("%.2f", evalMoy));
    }
}
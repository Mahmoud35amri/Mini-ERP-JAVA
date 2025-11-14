package com.minierp.mainmenu;

import java.util.Scanner;

public class MenuGeneral {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("      MINI ERP - SYSTÈME DE GESTION");
        System.out.println("==========================================");
        
        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireChoix();
            continuer = traiterChoixPrincipal(choix);
        }
        
        System.out.println("\nMerci d'avoir utilisé le système Mini ERP !");
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1.  Gestion des Clients");
        System.out.println("2.  Gestion des Commandes");
        System.out.println("3.  Gestion des Factures");
        System.out.println("4.  Gestion des Catégories");
        System.out.println("5.  Gestion des Entreprises");
        System.out.println("6.  Gestion des Fournisseurs");
        System.out.println("7.  Gestion des Produits");
        System.out.println("8.  Gestion des Utilisateurs");
        System.out.println("9.  Statistiques générales");
        System.out.println("10. Quitter l'application");
        System.out.print("Votre choix : ");
    }

    private static int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean traiterChoixPrincipal(int choix) {
        switch (choix) {
            case 1:
                lancerMenuClient();
                break;
            case 2:
                lancerMenuCommande();
                break;
            case 3:
                lancerMenuFacture();
                break;
            case 4:
                lancerMenuCategorie();
                break;
            case 5:
                lancerMenuEntreprise();
                break;
            case 6:
                lancerMenuFournisseur();
                break;
            case 7:
                lancerMenuProduit();
                break;
            case 8:
                lancerMenuUtilisateur();
                break;
            case 9:
                afficherStatistiquesGenerales();
                break;
            case 10:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 10.");
        }
        return true;
    }

    private static void lancerMenuClient() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU CLIENT");
        System.out.println("=".repeat(50));
        menuClient.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuCommande() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU COMMANDE");
        System.out.println("=".repeat(50));
        menuCommande.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuFacture() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU FACTURE");
        System.out.println("=".repeat(50));
        menuFacture.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuCategorie() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU CATEGORIE");
        System.out.println("=".repeat(50));
        menuCategorie.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuEntreprise() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU ENTREPRISE");
        System.out.println("=".repeat(50));
        menuEntreprise.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuFournisseur() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU FOURNISSEUR");
        System.out.println("=".repeat(50));
        menuFournisseur.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuProduit() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU PRODUIT");
        System.out.println("=".repeat(50));
        menuProduit.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void lancerMenuUtilisateur() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       LANCEMENT DU MENU UTILISATEUR");
        System.out.println("=".repeat(50));
        menuUtilisateur.main(new String[]{});
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RETOUR AU MENU PRINCIPAL");
        System.out.println("=".repeat(50));
    }

    private static void afficherStatistiquesGenerales() {
        System.out.println("\n--- STATISTIQUES GÉNÉRALES ---");
        System.out.println("Cette fonctionnalité affichera les statistiques");
        System.out.println("globales de l'ensemble du système Mini ERP.");
        System.out.println("\nFonctionnalité en cours de développement...");
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    // Méthode utilitaire pour pause
    private static void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();

    }
}
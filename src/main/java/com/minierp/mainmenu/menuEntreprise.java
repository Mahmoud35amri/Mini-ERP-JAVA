package com.minierp.mainmenu;

import java.util.List;
import java.util.Scanner;

import com.minierp.controller.EntrepriseController;
import com.minierp.model.Entreprise;

public class menuEntreprise {
    private static final EntrepriseController controller = EntrepriseController.getInstance();
    
    // Scanner partagé pour compatibilité avec MenuGeneral
    private static Scanner scanner;

    public static void main(String[] args) {
        // Utiliser un Scanner local si appelé directement
        Scanner localScanner = new Scanner(System.in);
        lancerAvecScanner(localScanner);
        // Ne pas fermer le Scanner pour éviter les problèmes avec System.in
    }

    // Nouvelle méthode pour utilisation avec MenuGeneral
    public static void lancerAvecScanner(Scanner sharedScanner) {
        scanner = sharedScanner;
        
        System.out.println("===== MINI ERP - GESTION DES ENTREPRISES =====");

        int choix = -1;

        while (choix != 0) {
            afficherMenu();
            choix = lireInt("Votre choix : ");

            switch (choix) {
                case 1 -> creerEntreprise();
                case 2 -> rechercherParId();
                case 3 -> rechercherParNom();
                case 4 -> modifierEntreprise();
                case 5 -> supprimerEntreprise();
                case 6 -> listerToutes();
                case 7 -> listerActives();
                case 8 -> desactiverEntreprise();
                case 0 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Choix invalide !");
            }

            if (choix != 0) {
                System.out.println("\n-------------------------------------\n");
            }
        }
    }

    // ====================================================
    // MENU
    // ====================================================
    private static void afficherMenu() {
        System.out.println("\n--- MENU ENTREPRISES ---");
        System.out.println("1. Créer une entreprise");
        System.out.println("2. Rechercher par ID");
        System.out.println("3. Rechercher par nom");
        System.out.println("4. Modifier une entreprise");
        System.out.println("5. Supprimer une entreprise");
        System.out.println("6. Lister toutes les entreprises");
        System.out.println("7. Lister les entreprises actives");
        System.out.println("8. Désactiver une entreprise");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix : ");
    }

    // ====================================================
    // CREATION
    // ====================================================
    private static void creerEntreprise() {
        System.out.println("\n=== CRÉATION D'UNE ENTREPRISE ===");

        String nom = lireString("Nom : ");
        String raison = lireString("Raison sociale : ");
        String email = lireString("Email : ");
        String pays = lireString("Pays : ");

        // Informations supplémentaires optionnelles
        System.out.print("Adresse (optionnel) : ");
        String adresse = scanner.nextLine().trim();
        
        System.out.print("Téléphone (optionnel) : ");
        String telephone = scanner.nextLine().trim();
        
        System.out.print("Site web (optionnel) : ");
        String siteWeb = scanner.nextLine().trim();

        Entreprise e = new Entreprise(nom, raison, email, pays);
        
        // Ajouter les informations optionnelles si fournies
        if (!adresse.isEmpty()) e.setAdresse(adresse);
        if (!telephone.isEmpty()) e.setTelephone(telephone);
        if (!siteWeb.isEmpty()) e.setSiteWeb(siteWeb);

        if (!e.valider()) {
            System.out.println("❌ Données invalides ! Création annulée.");
            return;
        }

        boolean succes = controller.creer(e);
        if (succes) {
            System.out.println("✅ Entreprise créée avec ID : " + e.getId());
        } else {
            System.out.println("❌ Erreur lors de la création de l'entreprise.");
        }
    }

    // ====================================================
    // RECHERCHE ID
    // ====================================================
    private static void rechercherParId() {
        int id = lireInt("ID à chercher : ");
        Entreprise e = controller.rechercherParId(id);

        if (e == null)
            System.out.println("❌ Aucune entreprise trouvée !");
        else {
            System.out.println("✅ Entreprise trouvée :");
            System.out.println(e);
        }
    }

    // ====================================================
    // RECHERCHE NOM
    // ====================================================
    private static void rechercherParNom() {
        String nom = lireString("Nom contenant : ");
        List<Entreprise> liste = controller.rechercherParNom(nom);

        if (liste.isEmpty())
            System.out.println("❌ Aucun résultat !");
        else {
            System.out.println("✅ " + liste.size() + " entreprise(s) trouvée(s) :");
            liste.forEach(System.out::println);
        }
    }

    // ====================================================
    // MODIFICATION
    // ====================================================
    private static void modifierEntreprise() {
        int id = lireInt("ID à modifier : ");
        Entreprise e = controller.rechercherParId(id);

        if (e == null) {
            System.out.println("❌ ID inexistant !");
            return;
        }

        System.out.println("Entreprise actuelle : " + e);
        System.out.println("Laissez vide pour ne pas modifier.");

        String nom = lireStringOptionnel("Nouveau nom : ");
        String raison = lireStringOptionnel("Nouvelle raison sociale : ");
        String email = lireStringOptionnel("Nouvel email : ");
        String pays = lireStringOptionnel("Nouveau pays : ");
        String adresse = lireStringOptionnel("Nouvelle adresse : ");
        String telephone = lireStringOptionnel("Nouveau téléphone : ");
        String siteWeb = lireStringOptionnel("Nouveau site web : ");

        if (!nom.isEmpty()) e.setNom(nom);
        if (!raison.isEmpty()) e.setRaisonSociale(raison);
        if (!email.isEmpty()) e.setEmail(email);
        if (!pays.isEmpty()) e.setPays(pays);
        if (!adresse.isEmpty()) e.setAdresse(adresse);
        if (!telephone.isEmpty()) e.setTelephone(telephone);
        if (!siteWeb.isEmpty()) e.setSiteWeb(siteWeb);

        if (!e.valider()) {
            System.out.println("❌ Données invalides ! Modification annulée.");
            return;
        }

        boolean succes = controller.modifier(e);
        if (succes) {
            System.out.println("✅ Modification réussie.");
            System.out.println("Entreprise après modification : " + e);
        } else {
            System.out.println("❌ Erreur lors de la modification.");
        }
    }

    // ====================================================
    // SUPPRESSION
    // ====================================================
    private static void supprimerEntreprise() {
        int id = lireInt("ID à supprimer : ");

        // Demander confirmation
        Entreprise e = controller.rechercherParId(id);
        if (e != null) {
            System.out.println("Entreprise à supprimer : " + e.getNom());
            System.out.print("Êtes-vous sûr de vouloir supprimer cette entreprise ? (o/n) : ");
            String confirmation = scanner.nextLine().trim();
            
            if (confirmation.equalsIgnoreCase("o")) {
                boolean ok = controller.supprimer(id);
                System.out.println(ok ? "✅ Suppression réussie." : "❌ ID introuvable.");
            } else {
                System.out.println("❌ Suppression annulée.");
            }
        } else {
            System.out.println("❌ Entreprise non trouvée.");
        }
    }

    // ====================================================
    // LISTES
    // ====================================================
    private static void listerToutes() {
        System.out.println("\n=== LISTE DE TOUTES LES ENTREPRISES ===");
        List<Entreprise> entreprises = controller.listerTout();
        if (entreprises.isEmpty()) {
            System.out.println("Aucune entreprise enregistrée.");
        } else {
            System.out.println("Total : " + entreprises.size() + " entreprise(s)");
            entreprises.forEach(System.out::println);
        }
    }

    private static void listerActives() {
        System.out.println("\n=== ENTREPRISES ACTIVES ===");
        List<Entreprise> actives = controller.listerActives();
        if (actives.isEmpty()) {
            System.out.println("Aucune entreprise active.");
        } else {
            System.out.println("Total actives : " + actives.size() + " entreprise(s)");
            actives.forEach(System.out::println);
        }
    }

    // ====================================================
    // DESACTIVATION
    // ====================================================
    private static void desactiverEntreprise() {
        int id = lireInt("ID à désactiver : ");

        Entreprise e = controller.rechercherParId(id);

        if (e == null) {
            System.out.println("❌ ID inexistant !");
            return;
        }

        if (!e.isActif()) {
            System.out.println("⚠️ Cette entreprise est déjà désactivée.");
            return;
        }

        System.out.println("Entreprise à désactiver : " + e.getNom());
        System.out.print("Êtes-vous sûr de vouloir désactiver cette entreprise ? (o/n) : ");
        String confirmation = scanner.nextLine().trim();
        
        if (confirmation.equalsIgnoreCase("o")) {
            e.setActif(false);
            boolean succes = controller.modifier(e);
            System.out.println(succes ? "✅ Entreprise désactivée." : "❌ Erreur lors de la désactivation.");
        } else {
            System.out.println("❌ Désactivation annulée.");
        }
    }

    // ====================================================
    // FONCTIONS UTILITAIRES
    // ====================================================
    private static int lireInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez saisir un nombre valide !");
            }
        }
    }

    private static String lireString(String msg) {
        String s;
        do {
            System.out.print(msg);
            s = scanner.nextLine().trim();
            if (s.isEmpty())
                System.out.println("❌ Ce champ ne peut pas être vide !");
        } while (s.isEmpty());
        return s;
    }

    private static String lireStringOptionnel(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
}
package com.minierp.mainmenu;

import com.minierp.controller.CategorieController;
import com.minierp.model.Categorie;
import java.util.List;
import java.util.Scanner;

public class menuCategorie {
    private static CategorieController controller = CategorieController.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== APPLICATION DE GESTION DES CATÉGORIES =====\n");
        
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = lireChoix();
            continuer = traiterChoix(choix);
        }
        
        System.out.println("\nMerci d'avoir utilisé l'application !");
        // scanner.close(); // NE PAS FERMER pour compatibilité avec MenuGeneral
    }

    private static void afficherMenu() {
        System.out.println("\n--- MENU PRINCIPAL CATÉGORIES ---");
        System.out.println("1. Créer une nouvelle catégorie");
        System.out.println("2. Lister toutes les catégories");
        System.out.println("3. Rechercher une catégorie par ID");
        System.out.println("4. Rechercher par code");
        System.out.println("5. Rechercher par nom");
        System.out.println("6. Modifier une catégorie");
        System.out.println("7. Lister les catégories actives");
        System.out.println("8. Lister les catégories racines");
        System.out.println("9. Lister les sous-catégories");
        System.out.println("10. Afficher le chemin complet");
        System.out.println("11. Déplacer une catégorie");
        System.out.println("12. Compter les produits d'une catégorie");
        System.out.println("13. Désactiver une catégorie");
        System.out.println("14. Supprimer une catégorie");
        System.out.println("15. Valider une catégorie");
        System.out.println("16. Quitter");
        System.out.print("Votre choix : ");
    }

    private static int lireChoix() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean traiterChoix(int choix) {
        switch (choix) {
            case 1:
                creerCategorie();
                break;
            case 2:
                listerToutesCategories();
                break;
            case 3:
                rechercherParId();
                break;
            case 4:
                rechercherParCode();
                break;
            case 5:
                rechercherParNom();
                break;
            case 6:
                modifierCategorie();
                break;
            case 7:
                listerCategoriesActives();
                break;
            case 8:
                listerCategoriesRacines();
                break;
            case 9:
                listerSousCategories();
                break;
            case 10:
                afficherCheminComplet();
                break;
            case 11:
                deplacerCategorie();
                break;
            case 12:
                compterProduitsCategorie();
                break;
            case 13:
                desactiverCategorie();
                break;
            case 14:
                supprimerCategorie();
                break;
            case 15:
                validerCategorie();
                break;
            case 16:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 16.");
        }
        return true;
    }

    private static void creerCategorie() {
        System.out.println("\n--- CRÉATION D'UNE NOUVELLE CATÉGORIE ---");
        
        System.out.print("ID : ");
        int id = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        System.out.print("Description : ");
        String description = scanner.nextLine();
        
        System.out.print("Code (optionnel) : ");
        String code = scanner.nextLine();

        // Sélection de la catégorie parente (optionnelle)
        List<Categorie> categories = controller.listerTout();
        if (!categories.isEmpty()) {
            System.out.println("\nCatégories disponibles (pour parente) :");
            categories.forEach(c -> System.out.println(c.getId() + ". " + c.getNom()));
            System.out.print("ID de la catégorie parente (0 pour aucune) : ");
            int parentId = Integer.parseInt(scanner.nextLine());
            
            Categorie categorie = new Categorie(id, nom, description);
            if (!code.isEmpty()) categorie.setCode(code);
            
            if (parentId > 0) {
                Categorie parent = controller.rechercherParId(parentId);
                if (parent != null) {
                    categorie.setCategorieParente(parent);
                }
            }
            
            boolean succes = controller.creer(categorie);
            System.out.println(succes ? "Catégorie créée avec succès !" : "Échec de la création.");
        } else {
            Categorie categorie = new Categorie(id, nom, description);
            if (!code.isEmpty()) categorie.setCode(code);
            
            boolean succes = controller.creer(categorie);
            System.out.println(succes ? "Catégorie racine créée avec succès !" : "Échec de la création.");
        }
    }

    private static void listerToutesCategories() {
        System.out.println("\n--- LISTE DE TOUTES LES CATÉGORIES ---");
        List<Categorie> categories = controller.listerTout();
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie trouvée.");
        } else {
            categories.forEach(System.out::println);
        }
    }

    private static void rechercherParId() {
        System.out.print("\nEntrez l'ID de la catégorie : ");
        int id = Integer.parseInt(scanner.nextLine());
        Categorie categorie = controller.rechercherParId(id);
        System.out.println(categorie != null ? categorie : "Catégorie non trouvée.");
    }

    private static void rechercherParCode() {
        System.out.print("\nEntrez le code : ");
        String code = scanner.nextLine();
        Categorie categorie = controller.rechercherParCode(code);
        System.out.println(categorie != null ? categorie : "Catégorie non trouvée.");
    }

    private static void rechercherParNom() {
        System.out.print("\nEntrez le nom (ou partie) : ");
        String nom = scanner.nextLine();
        List<Categorie> categories = controller.rechercherParNom(nom);
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie trouvée avec ce nom.");
        } else {
            categories.forEach(System.out::println);
        }
    }

    private static void modifierCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());
        Categorie categorie = controller.rechercherParId(id);
        
        if (categorie == null) {
            System.out.println("Catégorie non trouvée.");
            return;
        }

        System.out.println("Catégorie actuelle : " + categorie);
        System.out.println("Laissez vide pour ne pas modifier.");

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();
        if (!nom.isEmpty()) {
            categorie.setNom(nom);
        }

        System.out.print("Nouvelle description : ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            categorie.setDescription(description);
        }

        boolean succes = controller.modifier(categorie);
        System.out.println(succes ? "Catégorie modifiée avec succès !" : "Échec de la modification.");
    }

    private static void listerCategoriesActives() {
        System.out.println("\n--- CATÉGORIES ACTIVES ---");
        List<Categorie> actives = controller.listerActives();
        if (actives.isEmpty()) {
            System.out.println("Aucune catégorie active.");
        } else {
            actives.forEach(System.out::println);
        }
    }

    private static void listerCategoriesRacines() {
        System.out.println("\n--- CATÉGORIES RACINES ---");
        List<Categorie> racines = controller.listerCategoriesRacines();
        if (racines.isEmpty()) {
            System.out.println("Aucune catégorie racine.");
        } else {
            racines.forEach(System.out::println);
        }
    }

    private static void listerSousCategories() {
        System.out.print("\nEntrez l'ID de la catégorie parente : ");
        int parentId = Integer.parseInt(scanner.nextLine());
        List<Categorie> sousCategories = controller.listerSousCategories(parentId);
        
        Categorie parent = controller.rechercherParId(parentId);
        if (parent != null) {
            System.out.println("Sous-catégories de " + parent.getNom() + " :");
            if (sousCategories.isEmpty()) {
                System.out.println("Aucune sous-catégorie.");
            } else {
                sousCategories.forEach(System.out::println);
            }
        } else {
            System.out.println("Catégorie parente non trouvée.");
        }
    }

    private static void afficherCheminComplet() {
        System.out.print("\nEntrez l'ID de la catégorie : ");
        int id = Integer.parseInt(scanner.nextLine());
        String chemin = controller.getCheminComplet(id);
        
        if (chemin != null) {
            System.out.println("Chemin complet : " + chemin);
        } else {
            System.out.println("Catégorie non trouvée.");
        }
    }

    private static void deplacerCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie à déplacer : ");
        int categorieId = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Entrez l'ID de la nouvelle catégorie parente (0 pour racine) : ");
        int nouvelleParentId = Integer.parseInt(scanner.nextLine());
        
        boolean succes = controller.deplacerCategorie(categorieId, nouvelleParentId);
        if (succes) {
            System.out.println("Catégorie déplacée avec succès !");
            String nouveauChemin = controller.getCheminComplet(categorieId);
            if (nouveauChemin != null) {
                System.out.println("Nouveau chemin : " + nouveauChemin);
            }
        } else {
            System.out.println("Échec du déplacement.");
        }
    }

    private static void compterProduitsCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie : ");
        int id = Integer.parseInt(scanner.nextLine());
        int nbProduits = controller.compterProduits(id);
        
        Categorie categorie = controller.rechercherParId(id);
        if (categorie != null) {
            System.out.println("Nombre de produits dans " + categorie.getNom() + " : " + nbProduits);
        } else {
            System.out.println("Catégorie non trouvée.");
        }
    }

    private static void desactiverCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie à désactiver : ");
        int id = Integer.parseInt(scanner.nextLine());
        Categorie categorie = controller.rechercherParId(id);
        
        if (categorie != null) {
            categorie.setActif(false);
            controller.modifier(categorie);
            System.out.println("Catégorie désactivée avec succès.");
        } else {
            System.out.println("Catégorie non trouvée.");
        }
    }

    private static void supprimerCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean succes = controller.supprimer(id);
        System.out.println(succes ? "Catégorie supprimée avec succès." : "Échec de la suppression.");
    }

    private static void validerCategorie() {
        System.out.print("\nEntrez l'ID de la catégorie à valider : ");
        int id = Integer.parseInt(scanner.nextLine());
        Categorie categorie = controller.rechercherParId(id);
        
        if (categorie != null) {
            System.out.println("Catégorie valide ? " + categorie.valider());
        } else {
            System.out.println("Catégorie non trouvée.");
        }
    }
}
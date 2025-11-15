package com.minierp.mainmenu;

import com.minierp.controller.ProduitController;
import com.minierp.controller.CategorieController;
import com.minierp.controller.FournisseurController;
import com.minierp.model.Produit;
import com.minierp.model.Categorie;
import com.minierp.model.Fournisseur;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class menuProduit {
    private static final ProduitController controller = ProduitController.getInstance();
    private static final CategorieController catCtrl = CategorieController.getInstance();
    private static final FournisseurController fourCtrl = FournisseurController.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== APPLICATION DE GESTION DES PRODUITS =====\n");
        
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
        System.out.println("\n--- MENU PRINCIPAL PRODUITS ---");
        System.out.println("1. Créer un nouveau produit");
        System.out.println("2. Lister tous les produits");
        System.out.println("3. Rechercher un produit par ID");
        System.out.println("4. Rechercher par référence");
        System.out.println("5. Rechercher par code-barres");
        System.out.println("6. Rechercher par nom");
        System.out.println("7. Modifier un produit");
        System.out.println("8. Ajuster le stock");
        System.out.println("9. Lister les produits actifs");
        System.out.println("10. Lister par catégorie");
        System.out.println("11. Lister par fournisseur");
        System.out.println("12. Lister les produits en rupture");
        System.out.println("13. Lister les produits sous seuil d'alerte");
        System.out.println("14. Appliquer une promotion");
        System.out.println("15. Annuler une promotion");
        System.out.println("16. Lister les nouveautés");
        System.out.println("17. Calculer la valeur du stock");
        System.out.println("18. Désactiver un produit");
        System.out.println("19. Supprimer un produit");
        System.out.println("20. Valider un produit");
        System.out.println("21. Afficher les statistiques");
        System.out.println("22. Quitter");
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
                creerProduit();
                break;
            case 2:
                listerTousProduits();
                break;
            case 3:
                rechercherParId();
                break;
            case 4:
                rechercherParReference();
                break;
            case 5:
                rechercherParCodeBarres();
                break;
            case 6:
                rechercherParNom();
                break;
            case 7:
                modifierProduit();
                break;
            case 8:
                ajusterStock();
                break;
            case 9:
                listerProduitsActifs();
                break;
            case 10:
                listerParCategorie();
                break;
            case 11:
                listerParFournisseur();
                break;
            case 12:
                listerEnRupture();
                break;
            case 13:
                listerSousSeuilAlerte();
                break;
            case 14:
                appliquerPromotion();
                break;
            case 15:
                annulerPromotion();
                break;
            case 16:
                listerNouveautes();
                break;
            case 17:
                calculerValeurStock();
                break;
            case 18:
                desactiverProduit();
                break;
            case 19:
                supprimerProduit();
                break;
            case 20:
                validerProduit();
                break;
            case 21:
                afficherStatistiques();
                break;
            case 22:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 22.");
        }
        return true;
    }

    private static void creerProduit() {
        System.out.println("\n--- CRÉATION D'UN NOUVEAU PRODUIT ---");
        
        // Vérification des catégories existantes
        List<Categorie> categories = catCtrl.listerTout();
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie disponible. Veuillez d'abord créer une catégorie.");
            return;
        }
        
        // Vérification des fournisseurs existants
        List<Fournisseur> fournisseurs = fourCtrl.listerTout();
        if (fournisseurs.isEmpty()) {
            System.out.println("Aucun fournisseur disponible. Veuillez d'abord créer un fournisseur.");
            return;
        }

        // Saisie des informations du produit
        System.out.print("Référence : ");
        String reference = scanner.nextLine();
        
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        // Sélection de la catégorie
        System.out.println("\nCatégories disponibles :");
        categories.forEach(c -> System.out.println(c.getId() + ". " + c.getNom()));
        System.out.print("ID de la catégorie : ");
        int categorieId = Integer.parseInt(scanner.nextLine());
        Categorie categorie = catCtrl.rechercherParId(categorieId);
        if (categorie == null) {
            System.out.println("Catégorie non trouvée !");
            return;
        }

        // Sélection du fournisseur
        System.out.println("\nFournisseurs disponibles :");
        fournisseurs.forEach(f -> System.out.println(f.getId() + ". " + f.getNomEntreprise()));
        System.out.print("ID du fournisseur : ");
        int fournisseurId = Integer.parseInt(scanner.nextLine());
        Fournisseur fournisseur = fourCtrl.rechercherParId(fournisseurId);
        if (fournisseur == null) {
            System.out.println("Fournisseur non trouvé !");
            return;
        }

        System.out.print("Prix d'achat : ");
        double prixAchat = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Prix de vente : ");
        double prixVente = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Quantité en stock : ");
        int quantiteStock = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Seuil d'alerte : ");
        int seuilAlerte = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Code-barres (optionnel) : ");
        String codeBarres = scanner.nextLine();
        
        System.out.print("Description (optionnel) : ");
        String description = scanner.nextLine();

        // Création du produit
        Produit produit = new Produit(reference, nom, categorie, fournisseur, prixAchat, prixVente, quantiteStock);
        produit.setSeuilAlerte(seuilAlerte);
        if (!codeBarres.isEmpty()) produit.setCodeBarres(codeBarres);
        if (!description.isEmpty()) produit.setDescription(description);

        boolean succes = controller.creer(produit);
        System.out.println(succes ? "Produit créé avec succès !" : "Échec de la création du produit.");
    }

    private static void listerTousProduits() {
        System.out.println("\n--- LISTE DE TOUS LES PRODUITS ---");
        List<Produit> produits = controller.listerTout();
        if (produits.isEmpty()) {
            System.out.println("Aucun produit trouvé.");
        } else {
            System.out.println("Total produits: " + produits.size());
            produits.forEach(System.out::println);
        }
    }

    private static void rechercherParId() {
        System.out.print("\nEntrez l'ID du produit : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        System.out.println(produit != null ? produit : "Produit non trouvé.");
    }

    private static void rechercherParReference() {
        System.out.print("\nEntrez la référence : ");
        String reference = scanner.nextLine();
        Produit produit = controller.rechercherParReference(reference);
        System.out.println(produit != null ? produit : "Produit non trouvé.");
    }

    private static void rechercherParCodeBarres() {
        System.out.print("\nEntrez le code-barres : ");
        String codeBarres = scanner.nextLine();
        Produit produit = controller.rechercherParCodeBarres(codeBarres);
        System.out.println(produit != null ? produit : "Produit non trouvé.");
    }

    private static void rechercherParNom() {
        System.out.print("\nEntrez le nom (ou partie) : ");
        String nom = scanner.nextLine();
        List<Produit> produits = controller.rechercherParNom(nom);
        if (produits.isEmpty()) {
            System.out.println("Aucun produit trouvé avec ce nom.");
        } else {
            produits.forEach(System.out::println);
        }
    }

    private static void modifierProduit() {
        System.out.print("\nEntrez l'ID du produit à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        
        if (produit == null) {
            System.out.println("Produit non trouvé.");
            return;
        }

        System.out.println("Produit actuel : " + produit);
        System.out.println("Laissez vide pour ne pas modifier.");

        System.out.print("Nouveau prix de vente : ");
        String prixVente = scanner.nextLine();
        if (!prixVente.isEmpty()) {
            produit.setPrixVente(Double.parseDouble(prixVente));
        }

        System.out.print("Nouvelle description : ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            produit.setDescription(description);
        }

        System.out.print("Nouveau seuil d'alerte : ");
        String seuil = scanner.nextLine();
        if (!seuil.isEmpty()) {
            produit.setSeuilAlerte(Integer.parseInt(seuil));
        }

        boolean succes = controller.modifier(produit);
        System.out.println(succes ? "Produit modifié avec succès !" : "Échec de la modification.");
    }

    private static void ajusterStock() {
        System.out.print("\nEntrez l'ID du produit : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        
        if (produit == null) {
            System.out.println("Produit non trouvé.");
            return;
        }

        System.out.println("Stock actuel : " + produit.getQuantiteStock());
        System.out.print("Quantité à ajouter (+ pour augmentation, - pour diminution) : ");
        int quantite = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Raison de l'ajustement : ");
        String raison = scanner.nextLine();

        controller.ajusterStock(id, quantite, raison);
        System.out.println("Stock ajusté. Nouveau stock : " + 
            controller.rechercherParId(id).getQuantiteStock());
    }

    private static void listerProduitsActifs() {
        System.out.println("\n--- PRODUITS ACTIFS ---");
        List<Produit> actifs = controller.listerActifs();
        if (actifs.isEmpty()) {
            System.out.println("Aucun produit actif.");
        } else {
            actifs.forEach(p -> System.out.println(p.getNom() + " - Stock: " + p.getQuantiteStock()));
        }
    }

    private static void listerParCategorie() {
        List<Categorie> categories = catCtrl.listerTout();
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie disponible.");
            return;
        }

        System.out.println("\nCatégories disponibles :");
        categories.forEach(c -> System.out.println(c.getId() + ". " + c.getNom()));
        System.out.print("ID de la catégorie : ");
        int categorieId = Integer.parseInt(scanner.nextLine());
        
        Categorie categorie = catCtrl.rechercherParId(categorieId);
        if (categorie != null) {
            List<Produit> produits = controller.listerParCategorie(categorie);
            System.out.println("Produits de la catégorie " + categorie.getNom() + " (" + produits.size() + ") :");
            produits.forEach(p -> System.out.println(p.getNom()));
        } else {
            System.out.println("Catégorie non trouvée.");
        }
    }

    private static void listerParFournisseur() {
        List<Fournisseur> fournisseurs = fourCtrl.listerTout();
        if (fournisseurs.isEmpty()) {
            System.out.println("Aucun fournisseur disponible.");
            return;
        }

        System.out.println("\nFournisseurs disponibles :");
        fournisseurs.forEach(f -> System.out.println(f.getId() + ". " + f.getNomEntreprise()));
        System.out.print("ID du fournisseur : ");
        int fournisseurId = Integer.parseInt(scanner.nextLine());
        
        Fournisseur fournisseur = fourCtrl.rechercherParId(fournisseurId);
        if (fournisseur != null) {
            List<Produit> produits = controller.listerParFournisseur(fournisseur);
            System.out.println("Produits du fournisseur " + fournisseur.getNomEntreprise() + " (" + produits.size() + ") :");
            produits.forEach(p -> System.out.println(p.getNom()));
        } else {
            System.out.println("Fournisseur non trouvé.");
        }
    }

    private static void listerEnRupture() {
        System.out.println("\n--- PRODUITS EN RUPTURE DE STOCK ---");
        List<Produit> rupture = controller.listerEnRupture();
        if (rupture.isEmpty()) {
            System.out.println("Aucun produit en rupture de stock.");
        } else {
            rupture.forEach(p -> System.out.println(p.getNom() + " - Stock: " + p.getQuantiteStock()));
        }
    }

    private static void listerSousSeuilAlerte() {
        System.out.println("\n--- PRODUITS SOUS SEUIL D'ALERTE ---");
        List<Produit> sousSeuil = controller.listerSousSeuilAlerte();
        if (sousSeuil.isEmpty()) {
            System.out.println("Aucun produit sous le seuil d'alerte.");
        } else {
            sousSeuil.forEach(p -> 
                System.out.println(p.getNom() + " - Stock: " + p.getQuantiteStock() + 
                                 " (Seuil: " + p.getSeuilAlerte() + ")")
            );
        }
    }

    private static void appliquerPromotion() {
        System.out.print("\nEntrez l'ID du produit : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        
        if (produit == null) {
            System.out.println("Produit non trouvé.");
            return;
        }

        System.out.print("Taux de promotion (%) : ");
        double taux = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Durée de la promotion (en jours) : ");
        int jours = Integer.parseInt(scanner.nextLine());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, jours);
        Date dateFin = cal.getTime();

        controller.appliquerPromotion(id, taux, dateFin);
        System.out.println("Promotion appliquée ! Prix actuel : " + 
            controller.rechercherParId(id).getPrixVenteActuel() + " DT");
    }

    private static void annulerPromotion() {
        System.out.print("\nEntrez l'ID du produit : ");
        int id = Integer.parseInt(scanner.nextLine());
        controller.annulerPromotion(id);
        System.out.println("Promotion annulée !");
    }

    private static void listerNouveautes() {
        System.out.println("\n--- NOUVEAUTÉS ---");
        List<Produit> nouveautes = controller.listerNouveautes();
        if (nouveautes.isEmpty()) {
            System.out.println("Aucune nouveauté.");
        } else {
            nouveautes.forEach(System.out::println);
        }
    }

    private static void calculerValeurStock() {
        System.out.println("\n--- VALEUR DU STOCK ---");
        double valeur = controller.calculerValeurStock();
        System.out.println("Valeur totale du stock : " + String.format("%.2f", valeur) + " DT");
    }

    private static void desactiverProduit() {
        System.out.print("\nEntrez l'ID du produit à désactiver : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        
        if (produit != null) {
            produit.setActif(false);
            controller.modifier(produit);
            System.out.println("Produit désactivé avec succès.");
        } else {
            System.out.println("Produit non trouvé.");
        }
    }

    private static void supprimerProduit() {
        System.out.print("\nEntrez l'ID du produit à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean succes = controller.supprimer(id);
        System.out.println(succes ? "Produit supprimé avec succès." : "Échec de la suppression.");
    }

    private static void validerProduit() {
        System.out.print("\nEntrez l'ID du produit à valider : ");
        int id = Integer.parseInt(scanner.nextLine());
        Produit produit = controller.rechercherParId(id);
        
        if (produit != null) {
            System.out.println("Produit valide ? " + produit.valider());
        } else {
            System.out.println("Produit non trouvé.");
        }
    }

    private static void afficherStatistiques() {
        System.out.println("\n--- STATISTIQUES DES PRODUITS ---");
        System.out.println("Total produits: " + controller.compter());
        System.out.println("Actifs: " + controller.listerActifs().size());
        System.out.println("En rupture: " + controller.listerEnRupture().size());
        System.out.println("Sous seuil: " + controller.listerSousSeuilAlerte().size());
        System.out.println("En promotion: " + controller.listerEnPromotion().size());
        System.out.println("Nouveautés: " + controller.listerNouveautes().size());
        
        double valeurStock = controller.calculerValeurStock();
        System.out.println("Valeur du stock: " + String.format("%.2f", valeurStock) + " DT");
    }
}
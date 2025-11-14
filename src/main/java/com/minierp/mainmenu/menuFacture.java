package com.minierp.mainmenu;
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
import java.util.Scanner;
public class menuFacture {
    private static FactureController controller = FactureController.getInstance();
    private static CommandeController cmdCtrl = CommandeController.getInstance();
    private static ClientController clientCtrl = ClientController.getInstance();
    private static UtilisateurController userCtrl = UtilisateurController.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== APPLICATION DE GESTION DES FACTURES =====\n");
        
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = lireChoix();
            continuer = traiterChoix(choix);
        }
        
        System.out.println("\nMerci d'avoir utilisé l'application !");
        //scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n--- MENU PRINCIPAL FACTURES ---");
        System.out.println("1. Créer une nouvelle facture");
        System.out.println("2. Générer une facture depuis une commande");
        System.out.println("3. Lister toutes les factures");
        System.out.println("4. Rechercher une facture par ID");
        System.out.println("5. Rechercher une facture par numéro");
        System.out.println("6. Modifier une facture");
        System.out.println("7. Lister les factures par client");
        System.out.println("8. Lister les factures par statut");
        System.out.println("9. Rechercher une facture par commande");
        System.out.println("10. Calculer le chiffre d'affaires");
        System.out.println("11. Changer le statut d'une facture");
        System.out.println("12. Afficher les statistiques");
        System.out.println("13. Lister les factures impayées");
        System.out.println("14. Supprimer une facture");
        System.out.println("15. Valider une facture");
        System.out.println("16. Afficher les délais de paiement");
        System.out.println("17. Quitter");
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
                creerFacture();
                break;
            case 2:
                genererFactureDepuisCommande();
                break;
            case 3:
                listerToutesFactures();
                break;
            case 4:
                rechercherParId();
                break;
            case 5:
                rechercherParNumero();
                break;
            case 6:
                modifierFacture();
                break;
            case 7:
                listerParClient();
                break;
            case 8:
                listerParStatut();
                break;
            case 9:
                rechercherParCommande();
                break;
            case 10:
                calculerChiffreAffaires();
                break;
            case 11:
                changerStatut();
                break;
            case 12:
                afficherStatistiques();
                break;
            case 13:
                listerFacturesImpayees();
                break;
            case 14:
                supprimerFacture();
                break;
            case 15:
                validerFacture();
                break;
            case 16:
                afficherDelaisPaiement();
                break;
            case 17:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 17.");
        }
        return true;
    }

    private static void creerFacture() {
        System.out.println("\n--- CRÉATION D'UNE NOUVELLE FACTURE ---");
        
        // Vérification des commandes existantes
        List<Commande> commandes = cmdCtrl.listerTout();
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande disponible. Veuillez d'abord créer une commande.");
            return;
        }
        
        // Vérification des clients existants
        List<Client> clients = clientCtrl.listerTout();
        if (clients.isEmpty()) {
            System.out.println("Aucun client disponible. Veuillez d'abord créer un client.");
            return;
        }

        // Sélection de la commande
        System.out.println("\nCommandes disponibles :");
        commandes.forEach(c -> System.out.println(c.getId() + ". " + c.getNumeroCommande() + 
            " - " + c.getClient().getNom() + " - Montant: " + c.getMontantTTC() + " DT"));
        System.out.print("ID de la commande : ");
        int commandeId = Integer.parseInt(scanner.nextLine());
        Commande commande = cmdCtrl.rechercherParId(commandeId);
        if (commande == null) {
            System.out.println("Commande non trouvée !");
            return;
        }

        // Sélection du client
        System.out.println("\nClients disponibles :");
        clients.forEach(c -> System.out.println(c.getId() + ". " + c.getNom() + " " + c.getPrenom()));
        System.out.print("ID du client : ");
        int clientId = Integer.parseInt(scanner.nextLine());
        Client client = clientCtrl.rechercherParId(clientId);
        if (client == null) {
            System.out.println("Client non trouvé !");
            return;
        }

        // Création de la facture
        Facture facture = new Facture(commande, client);
        
        // Saisie des informations supplémentaires
        System.out.print("Montant HT : ");
        double montantHT = Double.parseDouble(scanner.nextLine());
        facture.setMontantHT(montantHT);
        
        System.out.print("Montant TTC : ");
        double montantTTC = Double.parseDouble(scanner.nextLine());
        facture.setMontantTTC(montantTTC);
        
        System.out.print("Date limite de paiement (jj/mm/aaaa, optionnel) : ");
        String dateLimiteStr = scanner.nextLine();
        if (!dateLimiteStr.isEmpty()) {
            // Implémentation simplifiée - à adapter selon votre format de date
            System.out.println("Date limite définie (implémentation à compléter)");
        }

        boolean succes = controller.creer(facture);
        System.out.println(succes ? "Facture créée avec succès !" : "Échec de la création de la facture.");
    }

    private static void genererFactureDepuisCommande() {
        System.out.println("\n--- GÉNÉRATION DE FACTURE DEPUIS COMMANDE ---");
        
        List<Commande> commandes = cmdCtrl.listerTout();
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande disponible.");
            return;
        }

        System.out.println("\nCommandes disponibles :");
        commandes.forEach(c -> System.out.println(c.getId() + ". " + c.getNumeroCommande() + 
            " - " + c.getClient().getNom() + " - " + c.getMontantTTC() + " DT"));
        System.out.print("ID de la commande : ");
        int commandeId = Integer.parseInt(scanner.nextLine());
        
        Commande commande = cmdCtrl.rechercherParId(commandeId);
        if (commande == null) {
            System.out.println("Commande non trouvée !");
            return;
        }

        Facture factureGeneree = controller.genererDepuisCommande(commande);
        if (factureGeneree != null) {
            System.out.println("Facture générée : " + factureGeneree);
            System.out.print("Voulez-vous créer cette facture ? (o/n) : ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("o")) {
                boolean succes = controller.creer(factureGeneree);
                System.out.println(succes ? "Facture créée avec succès !" : "Échec de la création.");
            }
        } else {
            System.out.println("Impossible de générer la facture depuis cette commande.");
        }
    }

    private static void listerToutesFactures() {
        System.out.println("\n--- LISTE DE TOUTES LES FACTURES ---");
        List<Facture> factures = controller.listerTout();
        if (factures.isEmpty()) {
            System.out.println("Aucune facture trouvée.");
        } else {
            System.out.println("Total factures: " + factures.size());
            factures.forEach(System.out::println);
        }
    }

    private static void rechercherParId() {
        System.out.print("\nEntrez l'ID de la facture : ");
        int id = Integer.parseInt(scanner.nextLine());
        Facture facture = controller.rechercherParId(id);
        System.out.println(facture != null ? facture : "Facture non trouvée.");
    }

    private static void rechercherParNumero() {
        System.out.print("\nEntrez le numéro de facture : ");
        String numero = scanner.nextLine();
        Facture facture = controller.rechercherParNumero(numero);
        System.out.println(facture != null ? facture : "Facture non trouvée.");
    }

    private static void modifierFacture() {
        System.out.print("\nEntrez l'ID de la facture à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());
        Facture facture = controller.rechercherParId(id);
        
        if (facture == null) {
            System.out.println("Facture non trouvée.");
            return;
        }

        System.out.println("Facture actuelle : " + facture);
        System.out.println("Laissez vide pour ne pas modifier.");

        System.out.print("Nouveau montant HT : ");
        String mtHT = scanner.nextLine();
        if (!mtHT.isEmpty()) {
            facture.setMontantHT(Double.parseDouble(mtHT));
        }

        System.out.print("Nouveau montant TTC : ");
        String mtTTC = scanner.nextLine();
        if (!mtTTC.isEmpty()) {
            facture.setMontantTTC(Double.parseDouble(mtTTC));
        }

        boolean succes = controller.modifier(facture);
        System.out.println(succes ? "Facture modifiée avec succès !" : "Échec de la modification.");
    }

    private static void listerParClient() {
        System.out.print("\nEntrez l'ID du client : ");
        int clientId = Integer.parseInt(scanner.nextLine());
        List<Facture> factures = controller.listerParClient(clientId);
        System.out.println("Factures du client ID=" + clientId + " (" + factures.size() + ") :");
        if (factures.isEmpty()) {
            System.out.println("Aucune facture trouvée pour ce client.");
        } else {
            factures.forEach(f -> System.out.println("  " + f.getNumeroFacture() + 
                " - " + f.getMontantTTC() + " DT - " + f.getStatut()));
        }
    }

    private static void listerParStatut() {
        System.out.println("\nStatuts disponibles :");
        System.out.println("1. EN_RETARD");
        System.out.println("2. EMISE");
        System.out.println("3. VALIDEE");
        System.out.println("4. PAYEE");
        System.out.println("5. ANNULEE");
        System.out.print("Choisissez le statut (1-5) : ");
        int statutChoice = Integer.parseInt(scanner.nextLine());
        
        StatutFacture statut;
        switch (statutChoice) {
            case 1: statut = StatutFacture.EN_RETARD; break;
            case 2: statut = StatutFacture.EMISE; break;
            case 3: statut = StatutFacture.ENVOYEE; break;
            case 4: statut = StatutFacture.PAYEE; break;
            case 5: statut = StatutFacture.ANNULEE; break;
            default:
                System.out.println("Statut invalide, utilisation de EMISE par défaut.");
                statut = StatutFacture.EMISE;
        }
        
        List<Facture> factures = controller.listerParStatut(statut);
        System.out.println("Factures " + statut + ": " + factures.size());
        if (!factures.isEmpty()) {
            factures.forEach(f -> System.out.println("  " + f.getNumeroFacture() + " - " + f.getMontantTTC() + " DT"));
        }
    }

    private static void rechercherParCommande() {
        System.out.print("\nEntrez l'ID de la commande : ");
        int commandeId = Integer.parseInt(scanner.nextLine());
        Facture facture = controller.rechercherParCommande(commandeId);
        if (facture != null) {
            System.out.println("Facture de la commande: " + facture.getNumeroFacture());
            System.out.println(facture);
        } else {
            System.out.println("Aucune facture trouvée pour cette commande.");
        }
    }

    private static void calculerChiffreAffaires() {
        System.out.println("\n--- CALCUL DU CHIFFRE D'AFFAIRES ---");
        
        System.out.println("1. CA du dernier mois");
        System.out.println("2. CA total");
        System.out.println("3. CA sur période personnalisée");
        System.out.print("Choisissez l'option (1-3) : ");
        int option = Integer.parseInt(scanner.nextLine());
        
        double ca = 0.0;
        Calendar cal = Calendar.getInstance();
        
        switch (option) {
            case 1:
                Date fin = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                Date debut = cal.getTime();
                ca = controller.calculerChiffreAffaires(debut, fin);
                System.out.println("CA du dernier mois: " + String.format("%.2f", ca) + " DT");
                break;
                
            case 2:
                ca = controller.calculerChiffreAffaires(new Date(0), new Date());
                System.out.println("CA total: " + String.format("%.2f", ca) + " DT");
                break;
                
            case 3:
                System.out.print("Date de début (jj/mm/aaaa) : ");
                String debutStr = scanner.nextLine();
                System.out.print("Date de fin (jj/mm/aaaa) : ");
                String finStr = scanner.nextLine();
                // Implémentation simplifiée - à adapter avec le parsing des dates
                System.out.println("Fonctionnalité à implémenter avec le parsing des dates");
                break;
                
            default:
                System.out.println("Option invalide.");
        }
    }

    private static void changerStatut() {
        System.out.print("\nEntrez l'ID de la facture : ");
        int id = Integer.parseInt(scanner.nextLine());
        Facture facture = controller.rechercherParId(id);
        
        if (facture == null) {
            System.out.println("Facture non trouvée.");
            return;
        }

        System.out.println("Statut actuel : " + facture.getStatut());
        System.out.println("Nouveaux statuts disponibles :");
        System.out.println("1. EN_RETARD");
        System.out.println("2. EMISE");
        System.out.println("3. VALIDEE");
        System.out.println("4. PAYEE");
        System.out.println("5. ANNULEE");
        System.out.print("Choisissez le nouveau statut (1-5) : ");
        int statutChoice = Integer.parseInt(scanner.nextLine());
        
        StatutFacture nouveauStatut;
        switch (statutChoice) {
            case 1: nouveauStatut = StatutFacture.EN_RETARD; break;
            case 2: nouveauStatut = StatutFacture.EMISE; break;
            case 3: nouveauStatut = StatutFacture.ENVOYEE; break;
            case 4: nouveauStatut = StatutFacture.PAYEE; break;
            case 5: nouveauStatut = StatutFacture.ANNULEE; break;
            default:
                System.out.println("Statut invalide, aucun changement effectué.");
                return;
        }
        
        facture.setStatut(nouveauStatut);
        boolean succes = controller.modifier(facture);
        System.out.println(succes ? "Statut modifié avec succès !" : "Échec de la modification.");
        System.out.println("Nouveau statut: " + controller.rechercherParId(id).getStatut());
    }

    private static void afficherStatistiques() {
        System.out.println("\n--- STATISTIQUES DES FACTURES ---");
        System.out.println("Total factures: " + controller.compter());
        System.out.println("  EN_RETARD: " + 
            controller.listerParStatut(StatutFacture.EN_RETARD).size());
        System.out.println("  EMISE: " + 
            controller.listerParStatut(StatutFacture.EMISE).size());
        System.out.println("  VALIDEE: " + 
            controller.listerParStatut(StatutFacture.ENVOYEE).size());
        System.out.println("  PAYEE: " + 
            controller.listerParStatut(StatutFacture.PAYEE).size());
        System.out.println("  ANNULEE: " + 
            controller.listerParStatut(StatutFacture.ANNULEE).size());
    }

    private static void listerFacturesImpayees() {
        System.out.println("\n--- FACTURES IMPAYÉES ---");
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
    }

    private static void supprimerFacture() {
        System.out.print("\nEntrez l'ID de la facture à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean succes = controller.supprimer(id);
        System.out.println(succes ? "Facture supprimée avec succès." : "Échec de la suppression.");
        System.out.println("Total après suppression: " + controller.compter());
    }

    private static void validerFacture() {
        System.out.print("\nEntrez l'ID de la facture à valider : ");
        int id = Integer.parseInt(scanner.nextLine());
        Facture facture = controller.rechercherParId(id);
        
        if (facture != null) {
            System.out.println("Facture valide ? " + facture.valider());
            if (!facture.valider()) {
                System.out.println("La facture n'est pas valide. Vérifiez les données (commande, client).");
            }
        } else {
            System.out.println("Facture non trouvée.");
        }
    }

    private static void afficherDelaisPaiement() {
        System.out.println("\n--- DÉLAIS DE PAIEMENT ---");
        List<Facture> factures = controller.listerTout();
        for (Facture f : factures) {
            if (f.getDateLimite() != null && f.getDateCreation() != null) {
                long diff = f.getDateLimite().getTime() - f.getDateCreation().getTime();
                long jours = diff / (1000 * 60 * 60 * 24);
                System.out.println(f.getNumeroFacture() + " - Délai: " + jours + " jours - Statut: " + f.getStatut());
            }
        }
    }
}

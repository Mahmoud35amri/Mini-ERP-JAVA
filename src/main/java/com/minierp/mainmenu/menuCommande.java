package com.minierp.mainmenu;
import com.minierp.controller.CommandeController;
import com.minierp.controller.ClientController;
import com.minierp.controller.UtilisateurController;
import com.minierp.model.Commande;
import com.minierp.model.Client;
import com.minierp.model.Utilisateur;
import com.minierp.model.StatutsEnums.StatutCommande;
import com.minierp.model.StatutsEnums.TypeCommande;
import java.util.List;
import java.util.Scanner;
public class menuCommande {
    private static final CommandeController controller = CommandeController.getInstance();
    private static final ClientController clientCtrl = ClientController.getInstance();
    private static final UtilisateurController userCtrl = UtilisateurController.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== APPLICATION DE GESTION DES COMMANDES =====\n");
        
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
        System.out.println("\n--- MENU PRINCIPAL COMMANDES ---");
        System.out.println("1. Créer une nouvelle commande");
        System.out.println("2. Lister toutes les commandes");
        System.out.println("3. Rechercher une commande par ID");
        System.out.println("4. Rechercher une commande par numéro");
        System.out.println("5. Modifier une commande");
        System.out.println("6. Lister les commandes par client");
        System.out.println("7. Lister les commandes par fournisseur");
        System.out.println("8. Lister les commandes par statut");
        System.out.println("9. Changer le statut d'une commande");
        System.out.println("10. Afficher les statistiques");
        System.out.println("11. Supprimer une commande");
        System.out.println("12. Calculer le chiffre d'affaires total");
        System.out.println("13. Valider une commande");
        System.out.println("14. Quitter");
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
                creerCommande();
                break;
            case 2:
                listerToutesCommandes();
                break;
            case 3:
                rechercherParId();
                break;
            case 4:
                rechercherParNumero();
                break;
            case 5:
                modifierCommande();
                break;
            case 6:
                listerParClient();
                break;
            case 7:
                listerParFournisseur();
                break;
            case 8:
                listerParStatut();
                break;
            case 9:
                changerStatut();
                break;
            case 10:
                afficherStatistiques();
                break;
            case 11:
                supprimerCommande();
                break;
            case 12:
                calculerCATotal();
                break;
            case 13:
                validerCommande();
                break;
            case 14:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 14.");
        }
        return true;
    }

    private static void creerCommande() {
        System.out.println("\n--- CRÉATION D'UNE NOUVELLE COMMANDE ---");
        
        // Vérification des clients existants
        List<Client> clients = clientCtrl.listerTout();
        if (clients.isEmpty()) {
            System.out.println("Aucun client disponible. Veuillez d'abord créer un client.");
            return;
        }
        
        // Vérification des utilisateurs existants
        List<Utilisateur> utilisateurs = userCtrl.listerTous();
        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur disponible. Veuillez d'abord créer un utilisateur.");
            return;
        }

        // Sélection du type de commande
        System.out.println("Types de commande disponibles :");
        System.out.println("1. VENTE");
        System.out.println("2. DEVIS");
        System.out.println("3. ACHAT");
        System.out.print("Choisissez le type (1-3) : ");
        int typeChoice = Integer.parseInt(scanner.nextLine());
        
        TypeCommande type;
        switch (typeChoice) {
            case 1: type = TypeCommande.VENTE; break;
            case 2: type = TypeCommande.DEVIS; break;
            case 3: type = TypeCommande.ACHAT; break;
            default:
                System.out.println("Type invalide, utilisation de VENTE par défaut.");
                type = TypeCommande.VENTE;
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

        // Sélection de l'utilisateur
        System.out.println("\nUtilisateurs disponibles :");
        utilisateurs.forEach(u -> System.out.println(u.getId() + ". " + u.getNom()));
        System.out.print("ID de l'utilisateur : ");
        int userId = Integer.parseInt(scanner.nextLine());
        Utilisateur utilisateur = userCtrl.rechercherParId(userId);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé !");
            return;
        }

        // Création de la commande
        Commande commande = new Commande(type, client, utilisateur);
        
        // Saisie des informations supplémentaires
        System.out.print("Montant HT (optionnel) : ");
        String mtHT = scanner.nextLine();
        if (!mtHT.isEmpty()) {
            commande.setMontantHT(Double.parseDouble(mtHT));
        }
        
        System.out.print("Montant TTC (optionnel) : ");
        String mtTTC = scanner.nextLine();
        if (!mtTTC.isEmpty()) {
            commande.setMontantTTC(Double.parseDouble(mtTTC));
        }
        

        boolean succes = controller.creer(commande);
        System.out.println(succes ? "Commande créée avec succès !" : "Échec de la création de la commande.");
    }

    private static void listerToutesCommandes() {
        System.out.println("\n--- LISTE DE TOUTES LES COMMANDES ---");
        List<Commande> commandes = controller.listerTout();
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande trouvée.");
        } else {
            System.out.println("Total commandes: " + commandes.size());
            commandes.forEach(System.out::println);
        }
    }

    private static void rechercherParId() {
        System.out.print("\nEntrez l'ID de la commande : ");
        int id = Integer.parseInt(scanner.nextLine());
        Commande commande = controller.rechercherParId(id);
        System.out.println(commande != null ? commande : "Commande non trouvée.");
    }

    private static void rechercherParNumero() {
        System.out.print("\nEntrez le numéro de commande : ");
        String numero = scanner.nextLine();
        Commande commande = controller.rechercherParNumero(numero);
        System.out.println(commande != null ? commande : "Commande non trouvée.");
    }

    private static void modifierCommande() {
        System.out.print("\nEntrez l'ID de la commande à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());
        Commande commande = controller.rechercherParId(id);
        
        if (commande == null) {
            System.out.println("Commande non trouvée.");
            return;
        }

        System.out.println("Commande actuelle : " + commande);
        System.out.println("Laissez vide pour ne pas modifier.");

        System.out.print("Nouveau montant HT : ");
        String mtHT = scanner.nextLine();
        if (!mtHT.isEmpty()) {
            commande.setMontantHT(Double.parseDouble(mtHT));
        }

        System.out.print("Nouveau montant TTC : ");
        String mtTTC = scanner.nextLine();
        if (!mtTTC.isEmpty()) {
            commande.setMontantTTC(Double.parseDouble(mtTTC));
        }


        boolean succes = controller.modifier(commande);
        System.out.println(succes ? "Commande modifiée avec succès !" : "Échec de la modification.");
    }

    private static void listerParClient() {
        System.out.print("\nEntrez l'ID du client : ");
        int clientId = Integer.parseInt(scanner.nextLine());
        List<Commande> commandes = controller.listerParClient(clientId);
        System.out.println("Commandes du client ID=" + clientId + " (" + commandes.size() + ") :");
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande trouvée pour ce client.");
        } else {
            commandes.forEach(c -> System.out.println("  " + c.getNumeroCommande() + " - " + c.getStatut()));
        }
    }

    private static void listerParFournisseur() {
        System.out.print("\nEntrez l'ID du fournisseur : ");
        int fournisseurId = Integer.parseInt(scanner.nextLine());
        List<Commande> commandes = controller.listerParFournisseur(fournisseurId);
        System.out.println("Commandes du fournisseur ID=" + fournisseurId + " : " + commandes.size());
        if (!commandes.isEmpty()) {
            commandes.forEach(c -> System.out.println("  " + c.getNumeroCommande() + " - " + c.getTypeCommande()));
        }
    }

    private static void listerParStatut() {
        System.out.println("\nStatuts disponibles :");
        System.out.println("1. EN_ATTENTE");
        System.out.println("2. CONFIRMEE");
        System.out.println("3. EN_PREPARATION");
        System.out.println("4. LIVREE");
        System.out.println("5. ANNULEE");
        System.out.print("Choisissez le statut (1-5) : ");
        int statutChoice = Integer.parseInt(scanner.nextLine());
        
        StatutCommande statut;
        switch (statutChoice) {
            case 1: statut = StatutCommande.EN_ATTENTE; break;
            case 2: statut = StatutCommande.CONFIRMEE; break;
            case 3: statut = StatutCommande.EN_PREPARATION; break;
            case 4: statut = StatutCommande.LIVREE; break;
            case 5: statut = StatutCommande.ANNULEE; break;
            default:
                System.out.println("Statut invalide, utilisation de EN_ATTENTE par défaut.");
                statut = StatutCommande.EN_ATTENTE;
        }
        
        List<Commande> commandes = controller.listerParStatut(statut);
        System.out.println("Commandes " + statut + ": " + commandes.size());
        if (!commandes.isEmpty()) {
            commandes.forEach(c -> System.out.println("  " + c.getNumeroCommande()));
        }
    }

    private static void changerStatut() {
        System.out.print("\nEntrez l'ID de la commande : ");
        int id = Integer.parseInt(scanner.nextLine());
        Commande commande = controller.rechercherParId(id);
        
        if (commande == null) {
            System.out.println("Commande non trouvée.");
            return;
        }

        System.out.println("Statut actuel : " + commande.getStatut());
        System.out.println("Nouveaux statuts disponibles :");
        System.out.println("1. EN_ATTENTE");
        System.out.println("2. CONFIRMEE");
        System.out.println("3. EN_PREPARATION");
        System.out.println("4. LIVREE");
        System.out.println("5. ANNULEE");
        System.out.print("Choisissez le nouveau statut (1-5) : ");
        int statutChoice = Integer.parseInt(scanner.nextLine());
        
        StatutCommande nouveauStatut;
        switch (statutChoice) {
            case 1: nouveauStatut = StatutCommande.EN_ATTENTE; break;
            case 2: nouveauStatut = StatutCommande.CONFIRMEE; break;
            case 3: nouveauStatut = StatutCommande.EN_PREPARATION; break;
            case 4: nouveauStatut = StatutCommande.LIVREE; break;
            case 5: nouveauStatut = StatutCommande.ANNULEE; break;
            default:
                System.out.println("Statut invalide, aucun changement effectué.");
                return;
        }
        
        commande.setStatut(nouveauStatut);
        boolean succes = controller.modifier(commande);
        System.out.println(succes ? "Statut modifié avec succès !" : "Échec de la modification.");
        System.out.println("Nouveau statut: " + controller.rechercherParId(id).getStatut());
    }

    private static void afficherStatistiques() {
        System.out.println("\n--- STATISTIQUES DES COMMANDES ---");
        List<Commande> toutesCommandes = controller.listerTout();
        System.out.println("Total commandes: " + toutesCommandes.size());
        System.out.println("Commandes EN_ATTENTE: " + 
            controller.listerParStatut(StatutCommande.EN_ATTENTE).size());
        System.out.println("Commandes CONFIRMEE: " + 
            controller.listerParStatut(StatutCommande.CONFIRMEE).size());
        System.out.println("Commandes EN_PREPARATION: " + 
            controller.listerParStatut(StatutCommande.EN_PREPARATION).size());
        System.out.println("Commandes LIVREE: " + 
            controller.listerParStatut(StatutCommande.LIVREE).size());
        System.out.println("Commandes ANNULEE: " + 
            controller.listerParStatut(StatutCommande.ANNULEE).size());
        
        // Statistiques par type
        System.out.println("\nPar type :");
        System.out.println("Commandes VENTE: " + 
            controller.listerTout().stream().filter(c -> c.getTypeCommande() == TypeCommande.VENTE).count());
        System.out.println("Commandes DEVIS: " + 
            controller.listerTout().stream().filter(c -> c.getTypeCommande() == TypeCommande.DEVIS).count());
        System.out.println("Commandes ACHAT: " + 
            controller.listerTout().stream().filter(c -> c.getTypeCommande() == TypeCommande.ACHAT).count());
    }

    private static void supprimerCommande() {
        System.out.print("\nEntrez l'ID de la commande à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean succes = controller.supprimer(id);
        System.out.println(succes ? "Commande supprimée avec succès." : "Échec de la suppression.");
        System.out.println("Total après suppression: " + controller.listerTout().size());
    }

    private static void calculerCATotal() {
        System.out.println("\n--- CALCUL DU CHIFFRE D'AFFAIRES ---");
        double totalCA = controller.listerTout().stream()
            .mapToDouble(Commande::getMontantTTC)
            .sum();
        System.out.println("CA total des commandes: " + totalCA + " DT");
        
        // CA par type de commande
        double caVentes = controller.listerTout().stream()
            .filter(c -> c.getTypeCommande() == TypeCommande.VENTE)
            .mapToDouble(Commande::getMontantTTC)
            .sum();
        System.out.println("CA des ventes: " + caVentes + " DT");
    }

    private static void validerCommande() {
        System.out.print("\nEntrez l'ID de la commande à valider : ");
        int id = Integer.parseInt(scanner.nextLine());
        Commande commande = controller.rechercherParId(id);
        
        if (commande != null) {
            System.out.println("Commande valide ? " + commande.valider());
            if (!commande.valider()) {
                System.out.println("La commande n'est pas valide. Vérifiez les données (client, utilisateur, type).");
            }
        } else {
            System.out.println("Commande non trouvée.");
        }
    }
}

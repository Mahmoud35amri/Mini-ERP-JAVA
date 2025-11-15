package com.minierp.mainmenu;
import com.minierp.controller.ClientController;
import com.minierp.model.Client;
import com.minierp.model.Client.TypeClient;
import static com.minierp.util.InputUtils.lireString;
import static com.minierp.util.InputUtils.lireDouble;
import static com.minierp.util.InputUtils.lireInt;
import java.util.List;
import java.util.Scanner;


public class menuClient {
    

    private static final ClientController controller = ClientController.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===== APPLICATION DE GESTION DES CLIENTS =====\n");
        
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
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Créer un nouveau client");
        System.out.println("2. Lister tous les clients");
        System.out.println("3. Rechercher un client par ID");
        System.out.println("4. Rechercher un client par code");
        System.out.println("5. Rechercher un client par nom");
        System.out.println("6. Rechercher un client par email");
        System.out.println("7. Modifier un client");
        System.out.println("8. Lister les clients actifs");
        System.out.println("9. Lister les clients par type");
        System.out.println("10. Lister les clients par catégorie");
        System.out.println("11. Lister les meilleurs clients");
        System.out.println("12. Calculer le chiffre d'affaires d'un client");
        System.out.println("13. Désactiver un client");
        System.out.println("14. Supprimer un client");
        System.out.println("15. Valider un client");
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
                creerClient();
                break;
            case 2:
                listerTousClients();
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
                rechercherParEmail();
                break;
            case 7:
                modifierClient();
                break;
            case 8:
                listerClientsActifs();
                break;
            case 9:
                listerParType();
                break;
            case 10:
                listerParCategorie();
                break;
            case 11:
                listerMeilleursClients();
                break;
            case 12:
                calculerChiffreAffaires();
                break;
            case 13:
                desactiverClient();
                break;
            case 14:
                supprimerClient();
                break;
            case 15:
                validerClient();
                break;
            case 16:
                return false;
            default:
                System.out.println("Choix invalide ! Veuillez choisir entre 1 et 16.");
        }
        return true;
    }

    private static void creerClient() {
        System.out.println("\n--- CRÉATION D'UN NOUVEAU CLIENT ---");
        
        System.out.print("Type de client (1-PARTICULIER, 2-ENTREPRISE) : ");
        int typeChoice = Integer.parseInt(scanner.nextLine());
        TypeClient type = (typeChoice == 2) ? TypeClient.ENTREPRISE : TypeClient.PARTICULIER;

        if (type == TypeClient.PARTICULIER) {
            String nom = lireString("Nom : ");
            String prenom =  lireString("Prénom : ");
            String email = lireString("Email : ");
            Client client = new Client(nom, prenom, email);
            boolean succes = controller.creer(client);
            System.out.println(succes ? "Client créé avec succès !" : "Échec de la création du client.");
            
        } else {
            String civilite = lireString("Civilité (M., Mme, etc.) : ");
            String nom = lireString("Nom : ");
            String prenom = lireString("Prénom : ");
            String nomEntreprise = lireString("Nom de l'entreprise : ");
            String adresse = lireString("Adresse : ");
            String ville = lireString("Ville : ");
            String codePostal = lireString("Code postal : ");
            String pays = lireString("Pays : ");
            String telephone = lireString("Téléphone : ");
            String email = lireString("Email : ");
            double plafondCredit = lireDouble("Plafond de crédit : ");

            Client client = new Client(type, civilite, nom, prenom, nomEntreprise, 
                                     adresse, ville, codePostal, pays, telephone, email, plafondCredit);
            boolean succes = controller.creer(client);
            System.out.println(succes ? "Client entreprise créé avec succès !" : "Échec de la création du client.");
        }
    }

    private static void listerTousClients() {
        System.out.println("\n--- LISTE DE TOUS LES CLIENTS ---");
        List<Client> clients = controller.listerTout();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            clients.forEach(System.out::println);
        }
    }

    private static void rechercherParId() {
        int id = lireInt("\nEntrez l'ID d'un client : ");
        Client client = controller.rechercherParId(id);
        System.out.println(client != null ? client : "Client non trouvé.");
    }

    private static void rechercherParCode() {
        String code = lireString("\nEntrez le code du client : ");
        Client client = controller.rechercherParCode(code);
        System.out.println(client != null ? client : "Client non trouvé.");
    }

    private static void rechercherParNom() {
        String nom = lireString("\nEntrez le nom à rechercher : ");
        List<Client> clients = controller.rechercherParNom(nom);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé avec ce nom.");
        } else {
            clients.forEach(System.out::println);
        }
    }

    private static void rechercherParEmail() {
        System.out.print("\nEntrez l'email à rechercher : ");
        String email = scanner.nextLine();
        Client client = controller.rechercherParEmail(email);
        System.out.println(client != null ? client : "Client non trouvé.");
    }

    private static void modifierClient() {
        int id = lireInt("\nEntrez l'ID du client à modifier : ");
        Client client = controller.rechercherParId(id);
        
        if (client == null) {
            System.out.println("Client non trouvé.");
            return;
        }

        System.out.println("Client actuel : " + client);
        System.out.println("Laissez vide pour ne pas modifier.");

        System.out.print("Nouveau téléphone : ");
        String tel = scanner.nextLine();
        if (!tel.isEmpty()) client.setTelephone(tel);

        System.out.print("Nouvelle adresse : ");
        String adresse = scanner.nextLine();
        if (!adresse.isEmpty()) client.setAdresse(adresse);

        System.out.print("Nouvel email : ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) client.setEmail(email);

        boolean succes = controller.modifier(client);
        System.out.println(succes ? "Client modifié avec succès !" : "Échec de la modification.");
    }

    private static void listerClientsActifs() {
        System.out.println("\n--- CLIENTS ACTIFS ---");
        List<Client> actifs = controller.listerActifs();
        if (actifs.isEmpty()) {
            System.out.println("Aucun client actif.");
        } else {
            actifs.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom() + " - " + c.getEmail()));
        }
    }

    private static void listerParType() {
        System.out.print("\nType de client (1-PARTICULIER, 2-ENTREPRISE) : ");
        int typeChoice = Integer.parseInt(scanner.nextLine());
        TypeClient type = (typeChoice == 2) ? TypeClient.ENTREPRISE : TypeClient.PARTICULIER;
        
        List<Client> clients = controller.listerParType(type);
        System.out.println("Clients " + type + " (" + clients.size() + ") :");
        clients.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom()));
    }

    private static void listerParCategorie() {
        System.out.print("\nCatégorie à rechercher : ");
        String categorie = scanner.nextLine();
        List<Client> clients = controller.listerParCategorie(categorie);
        System.out.println("Clients " + categorie + " (" + clients.size() + ") :");
        clients.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom()));
    }

    private static void listerMeilleursClients() {
        System.out.print("\nNombre de meilleurs clients à afficher : ");
        int nombre = Integer.parseInt(scanner.nextLine());
        List<Client> meilleurs = controller.listerMeilleurs(nombre);
        System.out.println("Top " + nombre + " meilleurs clients :");
        meilleurs.forEach(c -> System.out.println(c.getNom() + " - CA: " + 
            controller.calculerChiffreAffaires(c.getId()) + " DT"));
    }

    private static void calculerChiffreAffaires() {
        System.out.print("\nEntrez l'ID du client : ");
        int id = Integer.parseInt(scanner.nextLine());
        double ca = controller.calculerChiffreAffaires(id);
        System.out.println("Chiffre d'affaires : " + ca + " DT");
    }

    private static void desactiverClient() {
        System.out.print("\nEntrez l'ID du client à désactiver : ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = controller.rechercherParId(id);
        
        if (client != null) {
            client.setActif(false);
            controller.modifier(client);
            System.out.println("Client désactivé avec succès.");
        } else {
            System.out.println("Client non trouvé.");
        }
    }

    private static void supprimerClient() {
        System.out.print("\nEntrez l'ID du client à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean succes = controller.supprimer(id);
        System.out.println(succes ? "Client supprimé avec succès." : "Échec de la suppression.");
    }

    private static void validerClient() {
        System.out.print("\nEntrez l'ID du client à valider : ");
        int id = Integer.parseInt(scanner.nextLine());
        Client client = controller.rechercherParId(id);
        
        if (client != null) {
            System.out.println("Client valide ? " + client.valider());
        } else {
            System.out.println("Client non trouvé.");
        }
    }
}

package com.minierp;

import java.util.List;

import com.minierp.controller.EntrepriseController;
import com.minierp.model.Entreprise;

public class TestFonctions {

    public static void main(String[] args) {

        EntrepriseController controller = EntrepriseController.getInstance();

        System.out.println("===== TEST ERP ENTREPRISE =====");

        // ---------------------------------------------
        // 1. TEST CREATION
        // ---------------------------------------------
        Entreprise e1 = new Entreprise("TechCorp", "SARL TechCorp", "contact@tech.com", "Tunisie");
        Entreprise e2 = new Entreprise("SoftPlus", "SA SoftPlus", "info@softplus.com", "France");
        Entreprise e3 = new Entreprise("MiniERP", "SAS MiniERP", "admin@erp.com", "Tunisie");

        controller.creer(e1);
        controller.creer(e2);
        controller.creer(e3);

        System.out.println("\n--- Après création ---");
        System.out.println("Total = " + controller.compter());
        controller.listerTout().forEach(System.out::println);


        // ---------------------------------------------
        // 2. TEST RECHERCHE PAR ID
        // ---------------------------------------------
        System.out.println("\n--- Test rechercherParId(2) ---");
        System.out.println(controller.rechercherParId(2));


        // ---------------------------------------------
        // 3. TEST RECHERCHE PAR NOM
        // ---------------------------------------------
        System.out.println("\n--- Test rechercherParNom(\"Tech\") ---");
        List<Entreprise> listeNom = controller.rechercherParNom("Tech");
        listeNom.forEach(System.out::println);


        // ---------------------------------------------
        // 4. TEST MODIFICATION
        // ---------------------------------------------
        System.out.println("\n--- Test modifier entreprise id=1 ---");
        e1.setEmail("support@techcorp.com");
        e1.setNom("TechCorp International");

        boolean modifOK = controller.modifier(e1);
        System.out.println("Modification effectuée ? " + modifOK);

        System.out.println("Après modification : ");
        System.out.println(controller.rechercherParId(1));


        // ---------------------------------------------
        // 5. TEST LISTE DES ENTREPRISES ACTIVES
        // ---------------------------------------------
        System.out.println("\n--- Test listerActives ---");
        controller.listerActives().forEach(System.out::println);


        // ---------------------------------------------
        // 6. TEST SUPPRESSION
        // ---------------------------------------------
        System.out.println("\n--- Test supprimer id=2 ---");
        boolean supprimeOK = controller.supprimer(2);
        System.out.println("Suppression effectuée ? " + supprimeOK);

        System.out.println("\n--- Après suppression ---");
        controller.listerTout().forEach(System.out::println);
        System.out.println("Total = " + controller.compter());


        // ---------------------------------------------
        // 7. TEST RECHERCHE D’UNE ENTREPRISE INEXISTANTE
        // ---------------------------------------------
        System.out.println("\n--- Test rechercherParId(999) ---");
        Entreprise notFound = controller.rechercherParId(999);
        System.out.println(notFound == null ? "Entreprise non trouvée." : notFound);


        // ---------------------------------------------
        // 8. TEST DESACTIVATION ET LISTER ACTIVES
        // ---------------------------------------------
        System.out.println("\n--- Test désactivation d’une entreprise ---");
        e3.setActif(false);
        controller.modifier(e3);

        System.out.println("Entreprises actives après désactivation : ");
        controller.listerActives().forEach(System.out::println);


        // ---------------------------------------------
        // 9. TEST RECHERCHE PAR NOM (aucun résultat)
        // ---------------------------------------------
        System.out.println("\n--- Test rechercherParNom(\"XYZ\") ---");
        List<Entreprise> emptyList = controller.rechercherParNom("XYZ");
        System.out.println(emptyList.isEmpty() ? "Aucun résultat." : emptyList);


        // ---------------------------------------------
        // 10. TEST COMPLETE INFORMATION
        // ---------------------------------------------
        System.out.println("\n--- Test getInformationsCompletes() pour e1 ---");
        //System.out.println(e1.getInformationsCompletes());


        // ---------------------------------------------
        // 11. TEST VALEUR DE VALIDATION (méthode valider)
        // ---------------------------------------------
        System.out.println("\n--- Test valider() ---");
        System.out.println("e1 valide ? " + e1.valider());

        Entreprise invalid = new Entreprise("", "", "invalid", "Tunisie");
        System.out.println("Entreprise invalide valide ? " + invalid.valider());


        // ---------------------------------------------
        // FIN
        // ---------------------------------------------
        System.out.println("\n===== FIN DES TESTS =====");
    }
}

package com.minierp.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int lireInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Veuillez entrer un nombre entier valide !");
            }
        }
    }

    // Méthode pour lire une chaîne obligatoire
    public static String lireString(String message) {
        String s;
        do {
            System.out.print(message);
            s = scanner.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println(" Ce champ est obligatoire !");
            }
        } while (s.isEmpty());
        return s;
    }
    
    public static String lireStringOptionnel(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }

    // Méthode pour lire un double
    public static double lireDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(" Veuillez entrer un nombre décimal valide !");
            }
        }
    }

    // Méthode pour lire un entier dans une plage spécifique
    public static int lireIntDansPlage(String message, int min, int max) {
        while (true) {
            int valeur = lireInt(message);
            if (valeur >= min && valeur <= max) {
                return valeur;
            }
            System.out.println(" Veuillez entrer un nombre entre " + min + " et " + max + " !");
        }
    }

    // Méthode pour confirmer une action (O/N)
    public static boolean confirmer(String message) {
        while (true) {
            System.out.print(message + " (O/N) : ");
            String reponse = scanner.nextLine().trim().toUpperCase();
            if (reponse.equals("O") || reponse.equals("OUI")) {
                return true;
            } else if (reponse.equals("N") || reponse.equals("NON")) {
                return false;
            }
            System.out.println(" Veuillez répondre par O(ui) ou N(on) !");
        }
    }
}
package com.minierp.controller;

import com.minierp.model.Client;
import com.minierp.model.Produit;
import com.minierp.model.StatutsEnums.StatutCommande;
import com.minierp.model.StatutsEnums.StatutFacture;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StatistiqueController {
    private static StatistiqueController instance;
    private CommandeController commandeController = CommandeController.getInstance();
    private FactureController factureController = FactureController.getInstance();
    private ClientController clientController = ClientController.getInstance();
    private ProduitController produitController = ProduitController.getInstance();

    private StatistiqueController() {}

    public static StatistiqueController getInstance() {
        if (instance == null) instance = new StatistiqueController();
        return instance;
    }

    // Somme des montants des factures comprises entre debut et fin (inclus)
    public double getChiffreAffaires(Date debut, Date fin) {
        if (debut == null || fin == null) return 0.0;
        return factureController.listerTout().stream()
                .filter(f -> {
                    Date d = f.getDateCreation();
                    return d != null && !d.before(debut) && !d.after(fin);
                })
                .mapToDouble(f -> f.getMontantTTC() != 0 ? f.getMontantTTC() : 0.0)
                .sum();
    }

    // Retourne un map mois -> CA pour l'année donnée (mois 1..12)
    public Map<Integer, Double> getChiffreAffairesParMois(int annee) {
        Map<Integer, Double> res = new LinkedHashMap<>();
        for (int m = 1; m <= 12; m++) res.put(m, 0.0);

        Calendar cal = Calendar.getInstance();
        for (var f : factureController.listerTout()) {
            Date d = f.getDateCreation();
            if (d == null) continue;
            cal.setTime(d);
            int an = cal.get(Calendar.YEAR);
            if (an != annee) continue;
            int mois = cal.get(Calendar.MONTH) + 1;
            double montant = f.getMontantTTC() != 0 ? f.getMontantTTC() : 0.0;
            res.put(mois, res.getOrDefault(mois, 0.0) + montant);
        }
        return res;
    }

    // Top CA par client, retourne au plus 'limite' clients triés décroissant par CA
    public Map<Client, Double> getChiffreAffairesParClient(int limite) {
        Map<Client, Double> sums = new HashMap<>();
        for (var f : factureController.listerTout()) {
            Client c = f.getClient();
            if (c == null) continue;
            double montant = f.getMontantTTC() != 0 ? f.getMontantTTC() : 0.0;
            sums.put(c, sums.getOrDefault(c, 0.0) + montant);
        }
        return sums.entrySet().stream()
                .sorted(Map.Entry.<Client, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(Math.max(0, limite))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b)->a,
                        LinkedHashMap::new
                ));
    }

    // Evolution des ventes entre deux dates selon interval: "daily","weekly","monthly"
    public Map<Date, Double> getEvolutionVentes(Date debut, Date fin, String interval) {
        Map<Date, Double> res = new TreeMap<>();
        if (debut == null || fin == null || debut.after(fin)) return res;

        Calendar cur = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        cur.setTime(debut);
        end.setTime(fin);

        // normaliser au début du jour
        stripTime(cur);
        stripTime(end);

        while (!cur.after(end)) {
            res.put(cur.getTime(), 0.0);
            if ("weekly".equalsIgnoreCase(interval)) cur.add(Calendar.WEEK_OF_YEAR, 1);
            else if ("monthly".equalsIgnoreCase(interval)) cur.add(Calendar.MONTH, 1);
            else cur.add(Calendar.DATE, 1); // daily par défaut
        }

        for (var f : factureController.listerTout()) {
            Date d = f.getDateCreation();
            if (d == null) continue;
            if (d.before(debut) || d.after(fin)) continue;
            Date bucket = bucketForDate(d, interval);
            // trouver la clé exacte (la première <= d dans res)
            Date key = res.keySet().stream()
                    .filter(k -> !k.after(bucket))
                    .reduce((first, second) -> second)
                    .orElse(null);
            if (key == null) key = bucket;
            double montant = f.getMontantTTC() != 0 ? f.getMontantTTC() : 0.0;
            res.put(key, res.getOrDefault(key, 0.0) + montant);
        }

        return res;
    }

    private static void stripTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    private static Date bucketForDate(Date d, String interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        stripTime(cal);
        if ("monthly".equalsIgnoreCase(interval)) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } else if ("weekly".equalsIgnoreCase(interval)) {
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            stripTime(cal);
        }
        return cal.getTime();
    }

    // Count commandes par statut
    public Map<StatutCommande, Integer> getNombreCommandesParStatut() {
        Map<StatutCommande, Integer> res = new EnumMap<>(StatutCommande.class);
        for (var c : commandeController.listerTout()) {
            StatutCommande s = c.getStatut();
            res.put(s, res.getOrDefault(s, 0) + 1);
        }
        return res;
    }

    // Count factures par statut
    public Map<StatutFacture, Integer> getNombreFacturesParStatut() {
        Map<StatutFacture, Integer> res = new EnumMap<>(StatutFacture.class);
        for (var f : factureController.listerTout()) {
            StatutFacture s = f.getStatut();
            res.put(s, res.getOrDefault(s, 0) + 1);
        }
        return res;
    }

    // Top produits vendus par quantité
    public List<Produit> getTopProduits(int limite) {
        Map<Produit, Integer> sold = new HashMap<>();
        for (var cmd : commandeController.listerTout()) {
            if (cmd.getLignes() == null) continue;
            for (var ligne : cmd.getLignes()) {
                Produit p = ligne.getProduit();
                int q = ligne.getQuantite() != 0 ? ligne.getQuantite() : 0;
                sold.put(p, sold.getOrDefault(p, 0) + q);
            }
        }
        return sold.entrySet().stream()
                .sorted(Map.Entry.<Produit,Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(Math.max(0, limite))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Top clients par CA
    public List<Client> getTopClients(int limite) {
        Map<Client, Double> ca = getChiffreAffairesParClient(Integer.MAX_VALUE);
        return ca.entrySet().stream()
                .limit(Math.max(0, limite))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Taux de conversion = commandes / visites -> ici approximé commandes validées / total commandes
    public double getTauxConversion() {
        long total = commandeController.listerTout().size();
        if (total == 0) return 0.0;
        long valides = commandeController.listerTout().stream()
                .filter(c -> c.getStatut() != null && c.getStatut().toString().toLowerCase().contains("valide"))
                .count();
        return (double) valides / total * 100.0;
    }

    // Panier moyen = CA total / nb commandes payées
    public double getPanierMoyen() {
        double ca = getChiffreAffaires(new Date(0), new Date());
        long nb = commandeController.listerTout().stream()
                .filter(c -> c.getMontantTTC() != 0 && c.getMontantTTC() > 0)
                .count();
        if (nb == 0) return 0.0;
        return ca / nb;
    }
}
/* 
    // Taux de retour approximé = nb lignes retournées / nb lignes vendues
    public double getTauxRetour() {
        long vendues = 0;
        long retour = 0;
        for (var cmd : commandeController.listerTout()) {
            if (cmd.getLignes() == null) continue;
            for (var l : cmd.getLignes()) {
                int q = l.getQuantite() != 0 ? l.getQuantite() : 0;
                vendues += q;
                if (l.isRetour() != null && l.isRetour()) retour += q;
            }
        }
        if (vendues == 0) return 0.0;
        return (double) retour / vendues * 100.0;
    }

    // Taux de recouvrement = montant encaissé / montant facturé
    public double getTauxRecouvrement() {
        double factures = factureController.listerTout().stream()
                .mapToDouble(f -> f.getTotal() != null ? f.getTotal() : 0.0)
                .sum();
        double encaisses = factureController.listerTout().stream()
                .mapToDouble(f -> f.getEncaissement() != null ? f.getEncaissement() : 0.0)
                .sum();
        if (factures == 0) return 0.0;
        return encaisses / factures * 100.0;
    }

    // Délai moyen de paiement en jours (moyenne différence date paiement - date facture)
    public int getDelaiPaiementMoyen() {
        long totalJours = 0;
        int count = 0;
        for (var f : factureController.listerTout()) {
            Date dFact = f.getDate();
            Date dPay = f.getDatePaiement();
            if (dFact == null || dPay == null) continue;
            long diff = dPay.getTime() - dFact.getTime();
            totalJours += TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            count++;
        }
        if (count == 0) return 0;
        return (int) (totalJours / count);
    }

    // Marge globale approximative = (CA - coût des ventes) / CA * 100
    public double getMargeGlobale() {
        double ca = factureController.listerTout().stream()
                .mapToDouble(f -> f.getTotal() != null ? f.getTotal() : 0.0)
                .sum();
        double couts = commandeController.listerTout().stream()
                .flatMap(cmd -> cmd.getLignes() == null ? Collections.<Object>emptyList().stream() : cmd.getLignes().stream())
                .mapToDouble(l -> {
                    Double pu = l.getPrixUnitaire() != null ? l.getPrixUnitaire() : 0.0;
                    int q = l.getQuantite() != null ? l.getQuantite() : 0;
                    double coutUnitaire = l.getCoutUnitaire() != null ? l.getCoutUnitaire() : 0.0;
                    return q * coutUnitaire;
                })
                .sum();
        if (ca == 0) return 0.0;
        return (ca - couts) / ca * 100.0;
    }

    // Rotation de stock = cout des ventes / stock moyen -> approximé par quantité vendue / nb produits
    public double getRotationStock() {
        double quantiteVendue = commandeController.listerTout().stream()
                .flatMap(cmd -> cmd.getLignes() == null ? Collections.<Object>emptyList().stream() : cmd.getLignes().stream())
                .mapToDouble(l -> l.getQuantite() != null ? l.getQuantite() : 0)
                .sum();
        int stockItems = produitController.listerTout().size();
        if (stockItems == 0) return 0.0;
        return quantiteVendue / stockItems;
    }

    // Taux de rupture = nb produits en rupture / nb produits total
    public double getTauxRuptureStock() {
        long ruptures = produitController.listerTout().stream()
                .filter(p -> p.getQuantiteStock() != null && p.getQuantiteStock() <= 0)
                .count();
        long total = produitController.listerTout().size();
        if (total == 0) return 0.0;
        return (double) ruptures / total * 100.0;
    }

    // Nouveaux clients pour un mois donné
    public int getNouveauxClients(int mois, int annee) {
        Calendar cal = Calendar.getInstance();
        int count = 0;
        for (var c : clientController.listerTout()) {
            Date d = c.getDateCreation();
            if (d == null) continue;
            cal.setTime(d);
            int m = cal.get(Calendar.MONTH) + 1;
            int a = cal.get(Calendar.YEAR);
            if (m == mois && a == annee) count++;
        }
        return count;
    }

    public int getClientsActifs() {
        // clients ayant au moins une commande dans les 12 derniers mois
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -12);
        Date since = cal.getTime();
        Set<Client> actifs = new HashSet<>();
        for (var cmd : commandeController.listerTout()) {
            Date d = cmd.getDate();
            if (d == null || d.before(since)) continue;
            Client c = cmd.getClient();
            if (c != null) actifs.add(c);
        }
        return actifs.size();
    }

    public int getClientsInactifs(int joursInactivite) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -Math.abs(joursInactivite));
        Date cutoff = cal.getTime();
        Set<Client> inactifs = new HashSet<>(clientController.listerTout());
        for (var cmd : commandeController.listerTout()) {
            if (cmd.getDate() != null && !cmd.getDate().before(cutoff)) {
                inactifs.remove(cmd.getClient());
            }
        }
        return inactifs.size();
    }

    public String genererRapportVentes(Date debut, Date fin) {
        double ca = getChiffreAffaires(debut, fin);
        Map<Integer, Double> mois = getChiffreAffairesParMois(Calendar.getInstance().get(Calendar.YEAR));
        return String.format("Rapport ventes [%s - %s] : CA=%.2f, détails mois=%s", debut, fin, ca, mois);
    }

    public String genererRapportClients() {
        int total = clientController.listerTout().size();
        int actifs = getClientsActifs();
        return String.format("Rapport clients : total=%d, actifs=%d", total, actifs);
    }

    public String genererRapportProduits() {
        int total = produitController.listerTout().size();
        double tauxRupture = getTauxRuptureStock();
        return String.format("Rapport produits : total=%d, tauxRupture=%.2f%%", total, tauxRupture);
    }

    public String genererRapportFinancier(int mois, int annee) {
        Map<Integer, Double> moisMap = getChiffreAffairesParMois(annee);
        Double ca = moisMap.getOrDefault(mois, 0.0);
        double marge = getMargeGlobale();
        return String.format("Rapport financier %02d/%d : CA=%.2f, marge=%.2f%%", mois, annee, ca, marge);
    }
}*/

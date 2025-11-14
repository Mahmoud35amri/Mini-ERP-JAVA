package com.minierp.controller;

import com.minierp.dao.CommandeDao;
import com.minierp.model.Commande;
import com.minierp.model.StatutsEnums.StatutCommande;

import java.util.List;

public class CommandeController {
    private static CommandeController instance;
    private final CommandeDao commandeDao = new CommandeDao();

    private CommandeController() {}

    public static CommandeController getInstance() {
        if (instance == null) instance = new CommandeController();
        return instance;
    }

    public boolean creer(Commande c) { return commandeDao.creer(c); }
    public boolean modifier(Commande c) { return commandeDao.modifier(c); }
    public boolean supprimer(int id) { return commandeDao.supprimer(id); }
    public Commande rechercherParId(int id) { return commandeDao.rechercherParId(id); }
    public Commande rechercherParNumero(String numero) { return commandeDao.rechercherParNum(numero); }
    public List<Commande> listerTout() { return commandeDao.listerTout(); }
    public List<Commande> listerParClient(int idClient) { return commandeDao.listerParClient(idClient); }
    public List<Commande> listerParFournisseur(int idFournisseur) { return commandeDao.listerParFournisseur(idFournisseur); }
    public List<Commande> listerParStatut(StatutCommande statut) { return commandeDao.listerParStatut(statut); }
    /*public boolean valider(int id) { return false; }
    public boolean annuler(int id, String motif) { return false; }
    public boolean livrer(int id) { return false; }
    public boolean changerStatut(int id, StatutCommande statut) { return false; }
    public boolean verifierStock(int id) { return false; }
    public double calculerChiffreAffaires(Date debut, Date fin) { return 0; }
    public double calculerChiffreAffairesParClient(int idClient) { return 0; }
    public List<Commande> getCommandesDuJour() { return new ArrayList<>(); }
    public List<Commande> getCommandesEnRetard() { return new ArrayList<>(); }
    public int compter() { return commandeDao.getAll().size(); }
    public int compterParStatut(StatutCommande statut) { return 0; }*/
}

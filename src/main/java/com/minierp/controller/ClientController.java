package com.minierp.controller;

import com.minierp.dao.ClientDao;
import com.minierp.model.Client;

import java.util.List;

public class ClientController {

    private static ClientController instance;
    private final ClientDao clientDao;

    private ClientController() {
        clientDao = new ClientDao();
    }

    public static synchronized ClientController getInstance() {
        if (instance == null)
            instance = new ClientController();
        return instance;
    }

    public boolean creer(Client c) {
        return clientDao.creer(c);
    }

    public boolean modifier(Client c) {
        return clientDao.modifier(c);
    }

    public boolean supprimer(int id) {
        return clientDao.supprimer(id);
    }

    public Client rechercherParId(int id) {
        return clientDao.rechercherParId(id);
    }

    public Client rechercherParCode(String code) {
        return clientDao.rechercherParCode(code);
    }

    public List<Client> rechercherParNom(String nom) {
        return clientDao.rechercherParNom(nom);
    }

    public Client rechercherParEmail(String email) {
        return clientDao.rechercherParEmail(email);
    }

    public List<Client> listerTout() {
        return clientDao.listerTout();
    }

    public List<Client> listerActifs() {
        return clientDao.listerActifs();
    }

    public List<Client> listerParType(Client.TypeClient type) {
        return clientDao.listerParType(type);
    }

    public List<Client> listerParCategorie(String categorie) {
        return clientDao.listerParCategorie(categorie);
    }

    public List<Client> listerMeilleurs(int limite) {
        return clientDao.listerMeilleurs(limite);
    }

    public double calculerChiffreAffaires(int id) {
        return clientDao.calculerChiffreAffaires(id);
    }

    public int compter() {
        return clientDao.compter();
    }

}

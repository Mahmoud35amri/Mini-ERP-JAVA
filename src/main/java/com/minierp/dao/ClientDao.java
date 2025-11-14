package com.minierp.dao;

import com.minierp.model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientDao {
    private final List<Client> clients = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);
    

    public boolean creer(Client c) {
        c.setId(idCounter.incrementAndGet());
        clients.add(c);
        return true;
    }

    public boolean modifier(Client c) {
        if (c == null) return false;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == c.getId()) {
                clients.set(i, c);
                return true;
            }
        }
        return false;
    }

    public boolean supprimer(int id) {
        Client c = rechercherParId(id);
        if (c != null) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    public Client rechercherParId(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }
    public Client rechercherParCode(String code) {
        return clients.stream().filter(c -> c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }

    public List<Client> rechercherParNom(String nom) {
        return clients.stream().filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase())).toList();
    }

    public Client rechercherParEmail(String email) {
        return clients.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public List<Client> listerTout() {
        return new ArrayList<>(clients);
    }

    public List<Client> listerActifs() {
        return clients.stream().filter(Client::isActif).toList();
    }

    public List<Client> listerParType(Client.TypeClient type) {
        return clients.stream().filter(c -> c.getType() == type).toList();
    }

    public List<Client> listerParCategorie(String categorie) {
        return clients.stream().filter(c -> c.getCategorie().toString().equalsIgnoreCase(categorie)).toList();
    }

    public List<Client> listerMeilleurs(int limite) {
        // Simplifié: pour l'instant, retourner les premiers N clients actifs
        return clients.stream().filter(Client::isActif).limit(limite).toList();
    }

    public double calculerChiffreAffaires(int id) {
        // Pour l’instant simulé
        return 0.0;
    }

    public int compter() {
        return clients.size();
    }

}

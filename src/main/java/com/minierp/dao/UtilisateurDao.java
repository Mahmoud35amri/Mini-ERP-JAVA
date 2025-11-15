package com.minierp.dao;

import com.minierp.model.Utilisateur;
import com.minierp.model.Utilisateur.Role;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



public class UtilisateurDao {

    private final List<Utilisateur> utilisateurs = new ArrayList<>();
    private final AtomicInteger idCounte = new AtomicInteger(0);
    private final List<Utilisateur> utilisateurConnecte = new ArrayList<>();

    public boolean creer(Utilisateur utilisateur) {
        if (utilisateur == null) return false;
        utilisateur.setId(idCounte.getAndIncrement());
        utilisateurs.add(utilisateur);
        return true;
    }
    public boolean modifier(Utilisateur utilisateur) {
        if (utilisateur == null) return false;
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getId() == utilisateur.getId()) {
                utilisateurs.set(i, utilisateur);
                return true;
            }
        }
        return false;
    }
    public boolean supprimer(int id) {
        Utilisateur u = rechercherParId(id);
        if(u != null){
            utilisateurs.remove(id);
            return true;
        }
        return false;
    }

    public Utilisateur rechercherParId(int id) {
        return utilisateurs.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public Utilisateur rechercherParEmail(String email) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getEmail().equals(email)) {
                return utilisateur;
            }
        }
        return null;
    }
    public List<Utilisateur> listerTous() {
        return new ArrayList<>(utilisateurs);
    }
    public List<Utilisateur> listerParRole(Role role) {
    return utilisateurs.stream()
            .filter(u -> u.getRole().equals(role))
            .collect(Collectors.toList());
    }

    public List<Utilisateur> listerActifs(){
        return utilisateurs.stream()
                .filter(Utilisateur::isActif)
                .collect(Collectors.toList());
    }
    public Utilisateur authentifier(String email, String motDePasse){
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getEmail().equals(email) && utilisateur.getMotDePasse().equals(motDePasse)) {
                utilisateurConnecte.add(utilisateur);
                return utilisateur;
            }
        }
        return null;
    }
    /*public void deconnecter() {
        // Implémentation de la déconnexion
        utilisateurConnecte = null;
    }*/
    public boolean changerMotDePasse(int id, String nouveauMotDePasse) {
        Utilisateur u = rechercherParId(id);
        if (u != null) {
            u.setMotDePasse(nouveauMotDePasse);
            utilisateurConnecte.remove(u);
            return true;
        }
        
        return false;
    }
    public String reinitialiserMotDePasse(int id) {
        Utilisateur u = rechercherParId(id);
        if(u != null){
            String nouveau = "Tmp@" + (int)(Math.random() * 10000);
            u.setMotDePasse(nouveau);
            u.setDateModification(new Date());
            return nouveau;}
        return null;
    }
    public boolean verouiller(int id){
        Utilisateur u = rechercherParId(id);
        if(u != null){
            u.setActif(false);
            return true;
        }
        return false;
    }
        
    public boolean deverouiller(int id){
        Utilisateur u = rechercherParId(id);
        if(u != null){
            u.setActif(true);
            u.setTentativesEchouees(0);
            return true;
        }
        return false;
    }
    public int compter(){
        return utilisateurs.size();
    }
    public List<Utilisateur> getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    
}

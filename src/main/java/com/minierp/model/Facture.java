/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minierp.model;

import com.minierp.util.IdGenerator;
import java.time.LocalDate;

public class Facture {
    private final String id;
    private LocalDate date;
    private String clientId;
    private double montantTotal;
    private String statut;
    private String entrepriseId;

    public Facture(LocalDate date, String clientId, double montantTotal, String statut, String entrepriseId) {
        this.id = IdGenerator.generateInvoiceId();
        this.date = date;
        this.clientId = clientId;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.entrepriseId = entrepriseId;
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getClientId() { return clientId; }
    public double getMontantTotal() { return montantTotal; }
    public String getStatut() { return statut; }
    public String getEntrepriseId() { return entrepriseId; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }
    public void setStatut(String statut) { this.statut = statut; }
    public void setEntrepriseId(String entrepriseId) { this.entrepriseId = entrepriseId; }

    @Override
    public String toString() {
        return "Facture: " + id + " | Client: " + clientId +
               " | Montant: " + montantTotal + " | Statut: " + statut +
               " | Date: " + date + " | Entreprise ID: " + entrepriseId;
    }
}


package com.minierp.model;

public class StatutsEnums {

    public enum StatutCommande {
        BROUILLON, EN_ATTENTE, CONFIRMEE, EN_PREPARATION, EXPEDIE, LIVREE, ANNULEE, RETOURNEE
    }

    public enum TypeCommande {
        VENTE, ACHAT, DEVIS
    }

    public enum StatutFacture {
        BROUILLON, EMISE, ENVOYEE, PAYEE_PARTIELLEMENT, PAYEE, EN_RETARD, ANNULEE
    }

    public enum ModePaiement {
        ESPECES, CHEQUE, VIREMENT, CARTE_BANCAIRE, TRAITE, PRELEVEMENT
    }

    public enum TypeFacture {
        FACTURE, AVOIR, ACOMPTE, PROFORMA
    }

    public enum TypeMouvement {
        ENTREE, SORTIE, AJUSTEMENT, INVENTAIRE, RETOUR, PERTE, RESERVATION, ANNULATION
    }
}

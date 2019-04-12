package com.example.demo.Entities;


import lombok.Data;

@Data
public class Commande {
    private String commande;
    public Commande(String commande) {
        this.commande = commande;
    }

    public Commande() {
    }

    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }
}

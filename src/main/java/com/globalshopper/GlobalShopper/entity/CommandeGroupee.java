package com.globalshopper.GlobalShopper.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class CommandeGroupee {

    public CommandeGroupee(int id, double montant, OrderStatus status, int quantiteRequis, int quaniteActuelle, LocalDate deadline, Produit produit, List<Participation> participations, LocalDate dateDebut,Commercant commercant) {
        this.id = id;
        this.montant = montant;
        this.status = status;
        this.quantiteRequis = quantiteRequis;
        this.quaniteActuelle = quaniteActuelle;
        this.deadline = deadline;
        this.produit = produit;
        this.participations = participations;
        this.dateDebut = dateDebut;
        this.commercant = commercant;
    }
    public CommandeGroupee(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private int quantiteRequis;

    private int quaniteActuelle;

    @Column(nullable = false)
    private LocalDate deadline;
    private LocalDate dateDebut;

    @ManyToOne
    @JoinColumn(name = "commercant_id")
    @JsonBackReference
    private Commercant commercant;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonBackReference
    private Produit produit;

    @OneToMany(mappedBy = "commandeGroupee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Participation> participations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getQuantiteRequis() {
        return quantiteRequis;
    }

    public void setQuantiteRequis(int quantiteRequis) {
        this.quantiteRequis = quantiteRequis;
    }

    public int getQuaniteActuelle() {
        return quaniteActuelle;
    }

    public void setQuaniteActuelle(int quaniteActuelle) {
        this.quaniteActuelle = quaniteActuelle;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Commercant getCommercant() {
        return commercant;
    }

    public void setCommercant(Commercant commercant) {
        this.commercant = commercant;
    }
}

package com.globalshopper.GlobalShopper.entity;


import com.fasterxml.jackson.databind.DatabindException;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class CommandeGroupee {

    public CommandeGroupee(long id, int montant, OrderStatus status, int quantiteRequis, int quanitrActuelle, LocalDate deadline, Produit produit, List<Participation> participations) {
        this.id = id;
        this.montant = montant;
        this.status = status;
        this.quantiteRequis = quantiteRequis;
        this.quanitrActuelle = quanitrActuelle;
        this.deadline = deadline;
        this.produit = produit;
        this.participations = participations;
    }
    public CommandeGroupee(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int montant;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private int quantiteRequis;

    private int quanitrActuelle;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @OneToMany(mappedBy = "commandeGroupee", cascade = CascadeType.ALL)
    private List<Participation> participations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
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

    public int getQuanitrActuelle() {
        return quanitrActuelle;
    }

    public void setQuanitrActuelle(int quanitrActuelle) {
        this.quanitrActuelle = quanitrActuelle;
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
}

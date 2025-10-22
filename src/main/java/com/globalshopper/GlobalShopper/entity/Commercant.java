package com.globalshopper.GlobalShopper.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Commercant extends Utilisateur {

    public Commercant(List<Participation> participation, List<CommandeGroupee> commandeGroupees) {
        this.participation = participation;
        this.commandeGroupees = commandeGroupees;
    }

    public Commercant(){

    }

    @OneToMany(mappedBy = "commercant", cascade = CascadeType.ALL)
    @JsonManagedReference("commercant-commande")
    private List<CommandeGroupee> commandeGroupees;

    @OneToMany(mappedBy = "commercant", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("commercant-participation")
    private List<Participation> participation;

    public List<Participation> getParticipation() {
        return participation;
    }

    public void setParticipation(List<Participation> participation) {
        this.participation = participation;
    }

    public List<CommandeGroupee> getCommandeGroupees() {
        return commandeGroupees;
    }

    public void setCommandeGroupees(List<CommandeGroupee> commandeGroupees) {
        this.commandeGroupees = commandeGroupees;
    }
}

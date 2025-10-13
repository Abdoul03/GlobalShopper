package com.globalshopper.GlobalShopper.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "Commercant_id")
    private Commercant commercant;

    @ManyToOne
    @JoinColumn(name = "commandeGroupe_Id")
    private CommandeGroupee commandeGroupee;

    private LocalDate data;
    private int quantite;
    private int montant;

    @OneToOne(mappedBy = "participation")
    private Transaction transaction;
}

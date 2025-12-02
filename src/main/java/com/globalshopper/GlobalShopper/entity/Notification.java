package com.globalshopper.GlobalShopper.entity;


import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    private String message;
//    @ManyToOne
//    private Commercant commercant;

    public Notification(long id, String titre, String message, Commercant commercant) {
        this.id = id;
        this.titre = titre;
        this.message = message;
//        this.commercant = commercant;
    }

    public Notification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public Commercant getCommercant() {
//        return commercant;
//    }
//
//    public void setCommercant(Commercant commercant) {
//        this.commercant = commercant;
//    }
}

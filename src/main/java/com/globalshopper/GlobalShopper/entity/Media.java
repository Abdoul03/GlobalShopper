package com.globalshopper.GlobalShopper.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Media {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String fileType;

    private String filePath;

    @ManyToOne
    @JsonBackReference("produit-media")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    //Constructeur
    public Media(long id, String fileName, String fileType, String filePath, Produit produit) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.produit = produit;
    }

    public Media(){

    }



    //Getter and Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Transient // <-- pas stocké dans la base
    public String getWebPath() {
        String basePath = "/root/Desktop/uploads/";
        if (filePath != null && filePath.startsWith(basePath)) {
            // On retire la partie du chemin local et on remplace par /images/
            return "/images/" + filePath.substring(basePath.length());
        }
        return filePath;
    }
}

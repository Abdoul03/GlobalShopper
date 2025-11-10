package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.ProduitMapper;
import com.globalshopper.GlobalShopper.dto.request.CaracteristiqueDTO;
import com.globalshopper.GlobalShopper.dto.request.ProduitRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.ProduitResponseDTO;
import com.globalshopper.GlobalShopper.entity.*;
import com.globalshopper.GlobalShopper.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProduitService {
    private final ProduitRepository repository;
    private final CaracteristiqueRepository caracteristiqueRepository;
    private final FournisseurRepository fournisseurRepository;
    private final MediaRepository mediaRepository;
    private final CategorieRepository categorieRepository;

    //chemin de images de
    private final String S = File.separator;
    private final String userHome = System.getProperty("user.home");
    private final String UPLOAD_DIR = userHome + S + "Desktop" + S + "uploads" + S + "produits";

    public ProduitService(ProduitRepository repository,
                          CaracteristiqueRepository caracteristiqueRepository,
                          FournisseurRepository fournisseurRepository, MediaRepository mediaRepository,
                          CategorieRepository categorieRepository
    ) {
        this.repository = repository;
        this.caracteristiqueRepository = caracteristiqueRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.mediaRepository = mediaRepository;
        this.categorieRepository = categorieRepository;
    }

    public ProduitResponseDTO ajouterProduit(ProduitRequestDTO produitRequest, MultipartFile[] images)throws IOException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long fournisseurId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_FOURNISSEUR"))) {
            throw new RuntimeException("Seules les fournisseur peuvent ajouter des produits");
        }

        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElseThrow(()-> new RuntimeException("Fournisseur non trouver"));
        Categorie categorie = categorieRepository.findById(produitRequest.categorieId()).orElseThrow(()-> new RuntimeException("Categorie non trouver"));

        Produit produit = new Produit();

        produit.setNom(produitRequest.nom());
        produit.setDescription(produitRequest.description());
        produit.setPrix(produitRequest.prix());
        produit.setMoq(produitRequest.moq());
        produit.setStock(produitRequest.stock());
        produit.setCategorie(categorie);
        produit.setFournisseur(fournisseur);
        produit.setUnite(produitRequest.unite());

       repository.save(produit);

        // 2. Enregistrer les images
        Path uploadPath = Paths.get(UPLOAD_DIR, String.valueOf(produit.getId()));
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Impossible de créer le dossier d'upload.", e);
            }
        }

        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                try {
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    Media media = new Media();
                    media.setFileName(fileName);
                    media.setFileType(file.getContentType());
                    media.setFilePath(filePath.toString());
                    media.setProduit(produit);

                    mediaRepository.save(media);

                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'enregistrement du fichier " + file.getOriginalFilename(), e);
                }
            }
        }
        // 3. Enregistrer les caractéristiques spécifiques
        if (produitRequest.caracteristiques() != null){
            for (CaracteristiqueDTO cr : produitRequest.caracteristiques()) {
                Caracteristique caract = new Caracteristique();
                caract.setNom(cr.nom());
                caract.setValeur(cr.valeur());
                caract.setProduit(produit);

                caracteristiqueRepository.save(caract);
            }
        }
        return ProduitMapper.toResponse(produit);
    }

    //get All produit
    public List<ProduitResponseDTO> getProduit(){
        List<Produit> produits = repository.findAll();
        return produits.stream().map(ProduitMapper::toResponse).toList();
    }

    //Get produit by fournisseur Id
    public List<ProduitResponseDTO> getProduitBySupplierId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long fournisseurId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_FOURNISSEUR"))) {
            throw new RuntimeException("Seules les fournisseur peuvent acceder a cette methode");
        }

        List<Produit> produits = repository.findByFournisseurId(fournisseurId);

        return produits.stream().map(ProduitMapper ::toResponse).toList();

    }

    // for other users
    public List<ProduitResponseDTO> getProduitByfournisseurId(long id){
        List<Produit> produits = repository.findByFournisseurId(id);
        return produits.stream().map(ProduitMapper ::toResponse).toList();
    }

    //get a produit
    public ProduitResponseDTO getAProduict(long id){
        Produit produit = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Produit introuvable"));
        return ProduitMapper.toResponse(produit);
    }

    //get A Produict
    public ProduitResponseDTO updateProduct(Long id , ProduitRequestDTO produitRequestDTO){
        Produit produit = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Produit introuvable"));

        repository.save(produit);
        return ProduitMapper.toResponse(produit);
    }

    //delete produit
    public String deleteProduct(Long id){
        repository.deleteById(id);
        return "Produit supprimer avec succes";
    }

}

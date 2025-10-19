package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.entity.Produit;
import com.globalshopper.GlobalShopper.repository.MediaRepository;
import com.globalshopper.GlobalShopper.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final ProduitRepository produitRepository;

    private final String uploadDir;

    public MediaService(MediaRepository mediaRepository, ProduitRepository produitRepository, @Value("${upload.dir}") String uploadDir) {
        this.mediaRepository = mediaRepository;
        this.produitRepository = produitRepository;
        this.uploadDir = uploadDir;
    }


}

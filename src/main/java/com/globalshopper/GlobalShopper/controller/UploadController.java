package com.globalshopper.GlobalShopper.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("images")
public class UploadController {

    @GetMapping("/uploads/produits/{id}/{filename:.+}")
    public ResponseEntity<Resource> getFile(
            @PathVariable Long id,
            @PathVariable String filename) throws MalformedURLException {

        Path path = Paths.get("uploads", "produits", String.valueOf(id), filename).toAbsolutePath();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaTypeFactory.getMediaType(resource)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
    }

    @GetMapping("/uploads/produits/{id}")
    public ResponseEntity<List<String>> getProductImages(@PathVariable Long id) {
        Path productFolder = Paths.get("uploads", "produits", String.valueOf(id));
        if (!Files.exists(productFolder)) {
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> paths = Files.list(productFolder)) {
            List<String> imageUrls = paths
                    .filter(Files::isRegularFile)
                    .map(path -> "http://localhost:8080/api/uploads/produits/" + id + "/" + path.getFileName().toString())
                    .toList();

            return ResponseEntity.ok(imageUrls);
        } catch ( IOException e) {
            throw new RuntimeException("Erreur de lecture des images du produit " + id, e);
        }
    }
}

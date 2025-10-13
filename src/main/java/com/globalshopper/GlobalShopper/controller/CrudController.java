package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<T,ID> {

    @Autowired
    private CrudService<T,ID> crudService;

    @PostMapping
    public ResponseEntity<T> ajouter(@RequestBody T entity) {
        return ResponseEntity.ok(crudService.ajout(entity));
    }

    @GetMapping
    public ResponseEntity<?> liste() {
        return ResponseEntity.ok(crudService.liste());
    }


    @GetMapping("/{id}")
    public ResponseEntity<T> trouver(@PathVariable ID id) {
        return crudService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> miseAJour(@RequestBody T entity, @PathVariable ID id) {
        return ResponseEntity.ok(crudService.miseAJour(entity, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable ID id) {
        crudService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}

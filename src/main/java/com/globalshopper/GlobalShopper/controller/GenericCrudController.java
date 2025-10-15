package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.service.GenericCrudService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public abstract class GenericCrudController<E,ID,REQ,RES> {

    private final GenericCrudService<E, ID, REQ, RES> genericCrudService;

    @PostMapping
    public ResponseEntity<RES> add (@Valid @RequestBody REQ dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(genericCrudService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RES>> getAll (){
        return ResponseEntity.ok(genericCrudService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RES> getById (@PathVariable ID id){
        return ResponseEntity.ok(genericCrudService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RES> miseAJour(@Valid @RequestBody REQ dto, @PathVariable ID id){
        return ResponseEntity.ok(genericCrudService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id){
        genericCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

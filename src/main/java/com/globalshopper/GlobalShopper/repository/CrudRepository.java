package com.globalshopper.GlobalShopper.repository;


import java.util.List;

public interface CrudRepository <REQ, RES, ID> {
    // Add entity
    RES create(REQ dto);

    // Get all entity
    List<RES> getAll();

    // Get entity by id
    RES getById(ID id);

    // Update entity
    RES update(ID id, REQ dto);

    // Delete entity
    void delete(ID id);
}

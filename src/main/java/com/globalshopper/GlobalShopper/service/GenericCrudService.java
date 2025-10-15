package com.globalshopper.GlobalShopper.service;


import com.globalshopper.GlobalShopper.repository.CrudMapper;
import com.globalshopper.GlobalShopper.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenericCrudService<E,ID,REQ ,RES> implements CrudRepository<REQ ,RES ,ID> {

    private final JpaRepository<E,ID> repository;
    private final CrudMapper<E,REQ,RES> mapper;

    public GenericCrudService(JpaRepository<E,ID> repository,CrudMapper<E,REQ,RES> mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RES create(REQ dto) {
        E entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public List<RES> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RES getById(ID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow();
    }

    @Override
    public RES update(ID id,REQ dto) {
        E entity = repository.findById(id).orElseThrow();
        // mise à jour manuelle ou via mapper
        E updated = mapper.toEntity(dto);

        return mapper.toResponse(repository.save(updated));
    }

    @Override
    public void delete( ID id) {
        repository.deleteById(id);
    }
}

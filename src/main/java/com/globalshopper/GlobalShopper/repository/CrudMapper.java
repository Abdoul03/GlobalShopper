package com.globalshopper.GlobalShopper.repository;

public interface CrudMapper <E, REQ, RES>{
    E toEntity(REQ dto);
    RES toResponse(E entity);
}

package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.repository.CrudMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface adminMapper extends CrudMapper<Admin, UtilisateurRequestDTO, UtilisateurResponseDTO> {
}

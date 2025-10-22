package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.service.CommandeGroupeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("commandeGroupee")
public class CommandeGrooupeeController {

    private CommandeGroupeeService commandeGroupeeService;

    public CommandeGrooupeeController(CommandeGroupeeService commandeGroupeeService) {
        this.commandeGroupeeService = commandeGroupeeService;
    }

    @PostMapping("create/{produitId}")
    public ResponseEntity<CommandeGroupeeResponseDTO> getAOrder(
        @PathVariable long produitId,
        @RequestBody ParticipationRequestDTO participationDTO,
        @RequestParam LocalDate deadline
    ){
        return ResponseEntity.ok(commandeGroupeeService.createUneCommandeGroupee(produitId,participationDTO,deadline));
    }

    @PostMapping("join/{produitId}")
    public ResponseEntity<CommandeGroupeeResponseDTO> joinGroupOrder(
            @PathVariable long produitId,
            @RequestBody ParticipationRequestDTO participationRequestDTO
    ){
        return ResponseEntity.ok(commandeGroupeeService.rejoindreUneCommandeGroupee(produitId, participationRequestDTO));
    }
}

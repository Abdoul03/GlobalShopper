package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.service.CommandeGroupeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("remove/{commandeId}")
    public ResponseEntity<CommandeGroupeeResponseDTO> retirerCommande(
            @PathVariable int commandeId
    ){
        return ResponseEntity.ok(commandeGroupeeService.retirerParticipation(commandeId));
    }

    @GetMapping("commercant/all/{commercantId}")
    public ResponseEntity<List<CommandeGroupeeResponseDTO>> allGetOrdersCreateByTrader(@PathVariable long commercantId){
        return ResponseEntity.ok(commandeGroupeeService.allOrderCreateByTrader(commercantId));
    }

    @GetMapping("commercant/{commercantId}")
    public ResponseEntity<CommandeGroupeeResponseDTO> getOrdersCreateByTrader(@PathVariable long commercantId){
        return ResponseEntity.ok(commandeGroupeeService.orderCreateByTrader(commercantId));
    }

    @GetMapping("fournisseur/{id}")
    public ResponseEntity<List<CommandeGroupeeResponseDTO>> commandeFournisseur(@PathVariable long id){
        return ResponseEntity.ok(commandeGroupeeService.allSupplierOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<CommandeGroupeeResponseDTO>> getAllCommande(){
        return ResponseEntity.ok(commandeGroupeeService.getAllCommandeGrouper());
    }

    @GetMapping("{id}")
    public ResponseEntity<CommandeGroupeeResponseDTO> getAGroupeOrder(@PathVariable int id){
        return ResponseEntity.ok(commandeGroupeeService.getAOrderGroupe(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteGroupOrder(@PathVariable int id){
        return ResponseEntity.ok(commandeGroupeeService.deleteOrderGroup(id));
    }
}

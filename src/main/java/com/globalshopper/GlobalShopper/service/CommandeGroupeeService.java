package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.repository.CommandeGroupeeRepository;
import com.globalshopper.GlobalShopper.repository.CommercantRepository;
import com.globalshopper.GlobalShopper.repository.ParticipationRepository;
import com.globalshopper.GlobalShopper.repository.ProduitRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandeGroupeeService {

    private final CommandeGroupeeRepository commandeGroupeeRepository;
    private final CommercantRepository commercantRepository;
    private final ParticipationRepository participationRepository;
    private final ProduitRepository produitRepository;

    public CommandeGroupeeService(CommandeGroupeeRepository commandeGroupeeRepository, CommercantRepository commercantRepository, ParticipationRepository participationRepository, ProduitRepository produitRepository) {
        this.commandeGroupeeRepository = commandeGroupeeRepository;
        this.commercantRepository = commercantRepository;
        this.participationRepository = participationRepository;
        this.produitRepository = produitRepository;
    }

    public
}
